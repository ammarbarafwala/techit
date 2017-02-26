package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import function.RetrieveData;
import function.SendEmail;

@WebServlet("/AssignTechnician")
public class AssignTechnician extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("Login");
		}else if(Integer.parseInt(request.getSession().getAttribute("position").toString()) >= 2){
			response.sendRedirect("Home");
		}
		else {
			RetrieveData rd = null;
			if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
				rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
			}
			else{
				String dbURL = request.getServletContext().getAttribute("dbURL").toString();
				String dbUser = request.getServletContext().getAttribute("dbUser").toString();
				String dbPass = request.getServletContext().getAttribute("dbPass").toString();
				rd = new RetrieveData(dbURL, dbUser, dbPass);
			}
			
			int unitId = Integer.parseInt(request.getSession().getAttribute("unit_id").toString());
			int ticketId = Integer.parseInt(request.getParameter("id"));
			String ticketProgress = request.getParameter("prog");
			
			try {
				request.setAttribute("ticket_progress", ticketProgress);
				request.setAttribute("ticket_id", ticketId);
				request.setAttribute( "techList", rd.getUnitTechnicians(unitId) );
				request.setAttribute("techVerify", rd.getTechId(ticketId));
			} catch (SQLException e) {
				request.setAttribute("errorMessage", "Something went wrong when getting the technicians, please try again later.");
			}
			
			request.getRequestDispatcher("/WEB-INF/AssignTechnician.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] technicians = request.getParameterValues("tech");
		
		String ticketProgress = request.getParameter("ticket_progress");
		int ticketId = Integer.parseInt(request.getParameter("ticket_id")); 
		RetrieveData rd = null;
		if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
			rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
		}
		else{
			String dbURL = request.getServletContext().getAttribute("dbURL").toString();
			String dbUser = request.getServletContext().getAttribute("dbUser").toString();
			String dbPass = request.getServletContext().getAttribute("dbPass").toString();
			rd = new RetrieveData(dbURL, dbUser, dbPass);
		}
		Map<String, Boolean> techVerify = rd.getTechId(ticketId);
		
		Connection c = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		try{
			if(Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString()))
			{
				c = ((DataSource)request.getServletContext().getAttribute("dbSource")).getConnection();
			}
			else{
				String dbURL = request.getServletContext().getAttribute("dbURL").toString();
				String dbUser = request.getServletContext().getAttribute("dbUser").toString();
				String dbPass = request.getServletContext().getAttribute("dbPass").toString();

				c = DriverManager.getConnection(dbURL, dbUser, dbPass);
			}
			String insert_tech = "insert into assignments (ticketId, technicianUser) values (?, ?)";
			boolean insert = false;
			List<String> techs = new ArrayList<String>();
			for( String tech : technicians ){
				if(!techVerify.containsKey(tech)){
					insert = true;
					pstmt = c.prepareStatement( insert_tech );
		            pstmt.setInt( 1, ticketId);   
		            pstmt.setString(2, tech);
		            pstmt.executeUpdate();
		            pstmt.close();
		            
		            techs.add(rd.getEmailFromUsername(tech));
				}
			}
			
			if(insert){
				// Get current time
				java.util.Date dt = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				
				// Insert new technicians into assignment table
				String insert_update = "insert into updates (ticketId, modifier, updateDetails, modifiedDate) values (?, ?, ?, ?) ";
				pstmt2 = c.prepareStatement( insert_update );
				pstmt2.setInt(1, ticketId);
				pstmt2.setString(2, request.getSession().getAttribute("user").toString());
				pstmt2.setString(3, "Technician(s) have been assigned to this ticket.");
				pstmt2.setString(4, currentTime);
				pstmt2.executeUpdate();
				
				pstmt2.close();
				
				// Update ticket table
				if(ticketProgress.equals("OPEN")){
					String ticket_update = "update tickets set lastUpdated = ?, Progress = ? where id = ?";
					pstmt3 = c.prepareStatement( ticket_update );
					pstmt3.setString(1, currentTime);
					pstmt3.setInt(2, 1);
					pstmt3.setInt(3, ticketId);
					pstmt3.executeUpdate();
					pstmt3.close();
				}
				else{
					String ticket_update = "update tickets set lastUpdated = ? where id = ?";
					pstmt3 = c.prepareStatement( ticket_update );
					pstmt3.setString(1, currentTime);
					pstmt3.setInt(2, ticketId);
					pstmt3.executeUpdate();
					pstmt3.close();
				}
				
				c.close();
				
				String domain = request.getServletContext().getAttribute("domain").toString();
				
				//Email the technicians
				if(techs.size() > 0) {
					final List<String> allEmails = techs;
					final String requestorEmail = rd.getRequestorEmailFromTicket(ticketId);
					final String emailSubject = "TECHIT - You have been assigned to ticket #" + ticketId;
					final String emailDetails = "You have been assigned to ticket #" + ticketId 
							+ "\n" + rd.getTicket(ticketId).toString()
							+ "\n=================================================\n"
							+ "\n" + domain + "Details?id=" + ticketId;
					
					final String requestorEmailSubject = "TECHIT - Technician has been assigned to your ticket #" + ticketId;
					final String requestorEmailDetails = "Technician has been assigned to the following ticket: "
							+ "\n" + rd.getTicket(ticketId).toString()
							+ "\n=================================================\n"
							+ "\n" + domain + "Details?id=" + ticketId;
					final String emailFrom = request.getServletContext().getAttribute("email").toString();
					
					new Thread(new Runnable(){
						public void sendEmail(){
						SendEmail se = new SendEmail();
						se.sendMultipleEmail( (Session) getServletContext().getAttribute("session"),
								(Properties) getServletContext().getAttribute("properties"),
								getServletContext().getAttribute("email").toString(),
								allEmails, emailSubject, emailDetails);
						
						se.sendEmail((Session) getServletContext().getAttribute("session"), emailFrom, requestorEmail, requestorEmailSubject, requestorEmailDetails);
						}
						public void run(){
							this.sendEmail();
						}
					}).start();
					
				}
				request.getSession().setAttribute("tickets", rd.getUserTicket(request.getSession().getAttribute("user").toString(), 
						Integer.parseInt(request.getSession().getAttribute("position").toString()), 
						Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));
				request.getSession().setAttribute("pSuccessMessage", "Technicians have been successfully assigned to the ticket!");
				response.sendRedirect("Details?id="+ticketId);
			}
		}catch(Exception e){
			request.setAttribute("errorMessage", "Something went wrong when getting the technicians, please try again later.");
			request.getRequestDispatcher("/WEB-INF/AssignTechnician.jsp").forward(request, response);
		}finally{
			DbUtils.closeQuietly(c);
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(pstmt2);
			DbUtils.closeQuietly(pstmt3);
		}
	}

}

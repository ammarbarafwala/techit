package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import model.Ticket;

@WebServlet("/Priority")
public class Priority extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Does nothing
		if(request.getSession().getAttribute("user")==null){
			response.sendRedirect("Login");
		}
		else{
			response.sendRedirect("Home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection c = null;
		PreparedStatement pstmt = null;
		PreparedStatement insertUpdate = null;

		int id = Integer.parseInt(request.getParameter("priorityBt"));
		int priority = Integer.parseInt(request.getParameter("selectPriority"));
		try{
			
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
			
			Ticket ticket = rd.getTicket(id);
			if(ticket.getPriorityNumeric() == priority){
				request.getSession().setAttribute("pSuccessMessage", "No changes were made because priority selected was the same as before.");
				response.sendRedirect("Details?id="+id);
			}
			else{
				String updateStatement = "update tickets set priority = ? where id = ?";
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
				pstmt = c.prepareStatement(updateStatement);
				pstmt.setInt(1, priority);
				pstmt.setInt(2, id);
				pstmt.executeUpdate();
				pstmt.close();
				
				
				// Get current time
				java.util.Date dt = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				
				// Get string value of priority
				String priorityLevel = "";
				switch(priority){
					case 0:
						priorityLevel = "NOT ASSIGNED";
						break;
					case 1:
						priorityLevel = "LOW";
						break;
					case 2:
						priorityLevel = "MEDIUM";
						break;
					case 3:
						priorityLevel = "HIGH";
						break;
				}
				
				// Insert update
				String insertQuery = "insert into updates (ticketId, modifier, updateDetails, modifiedDate) values (?, ?, ?, ?) ";
				
            	insertUpdate = c.prepareStatement(insertQuery);
            	insertUpdate.setInt(1, id);
            	insertUpdate.setString(2, request.getSession().getAttribute("user").toString());
            	insertUpdate.setString(3, "The priority of this ticket has been set to: " + priorityLevel);
            	insertUpdate.setString(4, currentTime);
            	insertUpdate.executeUpdate();
            	insertUpdate.close();
            	c.close();
            	// Get emails if there is technicians assigned to the ticket
            	
            	if(ticket.getNumOfTechnician() > 0){
            		List<String> techEmails = new ArrayList<String>();
            		for(int i = 0; i < ticket.getNumOfTechnician(); i ++)
            		{
            			techEmails.add(ticket.getTechnicians().get(i).getEmail());
            		}
            	
		        	final List<String> finalList = techEmails;
		        	String domain = request.getServletContext().getAttribute("domain").toString();
		        	final String subjectDetails = "TECHIT - Ticket #" + id +"'s priority has been set to: " + priorityLevel;
		        	final String emailDetails = "A supervisor has set priority of " + priorityLevel + " to this ticket."
		        			+ "\n====================================================\n"
		        			+ "Ticket info: \n"
		        			+ ticket.toString() + "\n"
		        			+ "\n====================================================\n"
		        			+ domain + "Details?id=" + id;
		        	// Sending the technician the new update notice
		        	
		        	new Thread(new Runnable(){
						public void sendEmail(){
						SendEmail se = new SendEmail();
						se.sendMultipleEmail( (Session) getServletContext().getAttribute("session"),
								getServletContext().getAttribute("email").toString(),
								finalList, subjectDetails, emailDetails);
						}
						public void run(){
							this.sendEmail();
						}
					}).start();
            	}
            	
            	request.getSession().setAttribute("pSuccessMessage", "Priority was set successfully!");
            	response.sendRedirect("Details?id="+id);
			}
		}catch(SQLException e){
			request.setAttribute("errorMessage", "Something went wrong during cancelation, please try again later!");
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
		}
		catch(Exception e){
			request.setAttribute("errorMessage", "Unexpected error has occured, please try again later!");
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(insertUpdate);
			DbUtils.closeQuietly(c);
	}
	}

}

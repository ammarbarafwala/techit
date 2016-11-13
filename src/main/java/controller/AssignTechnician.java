package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import function.RetrieveData;

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
			RetrieveData rd = new RetrieveData();
			
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
		
		String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
		String db_user = "cs4961stu01";
		String db_pass = ".XCGG1Bc";
		
		String ticketProgress = request.getParameter("ticket_progress");
		int ticketId = Integer.parseInt(request.getParameter("ticket_id")); 
		RetrieveData rd = new RetrieveData();
		Map<String, Boolean> techVerify = rd.getTechId(ticketId);
		
		Connection c = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		try{
			c = DriverManager.getConnection(url, db_user, db_pass);
			String insert_tech = "insert into assignments (ticketId, technicianUser) values (?, ?)";
			boolean insert = false;
            
			for( String tech : technicians ){
				if(!techVerify.containsKey(tech)){
					insert = true;
					pstmt = c.prepareStatement( insert_tech );
		            pstmt.setInt( 1, ticketId);   
		            pstmt.setString(2, tech);
		            pstmt.executeUpdate();
		            pstmt.close();
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
				request.getSession().setAttribute("tickets", rd.getUserTicket(request.getSession().getAttribute("user").toString(), 
						Integer.parseInt(request.getSession().getAttribute("position").toString()), 
						Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));
				request.setAttribute("successMessage", "Technicians have been successfully assigned to the ticket!");
				request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
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

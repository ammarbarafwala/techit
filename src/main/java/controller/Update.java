package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;

import function.RetrieveData;
import function.StringFilter;

@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("Login");
		}else if(Integer.parseInt(request.getSession().getAttribute("position").toString()) == 3){
			response.sendRedirect("Home");
		}
		else {
			List<String> progName = Arrays.asList("IN PROGRESS", "ON HOLD", "COMPLETED", "CLOSED");
			int ticketId = Integer.parseInt(request.getParameter("id"));
			String ticketProgress = request.getParameter("prog");
			
			request.setAttribute("ticketProg", progName);
			request.setAttribute("ticket_progress", ticketProgress);
			request.setAttribute("ticket_id", ticketId);

			
			request.getRequestDispatcher("/WEB-INF/Update.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringFilter sf = new StringFilter();
		String updateMessage = sf.filterNull(request.getParameter("updateMessage"));
		
		if(!updateMessage.isEmpty())
		{
			String newProg = request.getParameter("newProg");
			String oldProg = request.getParameter("oldProg");
			int ticketId = Integer.parseInt(request.getParameter("ticket_id").toString());
			
			// Get current time
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
			String db_user = "cs4961stu01";
			String db_pass = ".XCGG1Bc";
			
			Connection c = null;
			PreparedStatement ptsmt = null;
			PreparedStatement pstmt2 = null;
			try{
				c = DriverManager.getConnection(url, db_user, db_pass);
				if(!newProg.equals(oldProg)){
					int status = 0;
					
					System.out.println("Before switch: " + status);
					
					switch(newProg){
					case "IN PROGRESS":
					default:
						status = 1;
						break;
					case "ON HOLD":
						status = 2;
						break;
					case "COMPLETED":
						status = 3;
						break;
					case "CLOSED":
						status = 4;
						break;
					}
					
					System.out.println("After switch: " + status);
					
					String update_ticket = "update tickets set Progress = ?, lastUpdated = ? where id = ?";
					ptsmt = c.prepareStatement(update_ticket);
					ptsmt.setInt(1, status);
					ptsmt.setString(2, currentTime);
					ptsmt.setInt(3, ticketId);
					ptsmt.executeUpdate();
					
				}
				else{
					String update_ticket = "update tickets set lastUpdated = ? where id = ?";
					ptsmt = c.prepareStatement(update_ticket);
					ptsmt.setString(1, currentTime);
					ptsmt.setInt(2, ticketId);
					ptsmt.executeUpdate();
					
				}
				
				String insert_update = "insert into updates (ticketId, modifier, updateDetails, modifiedDate) values (?, ?, ?, ?) ";
				pstmt2 = c.prepareStatement(insert_update);
				pstmt2.setInt(1, ticketId);
				pstmt2.setString(2, request.getSession().getAttribute("user").toString());
				pstmt2.setString(3, updateMessage);
				pstmt2.setString(4, currentTime);
				pstmt2.executeUpdate();
				
				ptsmt.close();
				pstmt2.close();
				c.close();
				
				RetrieveData rd = new RetrieveData();
				request.getSession().setAttribute("tickets", rd.getUserTicket(request.getSession().getAttribute("user").toString(), 
						Integer.parseInt(request.getSession().getAttribute("position").toString()), 
						Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));
				
				request.setAttribute("successMessage", "Successfully updated!");
				request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
				
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("errorMessage", "Something went wrong when updating, please try again later!");
				request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
			}finally{
				DbUtils.closeQuietly(ptsmt);
				DbUtils.closeQuietly(pstmt2);
				DbUtils.closeQuietly(c);
			}
		}
		else{
			List<String> progName = Arrays.asList("IN PROGRESS", "ON HOLD", "COMPLETED", "CLOSED");
			int ticketId = Integer.parseInt(request.getParameter("ticket_id"));
			String ticketProgress = request.getParameter("oldProg").toString();
			
			request.setAttribute("ticketProg", progName);
			request.setAttribute("ticket_progress", ticketProgress);
			request.setAttribute("ticket_id", ticketId);
			
			request.setAttribute("errorMessage", "Please input the details for this update.");
			request.getRequestDispatcher("/WEB-INF/Update.jsp").forward(request, response);
		}
	}

}

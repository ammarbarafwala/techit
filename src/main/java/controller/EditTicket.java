package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import function.RetrieveData;
import model.Ticket;

@WebServlet("/EditTicket")
public class EditTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Ticket getTicket (Integer id) throws ServletException
	{
		Ticket ticket = null;
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
			String db_user = "cs4961stu01";
			String db_pass = ".XCGG1Bc";
			
			c = DriverManager.getConnection(url, db_user, db_pass);
			String search_user = "select * from tickets where id = ?";
            PreparedStatement pstmt = c.prepareStatement( search_user );
            pstmt.setInt( 1, id);   
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
            {
            	
            	ticket = new Ticket (rs.getInt("id"), rs.getString("username"), rs.getString("userFirstName"), rs.getString("userLastName"), 
            			rs.getString("phone"), rs.getString("email"), rs.getInt("unitId"), rs.getString("details"), rs.getDate("startDate"), rs.getDate("lastUpdated"),
            			rs.getString("ticketLocation"));         
            	c.close();
            }          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if(c != null){
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		
		return ticket;
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Ticket ticket = getTicket(id);
		System.out.println(ticket.getEmail());
		request.setAttribute("ticket", ticket);
		request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName").replace(" ", "");
		String lastName = request.getParameter("lastName").replace(" ", "");
		String email = request.getParameter("email").replace(" ", "");
		String phoneNumber = request.getParameter("phoneNumber");
		String details = request.getParameter("details");
		String location = request.getParameter("location");
		int units = Integer.parseInt(request.getParameter("units"));
		int id = Integer.parseInt(request.getParameter("id"));
		int Progress = 0;
		//java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || details.isEmpty() || location.isEmpty())
		{
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("details", details);
			request.getSession().setAttribute("location", location);
			request.getSession().setAttribute("errorMessage", "Some fields are missing!");
			request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);
		}
		else if( phoneNumber.length() < 14){
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("errorMessage", "Incorrect phone number format!");
			request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);
			
		}
		else{
			Connection c = null;
			try
			{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
				String db_user = "cs4961stu01";
				String db_pass = ".XCGG1Bc";
				
				c = DriverManager.getConnection(url, db_user, db_pass);
				String createTicket = "update tickets set userFirstName = ? , userLastName =? , phone =? , email = ?, details = ?, lastUpdated = NOW() ,ticketLocation = ?, unitId =? where id =?";
	            PreparedStatement pstmt = c.prepareStatement( createTicket );
	            pstmt.setString( 1, firstName );
	            pstmt.setString( 2, lastName );
	            pstmt.setString( 3, phoneNumber );
	            pstmt.setString( 4, email );
	            pstmt.setString( 5, details );
	            pstmt.setString( 6, location );
	            pstmt.setInt( 7, units);
	            pstmt.setInt( 8, id);
	            pstmt.executeUpdate();	       
	            c.close();
	            
				RetrieveData rd = new RetrieveData();
				request.getSession().setAttribute("tickets", rd.getUserTicket(request.getSession().getAttribute("user").toString(), 
						Integer.parseInt(request.getSession().getAttribute("position").toString()), 
						Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				if(c != null){
					try {
						c.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(request.getSession().getAttribute("errorMessage")!= null){
				request.removeAttribute("errorMessage");
			}
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
		}
	}	

}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditTicket
 */
@WebServlet("/EditTicket")
public class EditTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName").replace(" ", "");
		String lastName = request.getParameter("lastName").replace(" ", "");
		String email = request.getParameter("email").replace(" ", "");
		String phoneNumber = request.getParameter("phoneNumber");
		String details = request.getParameter("details");
		String location = request.getParameter("location");
		String units = request.getParameter("units");
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
				String createTicket = "insert into tickets (username, userFirstName, userLastName, phone, email, details,startDate, lastUpdated ,ticketLocation, units) values (?,?,?,?,?,?,NOW(),NOW(),?,?)";
	            PreparedStatement pstmt = c.prepareStatement( createTicket );
	            pstmt.setString( 1, request.getSession().getAttribute("user").toString() );
	            pstmt.setString( 2, firstName );
	            pstmt.setString( 3, lastName );
	            pstmt.setString( 4, phoneNumber );
	            pstmt.setString( 5, email );
	            pstmt.setString( 6, details );
	            pstmt.setString( 7, location );
	            pstmt.setString( 8, units);
	            pstmt.executeUpdate();	       
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
			request.getRequestDispatcher("/Home").forward(request, response);
		}
	}	

}

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

/**
 * This servlet is the controller for FirstLoginUpdate.jsp
 * This code is meant to add missing information of the Active Directory system
 * into our local database when the user login for the first time.
 */
@WebServlet(urlPatterns="/FirstLoginUpdate")
public class FirstLoginUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			request.getRequestDispatcher("/Login").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/FirstLoginUpdate.jsp").forward(request, response);
		}
		//request.getRequestDispatcher("/WEB-INF/FirstLoginUpdate.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty())
		{
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("errorMessage", "Some fields are missing!");
			request.getRequestDispatcher("/WEB-INF/FirstLoginUpdate.jsp").forward(request, response);
		}
		else if( phoneNumber.length() < 14){
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("errorMessage", "Incorrect phone number format!");
			request.getRequestDispatcher("/WEB-INF/FirstLoginUpdate.jsp").forward(request, response);
			
		}
		else{
			Connection c = null;
			try
			{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
				String db_user = "cs4961stu01";
				String db_pass = ".XCGG1Bc";
				
				c = DriverManager.getConnection(url, db_user, db_pass);
				String search_user = "select * from users where username = ?";
	            PreparedStatement pstmt = c.prepareStatement( search_user );
	            pstmt.setString( 1, request.getSession().getAttribute("user").toString() );
	            ResultSet rs = pstmt.executeQuery();
	            
	            if(rs.next())
	            {
	            	String update_user = "update users set firstname = ?, lastname = ?, email = ?, phone = ? where username = ?";
	            	PreparedStatement pstmt2 = c.prepareStatement(update_user);
	            	pstmt2.setString(1, firstName);
	            	pstmt2.setString(2, lastName);
	            	pstmt2.setString(3, email);
	            	pstmt2.setString(4, phoneNumber);
	            	pstmt2.setString(5, request.getSession().getAttribute("user").toString());
		            pstmt2.executeUpdate();
		            
	            	c.close();
	            }
	            else
	            {
	            	String insert_user = "insert into users (firstname, lastname, username, phone, email, position) values(?, ?, ?, ?, ?, ?)";
	            	PreparedStatement pstmt2 = c.prepareStatement(insert_user);
	            	pstmt2.setString(1, firstName);
	            	pstmt2.setString(2, lastName);
	            	pstmt2.setString(3, request.getSession().getAttribute("user").toString());
	            	pstmt2.setString(4, phoneNumber);
	            	pstmt2.setString(5, email);
	            	pstmt2.setInt(6, 3);
	            	pstmt2.executeUpdate();
	            	
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
			
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getRequestDispatcher("/Home").forward(request, response);
		}
	}

}

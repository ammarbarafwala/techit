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

import org.apache.commons.dbutils.DbUtils;

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
		String firstName = request.getParameter("firstName").replace(" ", "");
		String lastName = request.getParameter("lastName").replace(" ", "");
		String email = request.getParameter("email").replace(" ", "");
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
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
				String db_user = "cs4961stu01";
				String db_pass = ".XCGG1Bc";
				
				c = DriverManager.getConnection(url, db_user, db_pass);
				String search_user = "select * from users where username = ?";
	            pstmt = c.prepareStatement( search_user );
	            pstmt.setString( 1, request.getSession().getAttribute("user").toString() );
	            
	            rs = pstmt.executeQuery();
	            
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

	            }
	            else
	            {
	            	String insert_user = "insert into users (firstname, lastname, pass, username, phone, email, position) values(?, ?, ?, ?, ?, ?, ?)";
	            	PreparedStatement pstmt2 = c.prepareStatement(insert_user);
	            	pstmt2.setString(1, firstName);
	            	pstmt2.setString(2, lastName);
	            	pstmt2.setString(3, "");
	            	pstmt2.setString(4, request.getSession().getAttribute("user").toString());
	            	pstmt2.setString(5, phoneNumber);
	            	pstmt2.setString(6, email);
	            	pstmt2.setInt(7, 3);
	            	pstmt2.execute();

	            }
	            pstmt.close();
	            rs.close();
	            c.close();
	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				DbUtils.closeQuietly(pstmt);
				DbUtils.closeQuietly(rs);
				DbUtils.closeQuietly(c);
			}
			if(request.getSession().getAttribute("errorMessage")!= null){
				request.removeAttribute("errorMessage");
			}
			request.getSession().setAttribute("lastname", lastName);
			request.getSession().setAttribute("firstname", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			response.sendRedirect("Home");
		}
	}

}

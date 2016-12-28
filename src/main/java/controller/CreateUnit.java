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
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import function.RetrieveData;


@WebServlet("/CreateUnit")
public class CreateUnit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Does nothing since there is no create Unit page
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String unitName = request.getParameter("unitName").replace(" ", "");
		String phone = request.getParameter("phoneNumber");
		String location =  request.getParameter("location").replace(" ", "");
		String email = request.getParameter("email").replace(" ", "");
		String description = request.getParameter("description").replace(" ", "");
		if(unitName.isEmpty() || description.isEmpty())
		{	
			request.setAttribute("errorMessage", "Some fields are missing!");
			request.getRequestDispatcher("/WEB-INF/AcctManagement.jsp").forward(request, response);
		}
		else
		{
		Connection c = null;
		PreparedStatement pstmt = null;
		try {
			if(Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString()))
			{
				c = ((DataSource)request.getServletContext().getAttribute("dbSource")).getConnection();
			}
			else{
				String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
				String db_user = "cs4961stu01";
				String db_pass = ".XCGG1Bc";

				c = DriverManager.getConnection(url, db_user, db_pass);
			}
			
			String createTicket = "insert into units (unitName,phone,location,email, description) values (?,?,?,?,?)";
			pstmt = c.prepareStatement(createTicket);
			pstmt.setString(1, unitName);
			pstmt.setString(2, phone);
			pstmt.setString(3, location);
			pstmt.setString(4, email);
			pstmt.setString(5, description);			
			pstmt.executeUpdate();
			pstmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(c);
		}
		
		response.sendRedirect("Home");
		}
	}

}

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

@WebServlet("/Cancel")
public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Does nothing, no page
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("cancelBt"));
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
			String db_user = "cs4961stu01";
			String db_pass = ".XCGG1Bc";
			
			c = DriverManager.getConnection(url, db_user, db_pass);
			String cancel = "update tickets set position = ? where username = ?";
            PreparedStatement pstmt = c.prepareStatement( cancel );
            pstmt.setInt( 1, id );
            pstmt.setInt( 2, 4 );
		}
		catch(SQLException e){
			request.getSession().setAttribute("Error Message", "Something went wrong during cancelation, please try again later!");
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
		}
			
	}

}

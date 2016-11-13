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

import function.RetrieveData;

@WebServlet("/Cancel")
public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Does nothing, no page
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("cancelBt"));
		RetrieveData rd = new RetrieveData();
		Connection c = null;
		try
		{
			String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
			String db_user = "cs4961stu01";
			String db_pass = ".XCGG1Bc";
			
			c = DriverManager.getConnection(url, db_user, db_pass);
			String cancel = "update tickets set Progress = ? where id = ?";
            PreparedStatement pstmt = c.prepareStatement( cancel );
            pstmt.setInt( 1, 4 );
            pstmt.setInt( 2, id );
            pstmt.executeUpdate();	       
            c.close();
			request.getSession().setAttribute("tickets", rd.getUserTicket(request.getSession().getAttribute("user").toString(), 
					Integer.parseInt(request.getSession().getAttribute("position").toString()), 
					Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));
		}
		catch(SQLException e){
			request.setAttribute("errorMessage", "Something went wrong during cancelation, please try again later!");
		}finally{
			if(c != null){
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
	}

}

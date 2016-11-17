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
 * Servlet implementation class AcctManagement
 */
@WebServlet("/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user") == null){
			response.sendRedirect("Home");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pnumber = request.getParameter("phoneNumber");
		String save = request.getParameter("Save");
		System.out.println(save);

		if(email.isEmpty() || pnumber.isEmpty())
		{

			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", pnumber);
			request.getSession().setAttribute("errorMessage", "Some fields are missing!");
			request.getRequestDispatcher("/WEB-INF/FirstLoginUpdate.jsp").forward(request, response);
		}
		else if( pnumber.length() < 14){
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", pnumber);
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
					String update_user = "update users set email = ?, phone = ? where username = ?";
					PreparedStatement pstmt2 = c.prepareStatement(update_user);
					pstmt2.setString(1, email);
					pstmt2.setString(2, pnumber);
					pstmt2.setString(3, request.getSession().getAttribute("user").toString());
					pstmt2.executeUpdate();
				}
				
				pstmt.close();
				rs.close();
				c.close();
				
			} catch (SQLException e) {
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

			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", pnumber);
			request.getRequestDispatcher("/Home").forward(request, response);
		}

	}
}

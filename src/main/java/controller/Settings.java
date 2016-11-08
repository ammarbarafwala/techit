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
 * Servlet implementation class AcctManagement
 */
@WebServlet("/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Settings() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/Settings.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
					String update_user = "update users set email = ?, phone = ? where username = ?";
					PreparedStatement pstmt2 = c.prepareStatement(update_user);
					pstmt2.setString(1, email);
					pstmt2.setString(2, pnumber);
					pstmt2.setString(3, request.getSession().getAttribute("user").toString());
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
			if(request.getSession().getAttribute("errorMessage")!= null){
				request.removeAttribute("errorMessage");
			}

			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", pnumber);
			request.getRequestDispatcher("/Home").forward(request, response);
		}

	}
}

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
@WebServlet("/AcctManagement")
public class AcctManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AcctManagement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/AcctManagement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Create = request.getParameter("Create");
		String Search = request.getParameter("Search");
		if(Search == null)
		{
			String firstName = request.getParameter("firstName").replace(" ", "");
			String lastName = request.getParameter("lastName").replace(" ", "");
			String username = request.getParameter("username").replace(" ", "");
			String password = request.getParameter("password").replace(" ", ""); 
			String email = request.getParameter("email").replace(" ", "");
			String phoneNumber = request.getParameter("phoneNumber");
			String Position = request.getParameter("Position").replace(" ", ""); 

			if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || Position.isEmpty() || username.isEmpty())
			{
				request.getSession().setAttribute("errorMessage", "Some fields are missing!");
				request.getRequestDispatcher("/WEB-INF/AcctManagement.jsp").forward(request, response);
			}
			else if( phoneNumber.length() < 14){
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


					String insert_user = "insert into users (firstname, lastname, pass, username, phone, email, position) values(?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement pstmt2 = c.prepareStatement(insert_user);
					pstmt2.setString(1, firstName);
					pstmt2.setString(2, lastName);
					pstmt2.setString(3, org.apache.commons.codec.digest.DigestUtils.sha256Hex(password));
					pstmt2.setString(4, username);
					pstmt2.setString(5, phoneNumber);
					pstmt2.setString(6, email);
					pstmt2.setInt(7, Integer.parseInt(Position));
					pstmt2.execute();
					System.out.println("Hello");

					c.close();

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
			}
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);	
		}

	}
}

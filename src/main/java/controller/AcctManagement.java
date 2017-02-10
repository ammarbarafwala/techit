package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import function.RetrieveData;

@WebServlet("/AcctManagement")
public class AcctManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RetrieveData rd = null;
		if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
			rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
		}
		else{
			rd = new RetrieveData();
		}
		request.setAttribute("userList", rd.getAllUsers());
		request.setAttribute("positionList", Arrays.asList("USER", "TECHNICIAN", "SUPERVISING TECHNICIAN", "SYSTEM ADMINISTRATOR"));
		request.setAttribute("unitList", rd.getAllUnits());		
		request.getRequestDispatcher("/WEB-INF/AcctManagement.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//String Create = request.getParameter("Create");
		String Search = request.getParameter("Search");
		if(Search == null)
		{
			String firstName = request.getParameter("firstName").replace(" ", "");
			String lastName = request.getParameter("lastName").replace(" ", "");
			String username = request.getParameter("username").replace(" ", "");
			String password = request.getParameter("password").replace(" ", ""); 
			String email = request.getParameter("email").replace(" ", "");
			String phoneNumber = request.getParameter("phoneNumber");
			String Position = request.getParameter("Position"); 
			int UnitId = Integer.parseInt(request.getParameter("units").toString());

			if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || Position.isEmpty() || username.isEmpty())
			{

				request.setAttribute("positionList", Arrays.asList("USER", "TECHNICIAN", "SUPERVISING TECHNICIAN", "SYSTEM ADMINISTRATOR"));
				request.setAttribute("errorMessage", "Some fields are missing!");
				request.getRequestDispatcher("/WEB-INF/AcctManagement.jsp").forward(request, response);
			}
			else if( phoneNumber.length() < 14){

				request.setAttribute("positionList", Arrays.asList("USER", "TECHNICIAN", "SUPERVISING TECHNICIAN", "SYSTEM ADMINISTRATOR"));
				request.setAttribute("errorMessage", "Incorrect phone number format!");
				request.getRequestDispatcher("/WEB-INF/AcctManagement.jsp").forward(request, response);

			}
			else{
				Connection c = null;
				PreparedStatement pstmt2 = null;
				
				int position;
				
				if(Position.equals("SYSTEM ADMINISTRATOR")){
					position = 0;
				}
				else if(Position.equals("SUPERVISING TECHNICIAN")){
					position = 1;
				}
				else if(Position.equals("TECHNICIAN")){
					position = 2;
				}
				else{
					position = 3;
				}
				
				try
				{
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
					String insert_user = "insert into users (firstname, lastname, pass, username, phone, email, position, unit_id) values(?, ?, ?, ?, ?, ?, ?,?)";
					pstmt2 = c.prepareStatement(insert_user);
					pstmt2.setString(1, firstName);
					pstmt2.setString(2, lastName);
					pstmt2.setString(3, org.apache.commons.codec.digest.DigestUtils.sha256Hex(password));
					pstmt2.setString(4, username);
					pstmt2.setString(5, phoneNumber);
					pstmt2.setString(6, email);
					pstmt2.setInt(7, position);
					pstmt2.setInt(8, UnitId);

					pstmt2.execute();

					pstmt2.close();
					c.close();

				} catch (SQLException e) {

					DbUtils.closeQuietly(pstmt2);
					DbUtils.closeQuietly(c);
					
					e.printStackTrace();
				}finally
				{
					DbUtils.closeQuietly(pstmt2);
					DbUtils.closeQuietly(c);
				}
				if(request.getSession().getAttribute("errorMessage")!= null){
					request.removeAttribute("errorMessage");
				}
			}
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);	
		}

	}
}

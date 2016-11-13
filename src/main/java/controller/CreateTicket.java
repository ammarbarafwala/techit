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

import org.apache.commons.dbutils.DbUtils;

import function.SendEmail;

import function.RetrieveData;

@WebServlet("/CreateTicket")
public class CreateTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("Login");
		}
		else{
			RetrieveData rd = new RetrieveData();
			request.setAttribute("unitList", rd.getAllUnits());
			request.getRequestDispatcher("/WEB-INF/CreateTicket.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String user = request.getSession().getAttribute("user").toString();
		int UnitId = Integer.parseInt(request.getSession().getAttribute("unit_id").toString());
		int position = Integer.parseInt(request.getSession().getAttribute("position").toString());

		String firstName = request.getParameter("firstName").replace(" ", "");
		String lastName = request.getParameter("lastName").replace(" ", "");
		String email = request.getParameter("email").replace(" ", "");
		String phoneNumber = request.getParameter("phoneNumber");
		String details = request.getParameter("details");
		String location = request.getParameter("location");
		System.out.println(request.getParameter("units"));
		int units = Integer.parseInt(request.getParameter("units"));
		// java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || details.isEmpty()
				|| location.isEmpty()) {
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("details", details);
			request.getSession().setAttribute("location", location);
			request.getSession().setAttribute("errorMessage", "Some fields are missing!");
			request.getRequestDispatcher("/WEB-INF/CreateTicket.jsp").forward(request, response);
		} else if (phoneNumber.length() < 14) {
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("errorMessage", "Incorrect phone number format!");
			request.getRequestDispatcher("/WEB-INF/CreateTicket.jsp").forward(request, response);

		} else {
			Connection c = null;
			PreparedStatement pstmt = null;
			try {
				String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
				String db_user = "cs4961stu01";
				String db_pass = ".XCGG1Bc";

				c = DriverManager.getConnection(url, db_user, db_pass);
				String createTicket = "insert into tickets (username,userFirstName,userLastName,phone, email,unitId, details,startDate,lastUpdated, ticketLocation) values (?,?,?,?,?,?,?,NOW(),NOW(),?)";
				pstmt = c.prepareStatement(createTicket);
				pstmt.setString(1, request.getSession().getAttribute("user").toString());
				pstmt.setString(2, firstName);
				pstmt.setString(3, lastName);
				pstmt.setString(4, phoneNumber);
				pstmt.setString(5, email);
				pstmt.setInt(6, units);
				pstmt.setString(7, details);
				pstmt.setString(8, location);
				pstmt.executeUpdate();
				
				pstmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {

				DbUtils.closeQuietly(pstmt);
				DbUtils.closeQuietly(c);
			}
			if (request.getSession().getAttribute("errorMessage") != null) {
				request.removeAttribute("errorMessage");
			}
			
			SendEmail se = new SendEmail();
			se.sendEmail(email, "New Ticket Created", details);
			RetrieveData rd = new RetrieveData();
			
			try {
				request.getSession().setAttribute("tickets", rd.getUserTicket(user, position, UnitId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("Home");
		}
	}
}

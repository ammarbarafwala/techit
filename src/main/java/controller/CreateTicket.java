package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
			RetrieveData rd = null;
			if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
				rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
			}
			else{
				String dbURL = request.getServletContext().getAttribute("dbURL").toString();
				String dbUser = request.getServletContext().getAttribute("dbUser").toString();
				String dbPass = request.getServletContext().getAttribute("dbPass").toString();
				rd = new RetrieveData(dbURL, dbUser, dbPass);
			}
			request.setAttribute("unitList", rd.getAllUnits());
			request.getRequestDispatcher("/WEB-INF/CreateTicket.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RetrieveData rd = null;
		if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
			rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
		}
		else{
			String dbURL = request.getServletContext().getAttribute("dbURL").toString();
			String dbUser = request.getServletContext().getAttribute("dbUser").toString();
			String dbPass = request.getServletContext().getAttribute("dbPass").toString();
			rd = new RetrieveData(dbURL, dbUser, dbPass);
		}
		
		String user = request.getSession().getAttribute("user").toString();
		int UnitId = Integer.parseInt(request.getSession().getAttribute("unit_id").toString());
		int position = Integer.parseInt(request.getSession().getAttribute("position").toString());

		String firstName = request.getParameter("firstName").replace(" ", "");
		String lastName = request.getParameter("lastName").replace(" ", "");
		String email = request.getParameter("email").replace(" ", "");
		String phoneNumber = request.getParameter("phoneNumber");
		String details = request.getParameter("details");
		String location = request.getParameter("location");
		String department = "";
		if(!request.getParameter("department").isEmpty())
		{
			department = request.getParameter("department").toString();
		}
		
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
				if(Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString()))
				{
					c = ((DataSource)request.getServletContext().getAttribute("dbSource")).getConnection();
				}
				else{
					String dbURL = request.getServletContext().getAttribute("dbURL").toString();
					String dbUser = request.getServletContext().getAttribute("dbUser").toString();
					String dbPass = request.getServletContext().getAttribute("dbPass").toString();

					c = DriverManager.getConnection(dbURL, dbUser, dbPass);
				}
				

				// Get current time
				java.util.Date dt = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				// Query
				String createTicket = "insert into tickets (username,userFirstName,userLastName, phone, email, department, unitId, details,"
						+ "startDate,lastUpdated, ticketLocation) values (?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = c.prepareStatement(createTicket);
				pstmt.setString(1, request.getSession().getAttribute("user").toString());
				pstmt.setString(2, firstName);
				pstmt.setString(3, lastName);
				pstmt.setString(4, phoneNumber);
				pstmt.setString(5, email);
				pstmt.setString(6, department);
				pstmt.setInt(7, units);
				pstmt.setString(8, details);
				pstmt.setString(9, currentTime);
				pstmt.setString(10, currentTime);
				pstmt.setString(11, location);
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
			
			String domain = request.getServletContext().getAttribute("domain").toString();

			String link = "";
			
			int id = rd.getTicketIdFromUsernameUsingTime(request.getSession().getAttribute("user").toString());
			if(id != 0){
				link = domain + "Details?id=" + id;
			}
			else{
				link = domain + "Home";
			}
			
			
			final String emailDetails = "Ticket Creator: " + firstName + " " + lastName + "\n "
					+ "Phone number: " + phoneNumber + "\n"
					+ "Email: " + email + "\n"
					+ "Location of Problem: " + location + "\n"
					+ "Details: " + details + "\n"
					+ link;
			
			final List<String> emails = rd.getSupervisorEmails(units);
			final String emailSubject = "TECHIT - Ticket #" + id + " has been created.";
			
			if(emails.size() > 0){
				new Thread(new Runnable(){
					public void sendEmail(){
					SendEmail se = new SendEmail();
					se.sendMultipleEmail( (Session) getServletContext().getAttribute("session"),
							getServletContext().getAttribute("email").toString(),
							emails, emailSubject, emailDetails);
					}
					public void run(){
						this.sendEmail();
					}
				}).start();
			}
			
			try {
				request.getSession().setAttribute("tickets", rd.getUserTicket(user, position, UnitId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(id != 0){
				request.getSession().setAttribute("pSuccessMessage", "Successfully created a new ticket!");
				response.sendRedirect("Details?id="+id);
			}
			else{
				response.sendRedirect("Home");
			}
		}
	}
}

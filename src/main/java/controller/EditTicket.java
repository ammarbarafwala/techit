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
import model.Ticket;

@WebServlet("/EditTicket")
public class EditTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		int position = (int) request.getSession().getAttribute("position");
		RetrieveData rd = null;
		if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
			rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
		}
		else{
			rd = new RetrieveData();
		}
		Ticket ticket = null;
		try {
			ticket = rd.getTicket(id);
			if (request.getSession().getAttribute("user") == null) {
				response.sendRedirect("Login");
			}
			else if(!ticket.getUser().equals(request.getSession().getAttribute("user")) && !(position <= 1)){
				request.setAttribute("errorMessage", "Invalid ticket request!");
				request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
			}
			else{
				System.out.println(ticket.getEmail());
				request.setAttribute("unitList", rd.getAllUnits());
				request.setAttribute("ticket", ticket);
				request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Something went wrong when getting the ticket, please try again later!");
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
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
		int units = Integer.parseInt(request.getParameter("units"));
		int id = Integer.parseInt(request.getParameter("id"));
		
		RetrieveData rd = null;
		if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
			rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
		}
		else{
			rd = new RetrieveData();
		}
		
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
			
			request.setAttribute("unitList", rd.getAllUnits());
			
			request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);
		} else if (phoneNumber.length() < 14) {
			request.getSession().setAttribute("lastName", lastName);
			request.getSession().setAttribute("firstName", firstName);
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("phoneNumber", phoneNumber);
			request.getSession().setAttribute("errorMessage", "Incorrect phone number format!");
			
			request.setAttribute("unitList", rd.getAllUnits());
			
			request.getRequestDispatcher("/WEB-INF/EditTicket.jsp").forward(request, response);

		} else {
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
				String createTicket = "update tickets set userFirstName = ? , userLastName =? , phone =? , email = ?, details = ?, lastUpdated = NOW() ,ticketLocation = ?, unitId =? where id =?";
				pstmt = c.prepareStatement(createTicket);
				pstmt.setString(1, firstName);
				pstmt.setString(2, lastName);
				pstmt.setString(3, phoneNumber);
				pstmt.setString(4, email);
				pstmt.setString(5, details);
				pstmt.setString(6, location);
				pstmt.setInt(7, units);
				pstmt.setInt(8, id);
				pstmt.executeUpdate();
				pstmt.close();
				c.close();

				request.getSession().setAttribute("tickets",
						rd.getUserTicket(request.getSession().getAttribute("user").toString(),
								Integer.parseInt(request.getSession().getAttribute("position").toString()),
								Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DbUtils.closeQuietly(pstmt);
				DbUtils.closeQuietly(c);
			}
			if (request.getSession().getAttribute("errorMessage") != null) {
				request.removeAttribute("errorMessage");
			}
			try {
				request.getSession().setAttribute("tickets", rd.getUserTicket(user, position, UnitId));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("Home");
		}
	}

}

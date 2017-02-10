package controller;

import java.io.IOException;
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
@WebServlet("/Details")
public class Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			RetrieveData rd = null;
			if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
				rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
			}
			else{
				rd = new RetrieveData();
			}
			Ticket ticket = null;
			
			ticket = rd.getTicket(id);
			if (request.getSession().getAttribute("user") == null ) {
				response.sendRedirect("Login");
			}
			else if(!ticket.getUser().equals(request.getSession().getAttribute("user").toString()) 
					&& !(ticket.isTechnician(request.getSession().getAttribute("user").toString()))
					&& !((int)request.getSession().getAttribute("position") <= 1)){
				request.setAttribute("errorMessage", "Invalid ticket request!");
				request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
			}
			else{
				System.out.println(ticket.getEmail());
				request.setAttribute("unitList", rd.getAllUnits());
				request.setAttribute("ticket", ticket);
				request.getRequestDispatcher("/WEB-INF/Details.jsp").forward(request, response);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Something went wrong when getting the ticket, please try again later!");
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
		} catch (Exception e){
			e.printStackTrace();
			request.setAttribute("errorMessage", "Invalid request! Please try again later! ");
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

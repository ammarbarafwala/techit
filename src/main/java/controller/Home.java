package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getSession().getAttribute("user") == null) {
			request.getRequestDispatcher("/Login").forward(request, response);
		}else if(request.getSession().getAttribute("firstname").toString().isEmpty() 
				|| request.getSession().getAttribute("lastname").toString().isEmpty()  
				|| request.getSession().getAttribute("email").toString().isEmpty()  
				|| request.getSession().getAttribute("phoneNumber").toString().isEmpty())
		{
			response.sendRedirect("FirstLoginUpdate");
		}
		else {
			request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

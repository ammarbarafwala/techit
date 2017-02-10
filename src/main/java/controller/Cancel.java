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

import function.RetrieveData;
import function.SendEmail;
import function.StringFilter;
import model.Ticket;

@WebServlet("/Cancel")
public class Cancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Does nothing, no page
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("cancelBt"));
		RetrieveData rd = null;
		if (Boolean.valueOf(request.getServletContext().getAttribute("onServer").toString())){
			rd = new RetrieveData((DataSource)request.getServletContext().getAttribute("dbSource"));
		}
		else{
			rd = new RetrieveData();
		}
		StringFilter sf = new StringFilter();
		String rejected = sf.filterNull(request.getParameter("rejectInput"));
		
		Connection c = null;
		PreparedStatement pstmt = null;
		PreparedStatement insertUpdate = null;
		String insertQuery = "insert into updates (ticketId, modifier, updateDetails, modifiedDate) values (?, ?, ?, ?) ";
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
			String cancel = "update tickets set Progress = ? where id = ?";
            pstmt = c.prepareStatement( cancel );
            pstmt.setInt( 1, 4 );
            pstmt.setInt( 2, id );
            pstmt.executeUpdate();	       
            
            
			// Get current time
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
            // Insert into new update

        	Ticket ticket = rd.getTicket(id);
        	System.out.println(ticket==null);
        	System.out.println(rejected);
        	
        	String domain = request.getServletContext().getAttribute("domain").toString();
        	
            if(rejected.isEmpty()){
            	// This means that the user canceled the ticket
            	insertUpdate = c.prepareStatement(insertQuery);
            	insertUpdate.setInt(1, id);
            	insertUpdate.setString(2, request.getSession().getAttribute("user").toString());
            	insertUpdate.setString(3, "The ticket has been canceled by the requestor.");
            	insertUpdate.setString(4, currentTime);
            	insertUpdate.executeUpdate();
            	
            	
            	
            	final String subjectDetails = "Ticket #" + id + " was canceled by the requestor.";
            	final String emailDetails = "The following ticket was canceled by the requestor. \n" + ticket.toString() + "\n" 
            			+ "\n" + domain + "Details?id=" + id;
            	final List<String> requestorEmail = rd.getSupervisorEmails(ticket.getUnitId());
            	if(requestorEmail.size() > 0){
	            	new Thread(new Runnable(){
	    				public void sendEmail(){
	    				SendEmail se = new SendEmail();
	    				se.sendMultipleEmail( (Session) getServletContext().getAttribute("session"),
	    						getServletContext().getAttribute("email").toString(),
	    						requestorEmail, subjectDetails, emailDetails);
	    				}
	    				public void run(){
	    					this.sendEmail();
	    				}
	    			}).start();
            	}
            }
            else{
            	// This means the supervisor declined/rejected the ticket
            	insertUpdate = c.prepareStatement(insertQuery);
            	insertUpdate.setInt(1, id);
            	insertUpdate.setString(2, request.getSession().getAttribute("user").toString());
            	insertUpdate.setString(3, "The ticket has been closed by the supervisor. Reason: " + rejected);
            	insertUpdate.setString(4, currentTime);
            	insertUpdate.executeUpdate();
            	
            	
            	final String subjectDetails = "Your ticket #" + id + " was declined by a supervisor.";
            	final String emailDetails = "Your following ticket was declined by a supervisor. \n" + ticket.toString() + "\n"
            			+"Reason: " + rejected + "\n" + domain + "Details?id=" + id;
            	final String requestorEmail = sf.filterNull(rd.getRequestorEmailFromTicket(id));
            	if(!requestorEmail.isEmpty()){
	            	new Thread(new Runnable(){
	    				public void sendEmail(){
	    				SendEmail se = new SendEmail();
	    				se.sendEmail( (Session) getServletContext().getAttribute("session"),
	    						getServletContext().getAttribute("email").toString(),
	    						requestorEmail, subjectDetails, emailDetails);
	    				}
	    				public void run(){
	    					this.sendEmail();
	    				}
	    			}).start();
            	}
            }
            
            pstmt.close();
            insertUpdate.close();
            c.close();
			request.getSession().setAttribute("tickets", rd.getUserTicket(request.getSession().getAttribute("user").toString(), 
					Integer.parseInt(request.getSession().getAttribute("position").toString()), 
					Integer.parseInt(request.getSession().getAttribute("unit_id").toString())));
		}
		catch(SQLException e){
			request.setAttribute("errorMessage", "Something went wrong during cancelation, please try again later!");
			e.printStackTrace();
		}finally{
				DbUtils.closeQuietly(pstmt);
				DbUtils.closeQuietly(insertUpdate);
				DbUtils.closeQuietly(c);
		}
		request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
	}

}

package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Ticket;
import model.Update;
import model.User;
public class RetrieveTicket {
	
	public RetrieveTicket(){
		
	}
	
	//username is checked by email
	public ArrayList<Ticket> getUserTicket(String username, int position) throws SQLException{
		
		String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
		String db_user = "cs4961stu01";
		String db_pass = ".XCGG1Bc";
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		RetrieveUpdates rtup = new RetrieveUpdates();
		
		Connection c = null;
		try{
			c = DriverManager.getConnection(url, db_user, db_pass);
			
			String search_user = "select * from tickets where mail = ?";
            PreparedStatement pstmt = c.prepareStatement( search_user );
            pstmt.setString( 1, username );
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
            	
            	int id = rs.getInt("id");
            	String usernameRequestor = rs.getString("usernameRequestor");
            	int technicianId = rs.getInt("id");
        		String phone = rs.getString("phone");
        		String email = rs.getString("mail");
        		int currentProgress = rs.getInt("Progress");
        		String details = rs.getString("details");
        		Date startDate = rs.getDate("startDate");
        		Date endDate = rs.getDate("endDate");
        		String ticketLocation = rs.getString("ticketLocation");
        		List<Update> updates = rtup.getTicketUpdates(id);
        		String completionDetails = rs.getString("completeDetails");
            	
        		Ticket newTicket = new Ticket(id, usernameRequestor, technicianId, phone, email, currentProgress, details, startDate, endDate, ticketLocation, updates, completionDetails);
        		
        		tickets.add(newTicket);
            }
            	
		}finally{
			if(c != null){
				c.close();
			}
		}
		
		return tickets;
	}

}

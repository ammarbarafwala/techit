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

public class RetrieveUpdates {

	public RetrieveUpdates(){
		
	}
	
	public ArrayList<Update> getTicketUpdates(int ticketId) throws SQLException{
		String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
		String db_user = "cs4961stu01";
		String db_pass = ".XCGG1Bc";
		ArrayList<Update> tickets = new ArrayList<Update>();
		
		Connection c = null;
		try{
			c = DriverManager.getConnection(url, db_user, db_pass);
			
			String search_update = "select * from updates where ticketId = " + ticketId;
            PreparedStatement pstmt = c.prepareStatement( search_update );
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
            	
            	int id = rs.getInt("id");
            	int tId = rs.getInt("ticketId");
            	String modifier = rs.getString("modifier");
            	String modifierDetails = rs.getString("updateDetails");
            	Date modifiedDate = rs.getDate("modifiedDate");
            	
            	Update updt = new Update(id, tId, modifier, modifierDetails, modifiedDate);
            	tickets.add(updt);
            }
            	
		}finally{
			if(c != null){
				c.close();
			}
		}
		
		return tickets;
	}
}

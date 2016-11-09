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

public class RetrieveData {
	private final String url = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
	private final String db_user = "cs4961stu01";
	private final String db_pass = ".XCGG1Bc";

	public ArrayList<Ticket> getUserTicket(String username, int position) throws SQLException {

		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		List<User> techs = new ArrayList<User>();
		RetrieveUpdates rtup = new RetrieveUpdates();

		Connection c = null;
		try {
			c = DriverManager.getConnection(url, db_user, db_pass);

			String search_user = "select * from tickets where username = ?";
			PreparedStatement pstmt = c.prepareStatement(search_user);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
				String usernameRequestor = rs.getString("username");
				String userFirstName = rs.getString("userFirstName");
				String userLastName = rs.getString("userLastName");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				int currentProgress = rs.getInt("Progress");
				int unitId = rs.getInt("unitId");
				String details = rs.getString("details");
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				Date lastUpdated = rs.getDate("lastUpdated");
				String lastUpdatedTime = "";
				String ticketLocation = rs.getString("ticketLocation");
				List<Update> updates = getTicketUpdates(id);
				String completionDetails = "";

				Ticket newTicket = new Ticket(id, usernameRequestor, userFirstName, userLastName, techs, phone, email,
						currentProgress, unitId, details, startDate, endDate, lastUpdated, lastUpdatedTime,
						ticketLocation, updates, completionDetails);

				tickets.add(newTicket);
			}

		} finally {
			if (c != null) {
				c.close();
			}
		}

		return tickets;
	}

	public ArrayList<Update> getTicketUpdates(int ticketId) throws SQLException {

		ArrayList<Update> tickets = new ArrayList<Update>();

		Connection c = null;
		try {
			c = DriverManager.getConnection(url, db_user, db_pass);

			String search_update = "select * from updates where ticketId = ?";
			PreparedStatement pstmt = c.prepareStatement(search_update);
			pstmt.setInt(1, ticketId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
				int tId = rs.getInt("ticketId");
				String modifier = rs.getString("modifier");
				String modifierDetails = rs.getString("updateDetails");
				Date modifiedDate = rs.getDate("modifiedDate");

				Update updt = new Update(id, tId, modifier, modifierDetails, modifiedDate);
				tickets.add(updt);
			}

		} finally {
			if (c != null) {
				c.close();
			}
		}

		return tickets;
	}
	
	public List<User> getTechnicians(int ticketId) throws SQLException{
		List<User> userList = new ArrayList<User>();

		Connection c = null;
		try {
			c = DriverManager.getConnection(url, db_user, db_pass);

			String search_update = "select technicianUser from assignments where ticketId = ?";
			PreparedStatement pstmt = c.prepareStatement(search_update);
			pstmt.setInt(1, ticketId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				String getUser = "select * from users where username = ?";
				PreparedStatement pstmt2 = c.prepareStatement(getUser);
				pstmt2.setString(1, rs.getString("technicianUser").toString());
				ResultSet rs2 = pstmt2.executeQuery();
				
				if(rs2.next()){
					
				}
				
			}

		} finally {
			if (c != null) {
				c.close();
			}
		}
		return userList;
	}
}

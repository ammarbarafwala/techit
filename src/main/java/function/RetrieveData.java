package function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import model.Ticket;
import model.Unit;
import model.Update;
import model.User;

public class RetrieveData {
	
	private DataSource datasource;
	private final String dbUrl = "jdbc:mysql://cs3.calstatela.edu/cs4961stu01";
	private final String dbUser = "cs4961stu01";
	private final String dbPass = ".XCGG1Bc"; 
	
	public RetrieveData(){ // Constructor meant for localhosts
		this.datasource = null;
	}
	public RetrieveData(DataSource datasource){
		this.datasource = datasource;
	}

	public Ticket getFullTicket(int id){
		Ticket ticket = null;
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			String search_user = "select * from tickets where id = ?";
			pstmt = c.prepareStatement(search_user);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				ticket = new Ticket(rs.getInt("id"), rs.getString("username"), rs.getString("userFirstName"),
						rs.getString("userLastName"), getTechnicians(id) ,rs.getString("phone"), rs.getString("email"),rs.getInt("Progress"),
						rs.getInt("unitId"), rs.getString("details"), rs.getDate("startDate"), rs.getDate("endDate"),rs.getDate("lastUpdated"), 
						rs.getTime("lastUpdated").toString(), rs.getString("ticketLocation"), getTicketUpdates(id), "");
				
			}
			pstmt.close();
			rs.close();
			c.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}

		return ticket;
	}
	
	public int getID(String username, String details, String location){
		int id = 0;
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			String search_user = "select id from tickets where username = ? and details = ? and ticketLocation = ?";
			pstmt = c.prepareStatement(search_user);
			pstmt.setString(1, username);
			pstmt.setString(2, details);
			pstmt.setString(3, location);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				id = rs.getInt("id");
				
			}
			pstmt.close();
			rs.close();
			c.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}

		return id;
	}
	
	public List<Ticket> getUserTicket(String username, int position, int unit_id) throws SQLException {

		List<Ticket> tickets = new ArrayList<Ticket>();
		String search_user = "";

		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}

			if (position == 0) { // System Administrator
				search_user = "select * from tickets order by lastUpdated desc";
				pstmt = c.prepareStatement(search_user);

			}
			else if (position == 1) { // Supervising Technician
				search_user = "select * from tickets where unitId = ? order by lastUpdated desc";
				pstmt = c.prepareStatement(search_user);
				pstmt.setInt(1, unit_id);
			} 
			else if (position == 2) { // Technician
				search_user = "select t.id, t.username, t.userFirstName, t.userLastname, t.phone, t.email, t.Progress, t.unitId, t.details, t.startDate, t.endDate,"
						+ " t.lastUpdated, t.ticketLocation from tickets t left join assignments u on t.id = u.ticketId"
						+ " where t.username = ? or u.technicianUser = ? order by t.lastUpdated desc";

				pstmt = c.prepareStatement(search_user);
				pstmt.setString(1, username);
				pstmt.setString(2, username);


			} 
			else { // Regular users
				search_user = "select * from tickets where username = ? order by lastUpdated desc";
				pstmt = c.prepareStatement(search_user);
				pstmt.setString(1, username);
			}

			rs = pstmt.executeQuery();
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
				String lastUpdatedTime = rs.getTime("lastUpdated").toString();
				String ticketLocation = rs.getString("ticketLocation");
				String completionDetails = "";

				Ticket newTicket = new Ticket(id, usernameRequestor, userFirstName, userLastName, getTechnicians(id), phone, email,
						currentProgress, unitId, details, startDate, endDate, lastUpdated, lastUpdatedTime,
						ticketLocation, getTicketUpdates(id), completionDetails);

				tickets.add(newTicket);
			}
			
			pstmt.close();
			rs.close();
			c.close();
		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}

		return tickets;
	}

	public List<Ticket> searchTicket(String term, String username, int position, int unit_id) throws SQLException {
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		String search_user = "";
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}

			if (position == 0) { // System Administrator
				search_user = "select * from tickets where CONCAT_WS('', username, userFirstName, userLastName, phone, email, Progress, unitId, "
						+ "details, startDate, endDate, lastUpdated, ticketLocation) like ? order by lastUpdated desc";
				pstmt = c.prepareStatement(search_user);
				pstmt.setString(1, '%' + term + '%');

			}
			else if (position == 1) { // Supervising Technician
				search_user = "select * from tickets where unitId = ? and CONCAT_WS('', username, userFirstName, userLastName, phone, email,"
						+ " Progress, unitId, details, startDate, endDate, lastUpdated, ticketLocation) like ? order by lastUpdated desc";
				pstmt = c.prepareStatement(search_user);
				pstmt.setInt(1, unit_id);
				pstmt.setString(2, '%' + term + '%');
				
			} 
			else if (position == 2) { // Technician
				search_user = "select t.id, t.username, t.userFirstName, t.userLastname, t.phone, t.email, t.Progress, t.unitId, t.details, "
						+ "t.startDate, t.endDate, t.lastUpdated, t.ticketLocation from tickets t left join assignments u on t.id = u.ticketId "
						+ "where (t.username = ? or u.technicianUser = ?) and CONCAT_WS('',  t.id, t.username, t.userFirstName, t.userLastname, t.phone, "
						+ "t.email, t.Progress, t.unitId, "
						+ "t.details, t.startDate, t.endDate, t.lastUpdated, t.ticketLocation) like ? order by t.lastUpdated desc";

				pstmt = c.prepareStatement(search_user);
				pstmt.setString(1, username);
				pstmt.setString(2, username);
				pstmt.setString(3, '%' + term + '%');

			} 
			else { // Regular users
				search_user = "select * from tickets where username = ? and CONCAT_WS('', username, userFirstName, userLastName, phone, email, "
						+ "Progress, unitId, details, startDate, endDate, lastUpdated, ticketLocation) like ? order by lastUpdated desc";
				pstmt = c.prepareStatement(search_user);
				pstmt.setString(1, username);
				pstmt.setString(2, '%' + term + '%');
			}

			rs = pstmt.executeQuery();
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
				String lastUpdatedTime = rs.getTime("lastUpdated").toString();
				String ticketLocation = rs.getString("ticketLocation");
				String completionDetails = "";

				Ticket newTicket = new Ticket(id, usernameRequestor, userFirstName, userLastName, getTechnicians(id), phone, email,
						currentProgress, unitId, details, startDate, endDate, lastUpdated, lastUpdatedTime,
						ticketLocation, getTicketUpdates(id), completionDetails);

				tickets.add(newTicket);
			}
			pstmt.close();
			rs.close();
			c.close();

		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}

		return tickets;
	}
	
	public User getUser(int userId){
		User user = null;
		
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			String getUser = "select * from users where id = ?";
			pstmt = c.prepareStatement(getUser);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				user = new User(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("username"), rs.getString("phone"), rs.getString("email"),
						rs.getInt("position"), rs.getInt("unit_id"));
			}
			
			pstmt.close();
			rs.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		return user;
	}
	public Unit getUnit(int unitId){
		Unit unit = null;
		
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			String getUser = "select * from units where id = ?";
			pstmt = c.prepareStatement(getUser);
			pstmt.setInt(1, unitId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				unit = new Unit(rs.getInt("id"), rs.getString("unitName"), rs.getString("phone"),
						rs.getString("location"), rs.getString("email"), rs.getString("description"));
			}
			
			pstmt.close();
			rs.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		return unit;
	}
	
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<User>();
		
		String getUser = "";
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			getUser = "select * from users";
			pstmt = c.prepareStatement(getUser);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				users.add(new User(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("username"), rs.getString("phone"), rs.getString("email"),
						rs.getInt("position"), rs.getInt("unit_id")));
			}
			
			pstmt.close();
			rs.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		return users;
	}
	
/*	public List<Update> getTicketUpdates(Connection c, int ticketId){
		List<Update> updates = new ArrayList<Update>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			String search_update = "select * from updates where ticketId = ? order by modifiedDate desc";
			pstmt = c.prepareStatement(search_update);
			pstmt.setInt(1, ticketId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
				int tId = rs.getInt("ticketId");
				String modifier = rs.getString("modifier");
				String modifierDetails = rs.getString("updateDetails");
				String modifiedDate = rs.getTimestamp("modifiedDate").toString();

				Update updt = new Update(id, tId, modifier, modifierDetails, modifiedDate);
				updates.add(updt);
			}
			
			pstmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
		}
		return updates;
	}
*/
	public List<Update> getTicketUpdates(int ticketId) throws SQLException {

		List<Update> tickets = new ArrayList<Update>();

		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}

			String search_update = "select * from updates where ticketId = ? order by modifiedDate desc";
			pstmt = c.prepareStatement(search_update);
			pstmt.setInt(1, ticketId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				int id = rs.getInt("id");
				int tId = rs.getInt("ticketId");
				String modifier = rs.getString("modifier");
				String modifierDetails = rs.getString("updateDetails");
				String modifiedDate = rs.getTimestamp("modifiedDate").toString();

				Update updt = new Update(id, tId, modifier, modifierDetails, modifiedDate);
				tickets.add(updt);
			}
			pstmt.close();
			rs.close();
			c.close();

		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}

		return tickets;
	}
	
	/*public List<User> getTechnicians(Connection c, int ticketId){
		List<User> userList = new ArrayList<User>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			String search_update = "select technicianUser from assignments where ticketId = ?";
			pstmt = c.prepareStatement(search_update);
			pstmt.setInt(1, ticketId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				String getUser = "select * from users where username = ?";
				PreparedStatement pstmt2 = c.prepareStatement(getUser);
				pstmt2.setString(1, rs.getString("technicianUser").toString());
				ResultSet rs2 = pstmt2.executeQuery();

				if (rs2.next()) {
					userList.add(new User(rs2.getInt("id"), rs2.getString("firstname"), rs2.getString("lastname"),
							rs2.getString("username"), rs2.getString("phone"), rs2.getString("email"),
							rs2.getInt("position"), rs2.getInt("unit_id")));
				}

			}
			
			pstmt.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
		}
		
		return userList;
	}
*/	
	public List<User> getTechnicians(int ticketId) throws SQLException {
		List<User> userList = new ArrayList<User>();

		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			String search_update = "select technicianUser from assignments where ticketId = ?";
			pstmt = c.prepareStatement(search_update);
			pstmt.setInt(1, ticketId);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				String getUser = "select * from users where username = ?";
				PreparedStatement pstmt2 = c.prepareStatement(getUser);
				pstmt2.setString(1, rs.getString("technicianUser").toString());
				ResultSet rs2 = pstmt2.executeQuery();

				if (rs2.next()) {
					userList.add(new User(rs2.getInt("id"), rs2.getString("firstname"), rs2.getString("lastname"),
							rs2.getString("username"), rs2.getString("phone"), rs2.getString("email"),
							rs2.getInt("position"), rs2.getInt("unit_id")));
				}

			}
			pstmt.close();
			rs.close();
			c.close();

		} finally {
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		return userList;
	}
	
	public Map<String, Boolean> getTechId(int ticketId){
		Map<String, Boolean> techIds = new HashMap<String, Boolean>();
		
		String search_technician = "select technicianUser from assignments where ticketId = ? ";

		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			pstmt = c.prepareStatement(search_technician);
			pstmt.setInt(1, ticketId);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				techIds.put(rs.getString("technicianUser"), true);
			}
			
			pstmt.close();
			rs.close();
			c.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		
		return techIds;
	}
	
	public Ticket getTicket(int ticketId) throws SQLException{
		Ticket tk = null;
		
		String search_user = "";

		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			search_user = "select * from tickets where id = ?";
			pstmt = c.prepareStatement(search_user);
			pstmt.setInt(1, ticketId);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
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
				String lastUpdatedTime = rs.getTime("lastUpdated").toString();
				String ticketLocation = rs.getString("ticketLocation");
				String completionDetails = "";

				tk = new Ticket(id, usernameRequestor, userFirstName, userLastName, getTechnicians(id), phone, email,
						currentProgress, unitId, details, startDate, endDate, lastUpdated, lastUpdatedTime,
						ticketLocation, getTicketUpdates(id), completionDetails);
			}
			pstmt.close();
			rs.close();
			c.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}		
		finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		
		return tk;
	}
	
	public List<User> getUnitTechnicians(int unitId) throws SQLException{
		List<User> tchl = new ArrayList<User>();
		String search_user = "";
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			search_user = "select * from users where position = ? and unit_id = ?";
			pstmt = c.prepareStatement(search_user);
			pstmt.setInt(1, 2); // Can change this later if its not only technicians
			pstmt.setInt(2, unitId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				tchl.add(new User(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getString("username"), rs.getString("phone"), rs.getString("email"),
						rs.getInt("position"), rs.getInt("unit_id")));
				
			}
			pstmt.close();
			rs.close();
			c.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		return tchl;
	}

	public List<Unit> getAllUnits(){
		Connection c = null;
		PreparedStatement ptsmt = null;
		ResultSet rs = null; 
		
		List<Unit> unitList = new ArrayList<Unit>();
		
		String getUnit = "select * from units";
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			
			ptsmt = c.prepareStatement(getUnit);
			rs = ptsmt.executeQuery();
			
			while(rs.next()){
				unitList.add(new Unit(rs.getInt("id"),
						rs.getString("unitName"),
						rs.getString("phone"),
						rs.getString("location"),
						rs.getString("email"),
						rs.getString("description")
						));
			}
			
			ptsmt.close();
			rs.close();
			c.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		finally{
			DbUtils.closeQuietly(ptsmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		
		return unitList;
		
	}
	
	public List<String> getSupervisorEmails(int unitId){
		List<String> emails = new ArrayList<String>();
		String getEmail = "select email from users where unit_id = ? and position = ? ";

		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			pstmt = c.prepareStatement(getEmail);
			pstmt.setInt(1, unitId);
			pstmt.setInt(2, 1);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				emails.add(rs.getString("email"));
			}
			
			rs.close();
			pstmt.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		return emails;
	}
	
	public String getRequestorEmailFromTicket(int ticketId){
		String email = "";
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String emailQuery = "select email from users where username = (select username from tickets where id = ?)";
		
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			pstmt = c.prepareStatement(emailQuery);
			pstmt.setInt(1, ticketId);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				email = rs.getString("email");
			}
			
			rs.close();
			pstmt.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		
		return email;
	}
	
	public String getEmailFromUsername(String username){
		String email = "";
		Connection c = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String emailQuery = "select email from users where username = ?";
		
		try{
			if(this.datasource == null){
				c = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPass);
			}
			else{
				c = this.datasource.getConnection();
			}
			pstmt = c.prepareStatement(emailQuery);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				email = rs.getString("email");
			}
			
			rs.close();
			pstmt.close();
			c.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtils.closeQuietly(pstmt);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(c);
		}
		
		return email;
	}

}

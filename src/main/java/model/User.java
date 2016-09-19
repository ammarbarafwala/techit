package model;

public class User {
	
	private int id; //User's unique id
	private String firstname; //User's first name
	private String lastname; //User's last name
	private String email; //Username and email are the same.
	private String password; //User's password
	private int CIN; //User's campus ID if applicable
	
	// 0 - System Administrator
	// 1 - Supervisor
	// 2 - Technician
	// 3 - User
	//The lower the number, the more privileges the user has.
	private int position;
	
	private int unit_id; //Describes where the user belongs to in a unit (by id).
	
	
}

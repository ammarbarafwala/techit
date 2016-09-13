package model;

public class User {
	
	private String name; //User's actual name
	private String email; //Username and email are the same.
	private String password; //User's password
	private int CIN; //User's campus ID if applicable
	
	// 0 - System Administrator
	// 1 - Supervisor
	// 2 - Technician
	// 3 - User
	private int position;
	
	private int unit_id; //Describes where the user belongs to in a unit (by id).
	
	
}

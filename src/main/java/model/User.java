package model;

public class User {

	private int id; 				// User's unique id
	private String firstname; 		// User's first name
	private String lastname; 		// User's last name
	private String username; 		// Username and email are the same.
	private int CIN; 				// User's campus ID if applicable

	// Types of users on the system.
	private enum Position {
		SYS_ADMIN(0), SUPERVISING_TECHNICIAN(1), TECHNICIAN(2), USER(3);

		private int positionValue;

		Position(int position_value) {
			this.positionValue = position_value;
		}

		public int getValue() {
			return positionValue;
		}
	};

	private Position status;

	private int unit_id; 		// Describes where the user belongs to in a unit (by

	// Simple constructor for regular users ( students )
	public User(int id, String firstname, String lastname, String username, int CIN) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.CIN = CIN;
		this.status = Position.USER;
		this.unit_id = 0; 				// User does not belongs to any unit\
	}

	// Full user paramenter constructor
	public User(int id, String firstname, String lastname, String username,int CIN, int position, 
			int unit_id)
	{
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.CIN = CIN;
		
		switch(position)
		{
			case 0:
				this.status = Position.SYS_ADMIN;
			case 1:
				this.status = Position.SUPERVISING_TECHNICIAN;
			case 2:
				this.status = Position.TECHNICIAN;
			case 3:									// These assume that you want to enter a new technician
			default: 								// and somehow enter a number thats not intended
				this.status = Position.USER;
		}
		
		this.unit_id = unit_id;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return username + "@calstatela.edu"; // Since username has the same
												// header as email with the
												// "@calstatela.edu" domain
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCIN() {
		return CIN;
	}

	public void setCIN(int cIN) {
		CIN = cIN;
	}

	public int getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(int unit_id) {
		this.unit_id = unit_id;
	}

	public void setStatus(int position) {
		switch (position) {
		case 0:
			this.status = Position.SYS_ADMIN;
			break;
		case 1:
			this.status = Position.SUPERVISING_TECHNICIAN;
			break;
		case 2:
			this.status = Position.TECHNICIAN;
			break;
		case 3:
			this.status = Position.USER;
			break;
		}

	}

	public int getStatus() {
		return status.getValue();
	}

	
}

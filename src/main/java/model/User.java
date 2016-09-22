package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

	private int id; 				// User's unique id
	private String firstname; 		// User's first name
	private String lastname; 		// User's last name
	private String username; 		// Username and email are the same.
	private String password; 		// User's password
	private int CIN; 				// User's campus ID if applicable
	private boolean isSupervisor; 	// Whether if the technician is a supervisor

	// Types of users on the system.
	private enum Position {
		SYS_ADMIN(0), TECHNICIAN(1), USER(2);

		private int position_value;

		Position(int position_value) {
			this.position_value = position_value;
		}

		public int getValue() {
			return position_value;
		}
	};

	private Position status;

	private int unit_id; 		// Describes where the user belongs to in a unit (by
								// id).
	private int supervisor_id;

	// Simple constructor for regular users ( students )
	public User(int id, String firstname, String lastname, String username, String password, int CIN) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		// Hashing the password with SHA256 hash
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.CIN = CIN;
		this.status = Position.USER;
		this.unit_id = 0; 				// User does not belongs to any unit
		this.supervisor_id = 0; 		/* User does not have a supervisor ( not a
										   technician or a supervisor themselves)*/
		this.isSupervisor = false;
	}

	// Full user paramenter constructor
	public User(int id, String firstname, String lastname, String username, String password, int CIN, int position, 
			int unit_id, int supervisor_id, boolean isSupervisor )
	{
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.CIN = CIN;
		// Hashing the password with SHA256 hash
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(position)
		{
			case 0:
				this.status = Position.SYS_ADMIN;
			case 1:									// These assume that you want to enter a new technician
			default: 								// and somehow enter a number thats not intended
				this.status = Position.TECHNICIAN;
		}
		
		this.supervisor_id = supervisor_id;
		this.unit_id = unit_id;
		this.isSupervisor = isSupervisor;
		
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		case 1:
			this.status = Position.TECHNICIAN;
		case 2:
			this.status = Position.USER;
		}

	}

	public int getStatus() {
		return status.getValue();
	}

	public boolean isSupervisor() {
		return isSupervisor;
	}

	public void setSupervisor(boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public int getSupervisor_id() {
		return supervisor_id;
	}

	public void setSupervisor_id(int supervisor_id) {
		this.supervisor_id = supervisor_id;
	}
	
	
}

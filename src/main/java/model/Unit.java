package model;

import java.util.List;

public class Unit {
	private int id; // Unit's unique id.
	private String name; // Name of the department.

	// A unit may have more than one supervisor. This will allow them to assign
	// temporary leads when they are gone.
	private List<User> supervisors;
	private List<User> technicians;
	private String phone;
	private String location;
	private String email;

	public Unit(int id, String name, List<User> supervisors, List<User> technicians, String phone, String location,
			String email) {
		this.id = id;
		this.name = name;
		this.supervisors = supervisors;
		this.technicians = technicians;
		this.phone = phone;
		this.location = location;
		this.email = email;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setContact(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<User> getTechnicians() {
		return technicians;
	}

	public void setTechnicians(List<User> technicians) {
		this.technicians = technicians;
	}

	public void setSupervisors(List<User> supervisors) {
		this.supervisors = supervisors;
	}
	
	public List<User> getSupervisors()
	{
		return supervisors;
	}

}

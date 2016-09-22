package model;

import java.util.ArrayList;

public class Unit {
	private int id; //Unit's unique id.
	private String name; //Name of the department.
	
	//A unit may have more than one supervisor. This will allow them to assign temporary leads when they are gone.
	private ArrayList<User> supervisors;
	private String contact;
	private String address;
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
	public ArrayList<User> getSupervisors() {
		return supervisors;
	}
	public void setSupervisors(ArrayList<User> supervisors) {
		this.supervisors = supervisors;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}

package model;

import java.util.ArrayList;

public class Unit {
	private int id; //Unit's unique id.
	private String name; //Name of the department.
	
	//A unit may have more than one supervisor. This will allow them to assign temporary leads when they are gone.
	private ArrayList<User> supervisors;
	private String contact;
	private String address;
}

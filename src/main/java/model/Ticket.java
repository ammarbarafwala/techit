package model;

import java.util.Date;

public class Ticket {
	private int id; //Ticket's unique id.
	private User requestor; //The user who requested the ticket.
	private User technician; //The user/technician who will respond to the ticket.
	private String phone; //Requestor's phone
	private String email; //Requestor's email. May be different from the User's login email.
	
	//Constant values that apply to the status of a project
	private Status status;
	private String details; //Text concerning the project.
	private Date startDate; //Project's starting date.
	private Date endDate; //Project's completed date.
	private String ticketLocation; //Location where the project is.
	
	//Needs more work...
	private String completionDetails; //Information pertaining vendors, cost, materials used.
}

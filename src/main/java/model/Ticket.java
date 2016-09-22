package model;

import java.util.Date;

public class Ticket {
	private int id; //Ticket's unique id.
	private User requestor; //The user who requested the ticket.
	private User technician; //The user/technician who will respond to the ticket.
	private String phone; //Requestor's phone
	private String email; //Requestor's email. May be different from the User's login email.
	private enum Progress{
		OPEN,
		ASSIGNED,
		INPROGREGSS,
		ONHOLD,
		COMPLETED;
	}
	
	//Constant values that apply to the status of a project
	private Status status;
	private String details; //Text concerning the project.
	private Date startDate; //Project's starting date.
	private Date endDate; //Project's completed date.
	private String ticketLocation; //Location where the project is.
	
	//Needs more work...
	private String completionDetails; //Information pertaining vendors, cost, materials used.

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getRequestor() {
		return requestor;
	}

	public void setRequestor(User requestor) {
		this.requestor = requestor;
	}

	public User getTechnician() {
		return technician;
	}

	public void setTechnician(User technician) {
		this.technician = technician;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTicketLocation() {
		return ticketLocation;
	}

	public void setTicketLocation(String ticketLocation) {
		this.ticketLocation = ticketLocation;
	}

	public String getCompletionDetails() {
		return completionDetails;
	}

	public void setCompletionDetails(String completionDetails) {
		this.completionDetails = completionDetails;
	}
	
	
}

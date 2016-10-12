package model;

import java.util.Date;

public class Update {

	private int id;
	private Ticket ticket;
	
	private String modifier; // modifier's username
	private String updateDetails;
	private Date modifiedDate;
	
	public Update(int id, String modifier, String updateDetails, Date modifiedDate){
		this.id = id;
		this.modifier = modifier;
		this.updateDetails = updateDetails;
		this.modifiedDate = modifiedDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getUpdateDetails() {
		return updateDetails;
	}
	public void setDetails(String details) {
		this.updateDetails = details;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}

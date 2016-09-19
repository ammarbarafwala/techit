package model;

import java.util.Date;

public class Status {

	private int id;
	private String progress;
	private Ticket ticket;
	private enum Progress{
		OPEN,
		ASSIGNED,
		INPROGREGSS,
		ONHOLD,
		COMPLETED;
	}
	
	private User modifier;
	private String details;
	private Date modifiedDate;
}

package com.gulshansingh.gwanyone;

import java.util.Date;

public class Event {
	private String name;
	private Date date;
	private String details;

	public Event(String name, Date date) {
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	public String getDetails() {
		return details;
	}
}

package com.gulshansingh.gwanyone;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	private static final long serialVersionUID = 1299443682558966759L;
	public static final String EXTRA_KEY = "event_key";

	private String name;
	private Date date;
	private String details;

	public Event(String name, Date date, String details) {
		this.name = name;
		this.date = date;
		this.details = details;
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

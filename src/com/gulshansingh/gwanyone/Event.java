package com.gulshansingh.gwanyone;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	private static final long serialVersionUID = 1299443682558966759L;
	public static final String EXTRA_KEY = "event_key";

	private int id;
	private String name;
	private Date date;
	private String details;

	public Event(String name, Date date, String details) {
		this(-1, name, date, details);
	}

	public Event(int id, String name, Date date, String details) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.details = details;
	}

	public int getId() {
		return id;
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

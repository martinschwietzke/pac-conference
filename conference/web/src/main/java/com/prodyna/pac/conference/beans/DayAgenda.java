package com.prodyna.pac.conference.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.prodyna.pac.conference.model.Talk;

public class DayAgenda {

	Date date;
	List<Talk> talks = new ArrayList<Talk>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void addTalk(Talk t) {
		talks.add(t);
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

}

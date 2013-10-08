package com.prodyna.pac.conference.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Talk
 * 
 */
@Entity
public class Talk implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 75)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String name;

	private String description;

	@NotNull
	@Min(1)
	private int duration;

	@NotNull
	private Room room;

	@OneToMany
	@Size(min = 1, message = "Must have at least one speaker")
	private Set<Speaker> speakers;

	@ManyToOne
	@NotNull
	private Conference conference;

	private static final long serialVersionUID = 1L;

	public Talk() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Set<Speaker> getSpeakers() {
		// return a copy to prevent direct access to our list
		return new LinkedHashSet<Speaker>(this.speakers);
	}

	public void addSpeaker(Speaker speaker) {
		this.speakers.add(speaker);
	}

	public void removeSpeaker(Speaker speaker) {
		this.speakers.remove(speaker);
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}
}

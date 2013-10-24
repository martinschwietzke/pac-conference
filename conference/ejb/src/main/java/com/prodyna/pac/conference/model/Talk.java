package com.prodyna.pac.conference.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity Talk
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = Talk.FIND_ALL, query = "SELECT t FROM Talk t"),
		@NamedQuery(name = Talk.FIND_BY_CONFERENCE_ID, query = "SELECT t FROM Talk t WHERE t.conference.id = :conferenceId"),
		@NamedQuery(name = Talk.FIND_BY_ROOM_ID, query = "SELECT t FROM Talk t WHERE t.room.id = :roomId"),
		@NamedQuery(name = Talk.FIND_BY_SPEAKER, query = "SELECT t FROM Talk t WHERE :speaker MEMBER OF t.speakers") })
public class Talk extends AbstractEntity {

	public final static String FIND_ALL = "de.prodyna.pac.conference.model.talk.findAll";
	public final static String FIND_BY_CONFERENCE_ID = "de.prodyna.pac.conference.model.talk.findByConferenceId";
	public final static String FIND_BY_ROOM_ID = "de.prodyna.pac.conference.model.talk.findByRoomId";
	public final static String FIND_BY_SPEAKER = "de.prodyna.pac.conference.model.talk.findSpeakerId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 75)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String name;

	private String description;

	@NotNull
	private Date start;

	@NotNull
	@Min(1)
	private int duration;

	@NotNull
	@OneToOne
	private Room room;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Speaker> speakers;

	@ManyToOne
	@NotNull
	private Conference conference;

	private static final long serialVersionUID = 1L;

	public Talk() {
		super();
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getDuration()
	{
		return this.duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public List<Speaker> getSpeakers()
	{
		// return a copy to prevent direct access to our list
		return new ArrayList<Speaker>(this.speakers);
	}

	public void addSpeaker(Speaker speaker)
	{
		this.speakers.add(speaker);
	}

	public void removeSpeaker(Speaker speaker)
	{
		this.speakers.remove(speaker);
	}

	public Conference getConference()
	{
		return conference;
	}

	public void setConference(Conference conference)
	{
		this.conference = conference;
	}

	public Date getStart()
	{
		return start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}
}

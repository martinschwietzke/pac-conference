package com.prodyna.pac.conference.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Conference
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@NamedQuery(name = Conference.FIND_ALL, query = "SELECT c FROM Conference c")
public class Conference extends AbstractEntity {

	public final static String FIND_ALL = "de.prodyna.pac.conference.model.conference.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 75)
	private String name;

	@NotNull
	@Size(min = 1, max = 75)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String location;

	@Size(max = 255)
	private String description;

	@NotNull
	private Date start;

	@NotNull
	private Date end;

	@NotNull
	private boolean archived;

	private static final long serialVersionUID = 1L;

	public Conference() {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return this.start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

}

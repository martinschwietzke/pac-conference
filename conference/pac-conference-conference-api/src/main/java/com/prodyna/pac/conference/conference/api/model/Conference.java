package com.prodyna.pac.conference.conference.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.common.model.AbstractEntity;
import com.prodyna.pac.conference.common.model.CheckEndDataAfterStartDate;

/**
 * Entity implementation class for Entity: Conference
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@NamedQuery(name = Conference.FIND_ALL, query = "SELECT c FROM Conference c")
@CheckEndDataAfterStartDate(startDate = "start", endDate = "end")
@Table(name = "conference")
public class Conference extends AbstractEntity {

	@Override
	public String toString()
	{
		return "Conference [id=" + id + ", name=" + name + "]";
	}

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

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getStart()
	{
		return this.start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}

	public Date getEnd()
	{
		return this.end;
	}

	public void setEnd(Date end)
	{
		this.end = end;
	}

	public boolean isArchived()
	{
		return archived;
	}

	public void setArchived(boolean archived)
	{
		this.archived = archived;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (archived ? 1231 : 1237);
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conference other = (Conference) obj;
		if (archived != other.archived)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}

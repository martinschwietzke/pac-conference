package com.prodyna.pac.conference.room.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.model.AbstractEntity;

/**
 * Entity implementation class for Entity Room
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = Room.FIND_ALL, query = "SELECT r FROM Room r"),
		@NamedQuery(name = Room.COUNT_REFERENCING_TALKS, query = "SELECT COUNT(t) FROM Talk t WHERE t.room.id = :roomId") })
public class Room extends AbstractEntity {

	public final static String FIND_ALL = "de.prodyna.pac.conference.model.room.findAll";
	public final static String COUNT_REFERENCING_TALKS = "de.prodyna.pac.conference.model.room.countReferencingTalks";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 30)
	private String name;

	@NotNull
	@Min(1)
	private int capacity;

	private static final long serialVersionUID = 1L;

	public Room() {
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

	public String capacity()
	{
		return this.name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getCapacity()
	{
		return this.capacity;
	}

	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Room other = (Room) obj;
		if (capacity != other.capacity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}

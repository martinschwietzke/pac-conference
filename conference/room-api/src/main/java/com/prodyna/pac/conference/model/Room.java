package com.prodyna.pac.conference.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity Room
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Entity
@XmlRootElement
@NamedQuery(name = Room.FIND_ALL, query = "SELECT r FROM Room r")
public class Room extends AbstractEntity {

	public final static String FIND_ALL = "de.prodyna.pac.conference.model.room.findAll";

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

}

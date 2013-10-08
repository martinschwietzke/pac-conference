package com.prodyna.pac.conference.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Speaker
 * 
 */
@Entity
public class Speaker implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String firstname;

	@NotNull
	@Size(min = 1, max = 30)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String lastname;

	private String description;

	private static final long serialVersionUID = 1L;

	public Speaker() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

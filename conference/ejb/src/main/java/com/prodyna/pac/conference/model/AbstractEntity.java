package com.prodyna.pac.conference.model;

public abstract class AbstractEntity implements Entity {

	private static final long serialVersionUID = 1L;

	public boolean isNew() {
		return getId() != null && getId() > 0;
	}
}

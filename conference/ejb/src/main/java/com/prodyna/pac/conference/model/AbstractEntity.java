package com.prodyna.pac.conference.model;

/**
 * @author Martin Schwietzke, PRODYNA AG
 *
 */
public abstract class AbstractEntity implements Entity {

	private static final long serialVersionUID = 1L;

	public boolean isNew() {
		return getId() != null && getId() > 0;
	}
}

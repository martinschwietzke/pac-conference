package com.prodyna.pac.conference.model;

import java.io.Serializable;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface Entity extends Serializable {

	boolean isNew();

	Long getId();
}

package com.prodyna.pac.conference.room.service;

import com.prodyna.pac.conference.exception.ConferenceServiceException;
import com.prodyna.pac.conference.room.model.Room;

/**
 * Exception which indicates, that an instance of {@link Room} is still
 * referenced by another entity.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public class RoomReferencedException extends ConferenceServiceException {

	private static final long serialVersionUID = 1L;

}

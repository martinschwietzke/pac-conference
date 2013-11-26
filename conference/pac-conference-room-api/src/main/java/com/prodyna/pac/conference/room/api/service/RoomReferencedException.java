package com.prodyna.pac.conference.room.api.service;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.room.api.model.Room;

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

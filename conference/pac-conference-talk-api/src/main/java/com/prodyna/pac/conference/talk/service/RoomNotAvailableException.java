package com.prodyna.pac.conference.talk.service;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;

/**
 * Indicates that a {@link com.prodyna.pac.conference.room.model.Room} is not
 * available in certain timespan.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public class RoomNotAvailableException extends ConferenceServiceException {

	private static final long serialVersionUID = 1L;

	public RoomNotAvailableException() {
		super();
	}

	public RoomNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoomNotAvailableException(String message) {
		super(message);
	}

	public RoomNotAvailableException(Throwable cause) {
		super(cause);
	}

}

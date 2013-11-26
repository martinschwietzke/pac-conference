package com.prodyna.pac.conference.talk.service;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;

/**
 * Indicates that certain time span is not in the time span of a certain
 * {@link com.prodyna.pac.conference.conference.model.Conference}
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public class OutOfConferenceDateRangeException extends
		ConferenceServiceException {

	private static final long serialVersionUID = 1L;

	public OutOfConferenceDateRangeException() {
		super();
	}

	public OutOfConferenceDateRangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public OutOfConferenceDateRangeException(String message) {
		super(message);
	}

	public OutOfConferenceDateRangeException(Throwable cause) {
		super(cause);
	}

}

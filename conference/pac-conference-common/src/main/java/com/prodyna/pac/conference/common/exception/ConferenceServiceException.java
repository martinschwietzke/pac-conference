package com.prodyna.pac.conference.common.exception;

/**
 * This is a generic checked exception. All possible exceptions in services
 * should inherit from it.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public class ConferenceServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConferenceServiceException() {
		super();
	}

	public ConferenceServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConferenceServiceException(String message) {
		super(message);
	}

	public ConferenceServiceException(Throwable cause) {
		super(cause);
	}

}

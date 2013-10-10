package com.prodyna.pac.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.service.ConferenceService;

@Model
public class ConferenceController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private ConferenceService conferenceService;

	private Conference newConference;

	@Produces
	@Named
	public Conference getNewConference() {
		return newConference;
	}

	public void cancelEditing() {
		newConference = null;
	}

	public void editConference(Conference conference) {
		newConference = conference;
	}

	public void createConference() throws Exception {
		try {
			conferenceService.createConference(newConference);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Conference saved"));
			initNewMember();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	@PostConstruct
	public void initNewMember() {
		newConference = new Conference();
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Saving Confernce failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}

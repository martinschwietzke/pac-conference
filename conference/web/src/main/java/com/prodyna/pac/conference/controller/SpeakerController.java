package com.prodyna.pac.conference.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.service.SpeakerService;

@Model
public class SpeakerController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private SpeakerService speakerService;

	private Speaker newSpeaker;

	@Produces
	@Named("newSpeaker")
	public Speaker getNewSpeaker() {
		return newSpeaker;
	}

	public void createSpeaker() throws Exception {
		try {
			speakerService.createSpeaker(newSpeaker);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Speaker saved"));
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
		newSpeaker = new Speaker();
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Saving Speaker failed. See server log for more information";
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

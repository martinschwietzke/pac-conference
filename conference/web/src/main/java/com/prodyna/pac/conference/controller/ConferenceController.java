package com.prodyna.pac.conference.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.service.ConferenceService;

@ManagedBean
@SessionScoped
public class ConferenceController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private ConferenceService conferenceService;

	private Conference conferenceToEdit = new Conference();

	public Conference getEditedConference()
	{
		return conferenceToEdit;
	}

	public void initNewConference()
	{
		conferenceToEdit = new Conference();
	}

	public void setConference(Conference conferenceToEdit)
	{
		this.conferenceToEdit = conferenceToEdit;
	}

	public void discardConference()
	{
		initNewConference();
	}

	public void cancelEditing()
	{
		initNewConference();
	}

	public void editConference(long conferenceId) throws Exception
	{
		Conference c = conferenceService.getConferenceById(conferenceId);
		conferenceToEdit = c;
	}

	public void createConference() throws Exception
	{
		try {
			conferenceService.createConference(conferenceToEdit);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Conference saved"));
			conferenceToEdit = null;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	public void updateConference() throws Exception
	{
		try {
			conferenceService.updateConference(conferenceToEdit);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Update!",
							"Conference updated"));

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating conferennce unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	private String getRootErrorMessage(Exception e)
	{
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

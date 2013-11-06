package com.prodyna.pac.conference.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.ConferenceService;
import com.prodyna.pac.conference.service.TalkService;
import com.prodyna.pac.conference.web.constants.ViewConstants;

@ManagedBean(name = "conferenceDetails")
@ViewScoped
public class ConferenceDetails extends AbstractEditEntityMaskBean implements
		Serializable {

	@Inject
	ConferenceService conferenceService;

	@Inject
	TalkService talkService;
	
	private Conference conference;

	private static final long serialVersionUID = 1L;

	public Conference getConference() throws Exception
	{
		if (conference == null) {
			conference = conferenceService.getConferenceById(super.getId());
		}

		return conference;
	}

	public void setConference(Conference conference)
	{
		this.conference = conference;
	}

	public ConferenceAgenda getConferenceAgenda() throws Exception
	{

		List<Talk> talks = talkService.getTalksByConference(getConference()
				.getId());

		return new ConferenceAgenda(conference, talks);
	}

	public String cancelEditing()
	{
		initNewConference();

		return ViewConstants.VIEW_CONFERENCE_LIST;
	}

	public void initNewConference()
	{
		conference = new Conference();
	}

	public void createConference() throws Exception
	{
		try {
			conferenceService.createConference(conference);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Conference saved"));
			conference = null;
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
			conferenceService.updateConference(conference);
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

}
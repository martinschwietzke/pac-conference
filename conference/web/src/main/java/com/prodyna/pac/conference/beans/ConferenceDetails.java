package com.prodyna.pac.conference.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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

	private PopupBean speakersPopup = new PopupBean();

	public PopupBean getSpeakersPopup()
	{
		return speakersPopup;
	}

	public void setSpeakersPopup(PopupBean speakersPopup)
	{
		this.speakersPopup = speakersPopup;
	}

	private static final long serialVersionUID = 1L;

	public Conference getConference() throws Exception
	{
		if (conference == null) {
			if (isNewMode()) {
				initNewConference();
			} else {

				conference = conferenceService.getConferenceById(super.getId());
			}
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

	public String createConference() throws Exception
	{
		String outcome = ViewConstants.VIEW_CONFERENCE_EDIT;
		try {
			conferenceService.createConference(conference);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Conference saved"));
			conference = null;

			outcome = ViewConstants.VIEW_CONFERENCE_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}

		return outcome;
	}

	public String updateConference() throws Exception
	{

		String outcome = ViewConstants.VIEW_CONFERENCE_EDIT;
		try {
			conferenceService.updateConference(conference);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Update!",
							"Conference updated"));

			outcome = ViewConstants.VIEW_CONFERENCE_LIST;

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating conferennce unsuccessful");
			facesContext.addMessage(null, m);
		}

		return outcome;

	}

	public void deleteConference(Conference conference) throws Exception
	{
		conferenceService.deleteConference(conference);
	}

	public void validateStartEndDate(ComponentSystemEvent event)
	{

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent component = event.getComponent();

		UIInput uiInputStart = (UIInput) component.findComponent("start");
		Date startDate = (Date) uiInputStart.getLocalValue();
		String startDateId = uiInputStart.getClientId();

		UIInput uiInputEnd = (UIInput) component.findComponent("end");
		Date endDate = (Date) uiInputEnd.getLocalValue();
		String endDateId = uiInputEnd.getClientId();

		if (startDate.after(endDate)) {

			FacesMessage msg = new FacesMessage(
					"Start date must be after end date");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(startDateId, msg);

			FacesMessage msg2 = new FacesMessage(
					"End date must be before start date");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(endDateId, msg2);
			fc.renderResponse();
		}

	}
}
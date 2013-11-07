package com.prodyna.pac.conference.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.SpeakerService;
import com.prodyna.pac.conference.service.TalkService;
import com.prodyna.pac.conference.web.constants.ViewConstants;

@ManagedBean(name = "speakerDetails")
@ViewScoped
public class SpeakerDetails extends AbstractEditEntityMaskBean {
	private static final long serialVersionUID = 1L;

	@Inject
	SpeakerService speakerService;

	@Inject
	TalkService talkService;

	private Speaker speaker;

	public Speaker getSpeaker() throws Exception
	{
		if (speaker == null) {
			if (isNewMode()) {
				initNewSpeaker();
			} else {
				speaker = speakerService.getSpeakerById(getId());
			}
		}
		return speaker;
	}

	public void setSpeaker(Speaker speaker)
	{
		this.speaker = speaker;
	}

	public List<Talk> getTalks() throws Exception
	{
		return talkService.getTalksBySpeaker(getSpeaker());
	}

	public void initNewSpeaker()
	{
		speaker = new Speaker();
	}

	@Override
	public String cancelEditing()
	{
		initNewSpeaker();

		return ViewConstants.VIEW_SPEAKER_LIST;
	}

	public String createSpeaker() throws Exception
	{
		String outcome = ViewConstants.VIEW_SPEAKER_EDIT;

		try {
			speakerService.createSpeaker(speaker);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Speaker saved"));

			outcome = ViewConstants.VIEW_SPEAKER_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}

		return outcome;
	}

	public String updateSpeaker() throws Exception
	{
		String outcome = ViewConstants.VIEW_SPEAKER_EDIT;
		try {
			speakerService.updateSpeaker(speaker);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Update!", "Speaker updated"));
			outcome = ViewConstants.VIEW_SPEAKER_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating speaker unsuccessful");
			facesContext.addMessage(null, m);
		}
		return outcome;
	}

}
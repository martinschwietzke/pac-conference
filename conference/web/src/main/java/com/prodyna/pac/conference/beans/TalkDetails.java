package com.prodyna.pac.conference.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.TalkService;
import com.prodyna.pac.conference.web.constants.ViewConstants;

@ManagedBean(name = "talkDetails")
@ViewScoped
public class TalkDetails extends AbstractEditEntityMaskBean {

	private static final long serialVersionUID = 1L;

	@Inject
	TalkService talkService;

	private Talk talk;

	public Talk getTalk() throws Exception
	{
		if (talk == null) {
			talk = talkService.getTalkById(getId());
		}
		return talk;
	}

	public void setTalk(Talk talk)
	{
		this.talk = talk;
	}

	public List<Speaker> getSpeakers() throws Exception
	{
		return talkService.getSpeakersByTalk(getId());
	}

	public void createTalk() throws Exception
	{
		try {
			talkService.createTalk(talk);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Talk saved"));
			talk = null;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	public void updateTalk() throws Exception
	{
		try {
			talkService.updateTalk(talk);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Update!", "Talk updated"));

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating talk unsuccessful");
			facesContext.addMessage(null, m);
		}

	}

	public void initNewTalk()
	{
		talk = new Talk();
	}

	@Override
	public String cancelEditing()
	{
		initNewTalk();

		return ViewConstants.VIEW_TALK_LIST;
	}

}
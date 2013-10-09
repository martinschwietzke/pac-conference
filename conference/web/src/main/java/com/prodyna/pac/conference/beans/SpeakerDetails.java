package com.prodyna.pac.conference.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.SpeakerService;
import com.prodyna.pac.conference.service.TalkService;

@ManagedBean(name = "speakerDetails")
@ViewScoped
public class SpeakerDetails {

	@Inject
	SpeakerService speakerService;

	@Inject
	TalkService talkService;

	private Speaker speaker;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) throws Exception {
		this.id = id;
		speaker = speakerService.getSpeakerById(id);
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public List<Talk> getTalks() throws Exception {
		return talkService.getTalksBySpeaker(speaker);
	}

}
package com.prodyna.pac.conference.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.ConferenceService;
import com.prodyna.pac.conference.service.TalkService;

@ManagedBean(name = "conferenceDetails")
@ViewScoped
public class ConferenceDetails implements Serializable {

	@Inject
	ConferenceService conferenceService;

	@Inject
	TalkService talkService;

	private Conference conference;

	private Long id;

	private static final long serialVersionUID = 1L;

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) throws Exception {
		this.id = id;
		conference = conferenceService.getConferenceById(id);
	}

	public ConferenceAgenda getConferenceAgenda() throws Exception {

		List<Talk> talks = talkService.getTalksByConference(conference.getId());

		return new ConferenceAgenda(conference, talks);
	}

}
package com.prodyna.pac.conference.web.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.talk.api.model.Talk;
import com.prodyna.pac.conference.talk.api.service.TalkService;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RequestScoped
public class TalkListProducer {

	@Inject
	private TalkService talkService;

	private List<Talk> talks;

	@Produces
	@Named("talks")
	public List<Talk> getConferences()
	{
		return talks;
	}

	public void onTalkListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Talk talk)
			throws Exception
	{
		retrieveAllTalksOrderedByName();
	}

	@PostConstruct
	public void retrieveAllTalksOrderedByName() throws Exception
	{
		talks = talkService.getAllTalks();
	}
}

package com.prodyna.pac.conference.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RequestScoped
public class ConferenceListProducer {

	@Inject
	private ConferenceService conferenceService;

	private List<Conference> conferences;

	@Produces
	@Named("conferences")
	public List<Conference> getConferences()
	{
		return conferences;
	}

	public void onMemberListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Conference conference)
			throws Exception
	{
		retrieveAllMembersOrderedByName();
	}

	@PostConstruct
	public void retrieveAllMembersOrderedByName() throws Exception
	{
		conferences = conferenceService.getAllConferences();
	}
}

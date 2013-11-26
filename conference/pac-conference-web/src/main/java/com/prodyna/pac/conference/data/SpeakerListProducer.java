package com.prodyna.pac.conference.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.speaker.api.model.Speaker;
import com.prodyna.pac.conference.speaker.api.service.SpeakerService;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RequestScoped
public class SpeakerListProducer {

	@Inject
	private SpeakerService speakerService;

	private List<Speaker> speakers;

	@Produces
	@Named("speakers")
	public List<Speaker> getSpeakers()
	{
		return speakers;
	}

	public void onMemberListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Speaker room)
			throws Exception
	{
		retrieveAllSpeakersOrderedByName();
	}

	@PostConstruct
	public void retrieveAllSpeakersOrderedByName() throws Exception
	{
		speakers = speakerService.getAllSpeakers();
	}
}

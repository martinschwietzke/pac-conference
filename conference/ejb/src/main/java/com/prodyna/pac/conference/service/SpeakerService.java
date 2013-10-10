package com.prodyna.pac.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Speaker;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class SpeakerService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Speaker> roomEventSrc;

	public void createSpeaker(Speaker speaker) throws Exception {

		log.info("Creating Speaker [" + speaker.getName() + "]");
		em.persist(speaker);
		roomEventSrc.fire(speaker);
	}

	public void deleteSpeaker(Speaker speaker) throws Exception {

		log.info("Deleting Speaker [" + speaker.getName() + "]");
		em.remove(speaker);
		roomEventSrc.fire(speaker);
	}

	public void updateSpeaker(Speaker speaker) throws Exception {

		log.info("Updating Speaker [" + speaker.getName() + "]");
		em.merge(speaker);
		roomEventSrc.fire(speaker);

	}

	public Speaker getSpeakerById(long speakerId) throws Exception {

		return em.find(Speaker.class, speakerId);
	}

	public List<Speaker> getAllSpeakers() throws Exception {

		return em.createNamedQuery(Speaker.FIND_ALL, Speaker.class)
				.getResultList();

	}
}

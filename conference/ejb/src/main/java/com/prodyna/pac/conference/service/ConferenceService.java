package com.prodyna.pac.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Conference;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ConferenceService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Conference> conferenceEventSrc;

	public void createConference(Conference conference) throws Exception {

		log.info("Creating conference [" + conference.getName() + "]");
		em.persist(conference);
		conferenceEventSrc.fire(conference);
	}

	public void deleteConference(Conference conference) throws Exception {

		log.info("Deleting conference [" + conference.getName() + "]");
		em.remove(conference);
		conferenceEventSrc.fire(conference);
	}

	public void updateConference(Conference conference) throws Exception {

		log.info("Updating conference [" + conference.getName() + "]");
		em.merge(conference);
		conferenceEventSrc.fire(conference);

	}

	public Conference getConferenceById(long conferenceId) throws Exception {

		return em.find(Conference.class, conferenceId);
	}

	public List<Conference> getAllConferences() throws Exception {

		return em.createNamedQuery(Conference.FIND_ALL, Conference.class)
				.getResultList();

	}
}

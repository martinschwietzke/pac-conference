package com.prodyna.pac.conference.service.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.service.ConferenceService;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@Local(ConferenceService.class)
public class ConferenceServiceBean implements ConferenceService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Conference> conferenceEventSrc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.ConferenceService#createConference
	 * (com.prodyna.pac.conference.model.Conference)
	 */
	@Override
	public void createConference(Conference conference) throws Exception {

		log.info("Creating conference [" + conference.getName() + "]");
		em.persist(conference);
		conferenceEventSrc.fire(conference);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.ConferenceService#deleteConference
	 * (com.prodyna.pac.conference.model.Conference)
	 */
	@Override
	public void deleteConference(Conference conference) throws Exception {

		log.info("Deleting conference [" + conference.getName() + "]");
		em.remove(conference);
		conferenceEventSrc.fire(conference);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.ConferenceService#updateConference
	 * (com.prodyna.pac.conference.model.Conference)
	 */
	@Override
	public void updateConference(Conference conference) throws Exception {

		log.info("Updating conference [" + conference.getName() + "]");
		em.merge(conference);
		conferenceEventSrc.fire(conference);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.ConferenceService#getConferenceById
	 * (long)
	 */
	@Override
	public Conference getConferenceById(long conferenceId) throws Exception {

		return em.find(Conference.class, conferenceId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.ConferenceService#getAllConferences()
	 */
	@Override
	public List<Conference> getAllConferences() throws Exception {

		return em.createNamedQuery(Conference.FIND_ALL, Conference.class)
				.getResultList();

	}
}

package com.prodyna.pac.conference.conference.impl.service.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.common.interceptor.Logged;
import com.prodyna.pac.conference.common.interceptor.Performance;
import com.prodyna.pac.conference.conference.api.model.Conference;
import com.prodyna.pac.conference.conference.api.service.ConferenceService;

/**
 * EJB stateless session bean implementation for {@link ConferenceService}.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Stateless
@Local(ConferenceService.class)
@Logged
@Performance
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
	 * @see com.prodyna.pac.conference.conference.api.service.ConferenceService#
	 * createConference(com.prodyna.pac.conference.conference.api.model.Conference)
	 */
	@Override
	public Conference createConference(Conference conference)
			throws ConferenceServiceException
	{
		if (conference == null) {
			throw new IllegalArgumentException("Conference must not be null");
		}

		log.info("Creating conference [" + conference.getName() + "]");
		em.persist(conference);
		conferenceEventSrc.fire(conference);
		return conference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.conference.api.service.ConferenceService#
	 * deleteConference (com.prodyna.pac.conference.conference.api.model.Conference)
	 */
	@Override
	public void deleteConference(Conference conference)
			throws ConferenceServiceException
	{

		log.info("Deleting conference [" + conference.getName() + "]");
		Conference merge = em.merge(conference);
		em.remove(merge);
		conferenceEventSrc.fire(conference);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.conference.api.service.ConferenceService#
	 * deleteConference (long)
	 */
	@Override
	public void deleteConference(long conferenceId)
			throws ConferenceServiceException
	{
		Conference c = getConferenceById(conferenceId);
		if (c != null) {
			em.remove(c);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.conference.api.service.ConferenceService#
	 * updateConference (com.prodyna.pac.conference.conference.api.model.Conference)
	 */
	@Override
	public void updateConference(Conference conference)
			throws ConferenceServiceException
	{

		if (conference == null) {
			throw new IllegalArgumentException("Conference must not be null");
		}

		log.info("Updating conference [" + conference.getName() + "]");
		em.merge(conference);
		conferenceEventSrc.fire(conference);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.conference.api.service.ConferenceService#
	 * getConferenceById (long)
	 */
	@Override
	public Conference getConferenceById(long conferenceId)
			throws ConferenceServiceException
	{

		return em.find(Conference.class, conferenceId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.conference.api.service.ConferenceService#
	 * getAllConferences()
	 */
	@Override
	public List<Conference> getAllConferences()
			throws ConferenceServiceException
	{

		return em.createNamedQuery(Conference.FIND_ALL, Conference.class)
				.getResultList();

	}
}

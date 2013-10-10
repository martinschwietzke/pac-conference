package com.prodyna.pac.conference.service.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.service.SpeakerService;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@Local(SpeakerService.class)
public class SpeakerServiceBean implements SpeakerService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Speaker> roomEventSrc;

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.service.SpeakerService#createSpeaker(com.prodyna.pac.conference.model.Speaker)
	 */
	@Override
	public void createSpeaker(Speaker speaker) throws Exception {

		log.info("Creating Speaker [" + speaker.getName() + "]");
		em.persist(speaker);
		roomEventSrc.fire(speaker);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.service.SpeakerService#deleteSpeaker(com.prodyna.pac.conference.model.Speaker)
	 */
	@Override
	public void deleteSpeaker(Speaker speaker) throws Exception {

		log.info("Deleting Speaker [" + speaker.getName() + "]");
		em.remove(speaker);
		roomEventSrc.fire(speaker);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.service.SpeakerService#updateSpeaker(com.prodyna.pac.conference.model.Speaker)
	 */
	@Override
	public void updateSpeaker(Speaker speaker) throws Exception {

		log.info("Updating Speaker [" + speaker.getName() + "]");
		em.merge(speaker);
		roomEventSrc.fire(speaker);

	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.service.SpeakerService#getSpeakerById(long)
	 */
	@Override
	public Speaker getSpeakerById(long speakerId) throws Exception {

		return em.find(Speaker.class, speakerId);
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.conference.service.SpeakerService#getAllSpeakers()
	 */
	@Override
	public List<Speaker> getAllSpeakers() throws Exception {

		return em.createNamedQuery(Speaker.FIND_ALL, Speaker.class)
				.getResultList();

	}
}

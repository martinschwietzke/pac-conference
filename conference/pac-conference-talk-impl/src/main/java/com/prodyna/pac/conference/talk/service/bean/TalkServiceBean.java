package com.prodyna.pac.conference.talk.service.bean;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.prodyna.pac.conference.interceptor.Logged;
import com.prodyna.pac.conference.interceptor.Performance;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.model.TalkSpeaker;
import com.prodyna.pac.conference.talk.service.OutOfConferenceDateRangeException;
import com.prodyna.pac.conference.talk.service.RoomNotAvailableException;
import com.prodyna.pac.conference.talk.service.TalkService;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Stateless
@Local(TalkService.class)
@Logged
@Performance
public class TalkServiceBean implements TalkService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Talk> talkEventSrc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#createTalk(com.prodyna
	 * .pac.conference.model.Talk)
	 */
	@Override
	public Talk createTalk(Talk talk) throws RoomNotAvailableException,
			OutOfConferenceDateRangeException, Exception
	{
		long calcEnd = talk.getStart().getTime()
				+ TimeUnit.MINUTES.toMillis(talk.getDuration());
		talk.setEnd(new Date(calcEnd));

		assertRoomIsAvailable(talk);
		assertTalkIsInConferenceDateRange(talk);

		log.info("Creating Talk [" + talk.getName() + "]");
		em.persist(talk);
		talkEventSrc.fire(talk);

		return talk;
	}

	private void assertRoomIsAvailable(Talk talk) throws Exception,
			RoomNotAvailableException
	{

		boolean roomAvailable = isRoomAvailable(talk.getRoom().getId(),
				talk.getStart(), talk.getEnd());

		if (!roomAvailable) {
			throw new RoomNotAvailableException();
		}
	}

	private void assertTalkIsInConferenceDateRange(Talk talk) throws Exception,
			OutOfConferenceDateRangeException
	{
		long confStart = talk.getConference().getStart().getTime();
		long confEnd = talk.getConference().getEnd().getTime();
		long talkStart = talk.getStart().getTime();
		long talkEnd = talk.getEnd().getTime();

		boolean isValidRange = confStart <= talkStart && confEnd >= talkEnd;

		if (!isValidRange) {
			throw new OutOfConferenceDateRangeException();
		}
	}

	public boolean isRoomAvailable(long roomId, Date start, Date end)
			throws Exception
	{
		List<Talk> talks = this.getTalksByRoom(roomId);
		boolean available = true;
		for (Talk talk : talks) {
			if (talk.getStart().before(end) && talk.getEnd().after(start)) {
				available = false;
				break;
			}
		}
		return available;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#deleteTalk(com.prodyna
	 * .pac.conference.model.Talk)
	 */
	@Override
	public void deleteTalk(Talk talk) throws Exception
	{

		log.info("Deleting Talk [" + talk.getName() + "]");
		Talk merge = em.merge(talk);

		deleteAssignedTalksSpeakers(merge.getId());
		em.remove(merge);
		talkEventSrc.fire(talk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.talk.service.TalkService#deleteTalk(long)
	 */
	@Override
	public void deleteTalk(long talkId) throws Exception
	{
		Talk talk = getTalkById(talkId);
		if (talk != null) {
			log.info("Deleting Talk [" + talk.getName() + "]");
			deleteAssignedTalksSpeakers(talk.getId());
			em.remove(talk);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#updateTalk(com.prodyna
	 * .pac.conference.model.Talk)
	 */
	@Override
	public Talk updateTalk(Talk talk) throws RoomNotAvailableException,
			OutOfConferenceDateRangeException, Exception
	{

		assertRoomIsAvailable(talk);
		assertTalkIsInConferenceDateRange(talk);

		log.info("Updating Talk [" + talk.getName() + "]");
		Talk merge = em.merge(talk);
		talkEventSrc.fire(talk);
		return merge;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.talk.service.TalkService#getTalkById(long)
	 */
	@Override
	public Talk getTalkById(long talkId) throws Exception
	{

		return em.find(Talk.class, talkId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.talk.service.TalkService#getAllTalks()
	 */
	@Override
	public List<Talk> getAllTalks() throws Exception
	{

		return em.createNamedQuery(Talk.FIND_ALL, Talk.class).getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#getTalksByConference(long)
	 */
	@Override
	public List<Talk> getTalksByConference(long conferenceId) throws Exception
	{

		TypedQuery<Talk> q = em.createNamedQuery(Talk.FIND_BY_CONFERENCE_ID,
				Talk.class);
		q.setParameter("conferenceId", conferenceId);

		return q.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.talk.service.TalkService#getTalksByRoom(long)
	 */
	@Override
	public List<Talk> getTalksByRoom(long roomId) throws Exception
	{

		TypedQuery<Talk> q = em.createNamedQuery(Talk.FIND_BY_ROOM_ID,
				Talk.class);
		q.setParameter("roomId", roomId);

		return q.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#getTalksBySpeaker(com.
	 * prodyna.pac.conference.model.Speaker)
	 */
	@Override
	public List<Talk> getTalksBySpeaker(Speaker speaker) throws Exception
	{

		TypedQuery<Talk> q = em.createNamedQuery(
				TalkSpeaker.FIND_TALK_BY_SPEAKER_ID, Talk.class);
		q.setParameter("speakerId", speaker.getId());

		return q.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#getSpeakersByTalk(long)
	 */
	@Override
	public List<Speaker> getSpeakersByTalk(long talk) throws Exception
	{

		TypedQuery<Speaker> q = em.createNamedQuery(
				Talk.FIND_SPEAKERS_BY_TALK_ID, Speaker.class);
		q.setParameter("talkId", talk);

		return q.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.service.TalkService#updateTalkSpeakers(long,
	 * java.util.List)
	 */
	@Override
	public void updateTalkSpeakers(long talkId, List<Speaker> speakers)
			throws Exception
	{

		Talk talk = getTalkById(talkId);

		updateTalkSpeakers(talk, speakers);
	}

	@Override
	public void updateTalkSpeakers(Talk talk, List<Speaker> speakers)
			throws Exception
	{

		deleteAssignedTalksSpeakers(talk.getId());

		for (Speaker speaker : speakers) {
			TalkSpeaker ts = new TalkSpeaker();
			ts.setSpeaker(speaker);
			ts.setTalk(talk);
			em.persist(ts);
		}
	}

	private void deleteAssignedTalksSpeakers(long talkId)
	{
		Query q = em.createNamedQuery(Talk.DELETE_TALK_SPEAKERS_BY_TALK_ID);
		q.setParameter("talkId", talkId);
		q.executeUpdate();
	}
}

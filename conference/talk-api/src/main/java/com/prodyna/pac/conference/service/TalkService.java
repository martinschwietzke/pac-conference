package com.prodyna.pac.conference.service;

import java.util.List;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface TalkService {

	Talk createTalk(Talk talk) throws RoomNotAvailableException,
			OutOfConferenceDateRangeException, Exception;

	void deleteTalk(Talk talk) throws Exception;

	Talk updateTalk(Talk talk) throws RoomNotAvailableException,
			OutOfConferenceDateRangeException, Exception;

	Talk getTalkById(long talkId) throws Exception;

	List<Talk> getAllTalks() throws Exception;

	List<Talk> getTalksByConference(long conferenceId) throws Exception;

	List<Talk> getTalksByRoom(long roomId) throws Exception;

	List<Talk> getTalksBySpeaker(Speaker speaker) throws Exception;

	List<Speaker> getSpeakersByTalk(long talk) throws Exception;

	void updateTalkSpeakers(long talkId, List<Speaker> speakers)
			throws Exception;

	void updateTalkSpeakers(Talk talk, List<Speaker> speakers) throws Exception;

	void deleteTalk(long talkId) throws Exception;

}
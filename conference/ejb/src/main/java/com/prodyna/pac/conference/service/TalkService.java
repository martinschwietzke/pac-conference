package com.prodyna.pac.conference.service;

import java.util.List;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface TalkService {

	void createTalk(Talk talk) throws Exception;

	void deleteTalk(Talk talk) throws Exception;

	void updateTalk(Talk talk) throws Exception;

	Talk getTalkById(long talkId) throws Exception;

	List<Talk> getAllTalks() throws Exception;

	List<Talk> getTalksByConference(long conferenceId) throws Exception;

	List<Talk> getTalksByRoom(long roomId) throws Exception;

	List<Talk> getTalksBySpeaker(Speaker speaker) throws Exception;

}
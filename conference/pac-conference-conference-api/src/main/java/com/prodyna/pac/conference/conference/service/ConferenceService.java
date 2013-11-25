package com.prodyna.pac.conference.conference.service;

import java.util.List;

import com.prodyna.pac.conference.conference.model.Conference;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface ConferenceService {

	Conference createConference(Conference conference) throws Exception;

	void deleteConference(Conference conference) throws Exception;

	void updateConference(Conference conference) throws Exception;

	Conference getConferenceById(long conferenceId) throws Exception;

	List<Conference> getAllConferences() throws Exception;

	void deleteConference(long conferenceId) throws Exception;

}
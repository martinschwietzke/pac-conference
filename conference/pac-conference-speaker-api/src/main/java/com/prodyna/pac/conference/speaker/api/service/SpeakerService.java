package com.prodyna.pac.conference.speaker.api.service;

import java.util.List;

import com.prodyna.pac.conference.speaker.api.model.Speaker;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface SpeakerService {

	void createSpeaker(Speaker speaker) throws Exception;

	void deleteSpeaker(Speaker speaker) throws SpeakerReferencedException,
			Exception;

	void updateSpeaker(Speaker speaker) throws Exception;

	Speaker getSpeakerById(long speakerId) throws Exception;

	List<Speaker> getAllSpeakers() throws Exception;

}
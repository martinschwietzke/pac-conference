package com.prodyna.pac.conference.conference.service;

import java.util.List;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.conference.model.Conference;

/**
 * Service interface for managing {@link Conference} entities.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface ConferenceService {

	/**
	 * Creates and stores a new {@link Conference}.
	 * 
	 * @param conference
	 *            The conference to create.
	 * @return The created conference instance.
	 * @throws ConferenceServiceException
	 *             Thrown on error while creating new {@link Conference}.
	 */
	Conference createConference(Conference conference)
			throws ConferenceServiceException;

	/**
	 * Deletes the given conference.
	 * 
	 * @param conference
	 *            The {@link Conference} to delete.
	 * @throws ConferenceServiceException
	 *             Thrown on error while deleting the conference.
	 */
	void deleteConference(Conference conference)
			throws ConferenceServiceException;

	/**
	 * Updates the given conference.
	 * 
	 * @param conference
	 *            The conference to persist.
	 * @throws ConferenceServiceException
	 *             Thrown on error while updating the conference.
	 */
	void updateConference(Conference conference)
			throws ConferenceServiceException;

	/**
	 * Fetches a Conference by it's identifier.
	 * 
	 * @param conferenceId
	 *            The id of the {@link Conference} to fetch.
	 * @return An instance of {@link Conference} or <code>null</code> if no
	 *         conference exists with the given conferenceId.
	 * @throws ConferenceServiceException
	 *             Thrown on error when loading the conference.
	 */
	Conference getConferenceById(long conferenceId)
			throws ConferenceServiceException;

	/**
	 * Loads all conferences.
	 * 
	 * @return A {@link List} of all conferences.
	 * @throws ConferenceServiceException
	 *             Thrown on error while fetching conferences.
	 */
	List<Conference> getAllConferences() throws ConferenceServiceException;

	/**
	 * Deletes the conference with the given conferenceId.
	 * 
	 * @param conferenceId
	 *            The id of the conference to delete.
	 * @throws ConferenceServiceException
	 *             Thrown on error while deleting the conference.
	 */
	void deleteConference(long conferenceId) throws ConferenceServiceException;

}
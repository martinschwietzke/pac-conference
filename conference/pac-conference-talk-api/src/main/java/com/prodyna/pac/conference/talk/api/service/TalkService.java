package com.prodyna.pac.conference.talk.api.service;

import java.util.Date;
import java.util.List;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.speaker.api.model.Speaker;
import com.prodyna.pac.conference.talk.api.model.Talk;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface TalkService {

	/**
	 * Creates and stores a new {@link Talk}.
	 * 
	 * @param talk
	 *            The talk to create.
	 * @return The created talk instance.
	 * @throws ConferenceServiceException
	 *             Thrown on error while creating new {@link Talk}.
	 * @throws RoomNotAvailableException
	 *             Thrown of the references room in talk is not available in the
	 *             talks' timespan.
	 * @throws OutOfConferenceDateRangeException
	 *             Thrown if the timespan of the talk is not in timespan of the
	 *             belonging conference.
	 */
	Talk createTalk(Talk talk) throws RoomNotAvailableException,
			OutOfConferenceDateRangeException, ConferenceServiceException;

	/**
	 * Deletes the given talk.
	 * 
	 * @param talk
	 *            The {@link Talk} to delete.
	 * @throws ConferenceServiceException
	 *             Thrown on error while deleting the talk.
	 */
	void deleteTalk(Talk talk) throws ConferenceServiceException;

	/**
	 * Updates the given talk.
	 * 
	 * @param talk
	 *            The talk to persist.
	 * @throws ConferenceServiceException
	 *             Thrown on error while updating the talk.
	 */
	Talk updateTalk(Talk talk) throws RoomNotAvailableException,
			OutOfConferenceDateRangeException, ConferenceServiceException;

	/**
	 * Fetches a {@link Talk} by it's identifier.
	 * 
	 * @param talkId
	 *            The id of the {@link Talk} to fetch.
	 * @return An instance of {@link Talk} or <code>null</code> if no talk
	 *         exists with the given talkId.
	 * @throws ConferenceServiceException
	 *             Thrown on error when loading the talk.
	 */
	Talk getTalkById(long talkId) throws ConferenceServiceException;

	/**
	 * Loads all talks.
	 * 
	 * @return A {@link List} of all talks.
	 * @throws ConferenceServiceException
	 *             Thrown on error while fetching talks.
	 */
	List<Talk> getAllTalks() throws ConferenceServiceException;

	/**
	 * Returns a list of talks which belongs to the given conference.
	 * 
	 * @param conferenceId
	 *            The conference id which is used for filtering returned talks.
	 * @return Returns a list of talks which belongs to the given conference.
	 * @throws ConferenceServiceException
	 *             Thrown in error while loading talks.
	 */
	List<Talk> getTalksByConference(long conferenceId)
			throws ConferenceServiceException;

	/**
	 * Returns a list of talks which are assigned to the given room.
	 * 
	 * @param conferenceId
	 *            The room id which is used for filtering returned talks.
	 * @return Returns a list of talks which belongs to the given room.
	 * @throws ConferenceServiceException
	 *             Thrown in error while loading talks.
	 */
	List<Talk> getTalksByRoom(long roomId) throws ConferenceServiceException;

	/**
	 * Returns a list of talks where the given speaker is one of the speakers.
	 * 
	 * @param conferenceId
	 *            The speaker id which is used for filtering returned talks.
	 * @return Returns a list of talks where the given speaker is one of the
	 *         speakers.
	 * @throws ConferenceServiceException
	 *             Thrown in error while loading talks.
	 */
	List<Talk> getTalksBySpeaker(Speaker speaker)
			throws ConferenceServiceException;

	/**
	 * Returns a list of talks where the given speaker is one of the speakers.
	 * 
	 * @param conferenceId
	 *            The speaker id which is used for filtering returned talks.
	 * @return Returns a list of talks where the given speaker is one of the
	 *         speakers.
	 * @throws ConferenceServiceException
	 *             Thrown in error while loading talks.
	 */
	List<Speaker> getSpeakersByTalk(long talk)
			throws ConferenceServiceException;

	void updateTalkSpeakers(long talkId, List<Speaker> speakers)
			throws ConferenceServiceException;

	void updateTalkSpeakers(Talk talk, List<Speaker> speakers)
			throws ConferenceServiceException;

	/**
	 * Deletes the talk with the given talkId.
	 * 
	 * @param talkId
	 *            The id of the talk to delete.
	 * @throws ConferenceServiceException
	 *             Thrown on error while deleting the talk.
	 */
	void deleteTalk(long talkId) throws ConferenceServiceException;

	boolean isRoomAvailable(long roomId, Date start, Date end, Long talkId)
			throws ConferenceServiceException;

}
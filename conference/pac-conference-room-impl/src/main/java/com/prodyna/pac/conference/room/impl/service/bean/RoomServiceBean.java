package com.prodyna.pac.conference.room.impl.service.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.common.interceptor.Logged;
import com.prodyna.pac.conference.common.interceptor.Performance;
import com.prodyna.pac.conference.room.api.model.Room;
import com.prodyna.pac.conference.room.api.service.RoomReferencedException;
import com.prodyna.pac.conference.room.api.service.RoomService;

/**
 * EJB stateless session bean implementation for {@link RoomService}.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Stateless
@Local(RoomService.class)
@Logged
@Performance
public class RoomServiceBean implements RoomService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Room> roomEventSrc;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.api.service.RoomService#createRoom(com
	 * .prodyna .pac.conference.model.Room)
	 */
	@Override
	public Room createRoom(Room room) throws ConferenceServiceException
	{
		log.info("Creating Room [" + room.getName() + "]");
		em.persist(room);
		roomEventSrc.fire(room);
		return room;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.room.api.service.RoomService#deleteRoom(java
	 * .lang.Long)
	 */
	@Override
	public void deleteRoom(Long roomId) throws RoomReferencedException,
			ConferenceServiceException
	{
		Room r = getRoomById(roomId);

		deleteRoom(r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.api.service.RoomService#deleteRoom(com
	 * .prodyna .pac.conference.model.Room)
	 */
	@Override
	public void deleteRoom(Room room) throws RoomReferencedException,
			ConferenceServiceException
	{

		if (room == null || room.getId() == null) {
			throw new IllegalArgumentException(
					"Room must not be null and have an id set.");
		}

		Query q = em.createNamedQuery(Room.COUNT_REFERENCING_TALKS);
		q.setParameter("roomId", room.getId());
		Long count = (Long) q.getSingleResult();

		if (count > 0) {
			throw new RoomReferencedException();
		}

		log.info("Deleting Room [" + room.getName() + "]");
		Room merge = em.merge(room);
		em.remove(merge);

		roomEventSrc.fire(room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.api.service.RoomService#updateRoom(com
	 * .prodyna .pac.conference.model.Room)
	 */
	@Override
	public Room updateRoom(Room room) throws ConferenceServiceException
	{

		log.info("Updating Room [" + room.getName() + "]");
		Room merge = em.merge(room);
		roomEventSrc.fire(room);
		return merge;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.api.service.RoomService#getSpeakerById
	 * (long)
	 */
	@Override
	public Room getRoomById(long roomId) throws ConferenceServiceException
	{

		return em.find(Room.class, roomId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.talk.api.service.RoomService#getAllRooms()
	 */
	@Override
	public List<Room> getAllRooms() throws ConferenceServiceException
	{

		return em.createNamedQuery(Room.FIND_ALL, Room.class).getResultList();
	}

}

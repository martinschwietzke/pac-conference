package com.prodyna.pac.conference.service.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.interceptor.Logged;
import com.prodyna.pac.conference.interceptor.Performance;
import com.prodyna.pac.conference.model.Room;
import com.prodyna.pac.conference.service.RoomService;

/**
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
	 * com.prodyna.pac.conference.service.RoomService#createRoom(com.prodyna
	 * .pac.conference.model.Room)
	 */
	@Override
	public void createRoom(Room room) throws Exception
	{

		log.info("Creating Room [" + room.getName() + "]");
		em.persist(room);
		roomEventSrc.fire(room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.RoomService#deleteRoom(com.prodyna
	 * .pac.conference.model.Room)
	 */
	@Override
	public void deleteRoom(Room room) throws Exception
	{

		log.info("Deleting Room [" + room.getName() + "]");
		em.remove(room);
		roomEventSrc.fire(room);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.service.RoomService#updateRoom(com.prodyna
	 * .pac.conference.model.Room)
	 */
	@Override
	public void updateRoom(Room room) throws Exception
	{

		log.info("Updating Room [" + room.getName() + "]");
		em.merge(room);
		roomEventSrc.fire(room);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.service.RoomService#getSpeakerById(long)
	 */
	@Override
	public Room getSpeakerById(long roomId) throws Exception
	{

		return em.find(Room.class, roomId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.service.RoomService#getAllRooms()
	 */
	@Override
	public List<Room> getAllRooms() throws Exception
	{

		return em.createNamedQuery(Room.FIND_ALL, Room.class).getResultList();

	}
}

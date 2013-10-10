package com.prodyna.pac.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Room;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class RoomService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Room> roomEventSrc;

	public void createRoom(Room room) throws Exception {

		log.info("Creating Room [" + room.getName() + "]");
		em.persist(room);
		roomEventSrc.fire(room);
	}

	public void deleteRoom(Room room) throws Exception {

		log.info("Deleting Room [" + room.getName() + "]");
		em.remove(room);
		roomEventSrc.fire(room);
	}

	public void updateRoom(Room room) throws Exception {

		log.info("Updating Room [" + room.getName() + "]");
		em.merge(room);
		roomEventSrc.fire(room);

	}

	public Room getSpeakerById(long roomId) throws Exception {

		return em.find(Room.class, roomId);
	}

	public List<Room> getAllRooms() throws Exception {

		return em.createNamedQuery(Room.FIND_ALL, Room.class).getResultList();

	}
}

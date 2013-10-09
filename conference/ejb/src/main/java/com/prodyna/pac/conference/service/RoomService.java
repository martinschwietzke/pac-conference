/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prodyna.pac.conference.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Room;
import com.prodyna.pac.conference.model.Speaker;

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

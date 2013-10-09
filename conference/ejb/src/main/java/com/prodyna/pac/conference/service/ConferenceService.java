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

import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.model.Room;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ConferenceService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Conference> conferenceEventSrc;

	public void createConference(Conference conference) throws Exception {

		log.info("Creating conference [" + conference.getName() + "]");
		em.persist(conference);
		conferenceEventSrc.fire(conference);
	}

	public void deleteConference(Conference conference) throws Exception {

		log.info("Deleting conference [" + conference.getName() + "]");
		em.remove(conference);
		conferenceEventSrc.fire(conference);
	}

	public void updateConference(Conference conference) throws Exception {

		log.info("Updating conference [" + conference.getName() + "]");
		em.merge(conference);
		conferenceEventSrc.fire(conference);

	}

	public Conference getConferenceById(long conferenceId) throws Exception {

		return em.find(Conference.class, conferenceId);
	}

	public List<Conference> getAllConferences() throws Exception {

		return em.createNamedQuery(Conference.FIND_ALL, Conference.class)
				.getResultList();

	}
}

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
import javax.persistence.TypedQuery;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class TalkService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Talk> roomEventSrc;

	public void createTalk(Talk talk) throws Exception {

		log.info("Creating Talk [" + talk.getName() + "]");
		em.persist(talk);
		roomEventSrc.fire(talk);
	}

	public void deleteTalk(Talk talk) throws Exception {

		log.info("Deleting Talk [" + talk.getName() + "]");
		em.remove(talk);
		roomEventSrc.fire(talk);
	}

	public void updateTalk(Talk talk) throws Exception {

		log.info("Updating Talk [" + talk.getName() + "]");
		em.merge(talk);
		roomEventSrc.fire(talk);

	}

	public Talk getTalkById(long talkId) throws Exception {

		return em.find(Talk.class, talkId);
	}

	public List<Talk> getAllTalks() throws Exception {

		return em.createNamedQuery(Talk.FIND_ALL, Talk.class).getResultList();

	}

	public List<Talk> getTalksByConference(long conferenceId) throws Exception {

		TypedQuery<Talk> q = em.createNamedQuery(Talk.FIND_BY_CONFERENCE_ID,
				Talk.class);
		q.setParameter("conferenceId", conferenceId);

		return q.getResultList();

	}

	public List<Talk> getTalksByRoom(long roomId) throws Exception {

		TypedQuery<Talk> q = em.createNamedQuery(Talk.FIND_BY_ROOM_ID,
				Talk.class);
		q.setParameter("roomId", roomId);

		return q.getResultList();

	}

	public List<Talk> getTalksBySpeaker(Speaker speaker) throws Exception {

		TypedQuery<Talk> q = em.createNamedQuery(Talk.FIND_BY_SPEAKER,
				Talk.class);
		q.setParameter("speaker", speaker);

		return q.getResultList();

	}
}

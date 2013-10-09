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

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class SpeakerService {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Speaker> roomEventSrc;

	public void createSpeaker(Speaker speaker) throws Exception {

		log.info("Creating Speaker [" + speaker.getName() + "]");
		em.persist(speaker);
		roomEventSrc.fire(speaker);
	}

	public void deleteSpeaker(Speaker speaker) throws Exception {

		log.info("Deleting Speaker [" + speaker.getName() + "]");
		em.remove(speaker);
		roomEventSrc.fire(speaker);
	}

	public void updateSpeaker(Speaker speaker) throws Exception {

		log.info("Updating Speaker [" + speaker.getName() + "]");
		em.merge(speaker);
		roomEventSrc.fire(speaker);

	}
	
	public Speaker getSpeakerById(long speakerId) throws Exception {

		return em.find(Speaker.class, speakerId);
	}

	public List<Speaker> getAllSpeakers() throws Exception {

		return em.createNamedQuery(Speaker.FIND_ALL, Speaker.class)
				.getResultList();

	}
}

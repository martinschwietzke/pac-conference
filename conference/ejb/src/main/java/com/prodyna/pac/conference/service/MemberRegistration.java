package com.prodyna.pac.conference.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.pac.conference.model.Member;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Stateless
public class MemberRegistration {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Member> memberEventSrc;

	public void register(Member member) throws Exception
	{
		log.info("Registering " + member.getName());
		em.persist(member);
		memberEventSrc.fire(member);
	}
}

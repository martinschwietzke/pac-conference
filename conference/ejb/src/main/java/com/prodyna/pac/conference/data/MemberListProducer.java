package com.prodyna.pac.conference.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.pac.conference.model.Member;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RequestScoped
public class MemberListProducer {

	@Inject
	private MemberRepository memberRepository;

	private List<Member> members;

	@Produces
	@Named("members")
	public List<Member> getMembers()
	{
		return members;
	}

	public void onMemberListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Member member)
	{
		retrieveAllMembersOrderedByName();
	}

	@PostConstruct
	public void retrieveAllMembersOrderedByName()
	{
		members = memberRepository.findAllOrderedByName();
	}
}

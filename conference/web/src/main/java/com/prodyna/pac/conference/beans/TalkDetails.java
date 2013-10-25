package com.prodyna.pac.conference.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.TalkService;

@ManagedBean(name = "talkDetails")
@ViewScoped
public class TalkDetails {

	@Inject
	TalkService talkService;

	private Talk talk;

	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id) throws Exception
	{
		this.id = id;
		talk = talkService.getTalkById(id);
	}

	public Talk getTalk()
	{
		return talk;
	}

	public void setTalk(Talk talk)
	{
		this.talk = talk;
	}

	public List<Speaker> getSpeakers() throws Exception
	{
		return talkService.getSpeakersByTalk(id);
	}

}
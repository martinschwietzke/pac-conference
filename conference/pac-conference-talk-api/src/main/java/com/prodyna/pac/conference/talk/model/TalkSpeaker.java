package com.prodyna.pac.conference.talk.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.prodyna.pac.conference.common.model.AbstractEntity;
import com.prodyna.pac.conference.speaker.model.Speaker;

/**
 * Entity implementation class for Entity: TalkSpeaker
 * 
 */
@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = TalkSpeaker.FIND_SPEAKERS_BY_TALK_ID, query = "SELECT ts.speaker FROM TalkSpeaker ts WHERE ts.talk.id = :talkId"),
		@NamedQuery(name = TalkSpeaker.FIND_TALK_BY_SPEAKER_ID, query = "SELECT ts.talk FROM TalkSpeaker ts WHERE ts.speaker.id = :speakerId") })
@Table(name = "talkspeaker")
public class TalkSpeaker extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public final static String FIND_SPEAKERS_BY_TALK_ID = "de.prodyna.pac.conference.model.talkspeaker.findByTalkId";
	public final static String FIND_TALK_BY_SPEAKER_ID = "de.prodyna.pac.conference.model.talkspeaker.findBySpeakerId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	private Talk talk;

	@NotNull
	@ManyToOne
	private Speaker speaker;

	public TalkSpeaker() {
		super();
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Talk getTalk()
	{
		return this.talk;
	}

	public void setTalk(Talk talk)
	{
		this.talk = talk;
	}

	public Speaker getSpeaker()
	{
		return this.speaker;
	}

	public void setSpeaker(Speaker speaker)
	{
		this.speaker = speaker;
	}

}

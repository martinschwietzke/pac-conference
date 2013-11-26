package com.prodyna.pac.conference.test;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.common.init.ResourcesProducer;
import com.prodyna.pac.conference.common.interceptor.Logged;
import com.prodyna.pac.conference.common.interceptor.Performance;
import com.prodyna.pac.conference.common.model.AbstractEntity;
import com.prodyna.pac.conference.common.model.Entity;
import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;
import com.prodyna.pac.conference.conference.service.bean.ConferenceServiceBean;
import com.prodyna.pac.conference.room.model.Room;
import com.prodyna.pac.conference.room.service.RoomReferencedException;
import com.prodyna.pac.conference.room.service.RoomService;
import com.prodyna.pac.conference.room.service.bean.RoomServiceBean;
import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.model.TalkSpeaker;
import com.prodyna.pac.conference.talk.service.OutOfConferenceDateRangeException;
import com.prodyna.pac.conference.talk.service.RoomNotAvailableException;
import com.prodyna.pac.conference.talk.service.TalkService;
import com.prodyna.pac.conference.talk.service.bean.TalkServiceBean;

@RunWith(Arquillian.class)
public class TalkServiceTest {

	@Deployment
	public static Archive<?> createTestArchive()
	{

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
		war.addClass(Entity.class);
		war.addClass(AbstractEntity.class);
		war.addClass(Talk.class);
		war.addClass(Room.class);
		war.addClass(Conference.class);
		war.addClass(ConferenceService.class);
		war.addClass(ConferenceServiceBean.class);
		war.addClass(TalkService.class);
		war.addClass(ConferenceServiceException.class);
		war.addClass(RoomReferencedException.class);
		war.addClass(RoomNotAvailableException.class);
		war.addClass(OutOfConferenceDateRangeException.class);
		war.addClass(TalkServiceBean.class);
		war.addClass(RoomService.class);
		war.addClass(RoomServiceBean.class);
		war.addClass(ResourcesProducer.class);
		war.addClass(Speaker.class);
		war.addClass(TalkSpeaker.class);
		war.addClass(Logged.class);
		war.addClass(Performance.class);
		war.addAsResource("META-INF/test-persistence.xml",
				"META-INF/persistence.xml");
		war.addAsResource("import.sql", "import.sql");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		// Deploy our test datasource
		war.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		return war;
	}

	@Inject
	TalkService talkService;

	@Inject
	ConferenceService conferenceService;

	@Inject
	RoomService roomService;

	@Inject
	Logger log;

	@Inject
	EntityManager em;

	@Test
	public void testCreateTalk() throws Exception
	{
		int expectedTalkCount = 5;
		long unusedRoomId = 4;
		long conferenceId = 1;

		Assert.assertEquals(expectedTalkCount, talkService.getAllTalks().size());

		Conference conf = conferenceService.getConferenceById(conferenceId);
		Room room = roomService.getRoomById(unusedRoomId);
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 10, 5, 15, 0);
		Date start = new Date();
		Talk t = new Talk();
		t.setConference(conf);
		t.setDescription("description");
		t.setDuration(120);
		t.setStart(start);
		t.setName("name");
		t.setRoom(room);

		Talk newTalk = talkService.createTalk(t);

		Assert.assertNotNull(newTalk.getId());
		Assert.assertTrue(newTalk.getId() > 0);
		Assert.assertEquals(t.getStart(), newTalk.getStart());
		Assert.assertEquals(t.getEnd(), newTalk.getEnd());
		Assert.assertEquals(t.getDescription(), newTalk.getDescription());
		Assert.assertEquals(t.getRoom(), newTalk.getRoom());
		Assert.assertEquals(t.getName(), newTalk.getName());

		int conferenceCountNew = talkService.getAllTalks().size();

		Assert.assertEquals(conferenceCountNew, expectedTalkCount + 1);

	}

	@Test
	public void testDeleteTalk() throws Exception
	{
		Talk talk1 = em.find(Talk.class, 1l);

		Assert.assertNotNull(talk1);

		talkService.deleteTalk(talk1.getId());

		Talk talk1Del = em.find(Talk.class, talk1.getId());

		Assert.assertNull(talk1Del);

		Talk talk2 = em.find(Talk.class, 2l);

		Assert.assertNotNull(talk2);

		talkService.deleteTalk(talk2.getId());

		Talk talk2Del = em.find(Talk.class, talk2.getId());

		Assert.assertNull(talk2Del);

	}

}

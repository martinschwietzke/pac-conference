package com.prodyna.pac.conference.test;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.exception.ConferenceServiceException;
import com.prodyna.pac.conference.init.ResourcesProducer;
import com.prodyna.pac.conference.interceptor.Logged;
import com.prodyna.pac.conference.interceptor.Performance;
import com.prodyna.pac.conference.model.AbstractEntity;
import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.model.Entity;
import com.prodyna.pac.conference.model.Room;
import com.prodyna.pac.conference.model.Speaker;
import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.model.TalkSpeaker;
import com.prodyna.pac.conference.service.OutOfConferenceDateRangeException;
import com.prodyna.pac.conference.service.RoomNotAvailableException;
import com.prodyna.pac.conference.service.RoomReferencedException;
import com.prodyna.pac.conference.service.RoomService;
import com.prodyna.pac.conference.service.TalkService;
import com.prodyna.pac.conference.service.bean.RoomServiceBean;

@RunWith(Arquillian.class)
public class RoomServiceTest {

	@Deployment
	public static Archive<?> createTestArchive()
	{

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
		war.addClass(Entity.class);
		war.addClass(AbstractEntity.class);
		war.addClass(Talk.class);
		war.addClass(Room.class);
		war.addClass(TalkService.class);
		war.addClass(ConferenceServiceException.class);
		war.addClass(RoomReferencedException.class);
		war.addClass(RoomNotAvailableException.class);
		war.addClass(OutOfConferenceDateRangeException.class);
		war.addClass(RoomService.class);
		war.addClass(Speaker.class);
		war.addClass(Conference.class);
		war.addClass(RoomServiceBean.class);
		war.addClass(ResourcesProducer.class);
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
	RoomService roomService;

	@Inject
	Logger log;

	@Test(expected = RoomReferencedException.class)
	public void testDeleteRoomInUse() throws Exception
	{
		Room r = roomService.getRoomById(1);
		roomService.deleteRoom(r);
	}

}

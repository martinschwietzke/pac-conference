package com.prodyna.pac.conference.room.test;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.exception.ConferenceServiceException;
import com.prodyna.pac.conference.init.ResourcesProducer;
import com.prodyna.pac.conference.interceptor.Logged;
import com.prodyna.pac.conference.interceptor.Performance;
import com.prodyna.pac.conference.model.AbstractEntity;
import com.prodyna.pac.conference.model.Entity;
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

	@Test
	public void testCreateRoom() throws Exception
	{
		Room room = new Room();
		room.setCapacity(100);
		room.setName("room");

		Room r = roomService.createRoom(room);

		Assert.assertEquals(100, r.getCapacity());
		Assert.assertEquals("room", r.getName());

	}

	@Test
	public void testUpdateRoom() throws Exception
	{
		Room room = roomService.getRoomById(1);
		room.setCapacity(999);
		room.setName("Room updated");

		Room r = roomService.updateRoom(room);

		Assert.assertEquals(999, r.getCapacity());
		Assert.assertEquals("Room updated", r.getName());

	}

	@Test
	public void testGetAllRooms() throws Exception
	{
		List<Room> rooms = roomService.getAllRooms();

		Assert.assertEquals(3, rooms.size());
		Assert.assertEquals(rooms.get(0).getName(), "Raum 1");
		Assert.assertEquals(rooms.get(0).getCapacity(), 50);
		Assert.assertEquals(rooms.get(1).getName(), "Raum 2");
		Assert.assertEquals(rooms.get(1).getCapacity(), 100);
		Assert.assertEquals(rooms.get(2).getName(), "Raum 3");
		Assert.assertEquals(rooms.get(2).getCapacity(), 75);

	}
}

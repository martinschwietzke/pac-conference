package com.prodyna.pac.conference.room.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.room.api.model.Room;
import com.prodyna.pac.conference.room.api.service.RoomReferencedException;
import com.prodyna.pac.conference.room.api.service.RoomService;

@RunWith(Arquillian.class)
public class RoomServiceTest {

	private static final String TEST_DATA_SET_JSON_FILE = "testDataSet.json";

	private static final long COUNT_ALL_ROOMS = 4;

	@Deployment
	public static Archive<?> createTestArchive()
	{

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
		List<File> libs = new ArrayList<File>();
		libs.addAll(Arrays.asList(Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.resolve(
						Arrays.asList(
								"com.prodyna.pac:pac-conference-room-impl",
								"com.prodyna.pac:pac-conference-room-api",
								"com.prodyna.pac:pac-conference-conference-api",
								"com.prodyna.pac:pac-conference-speaker-api",
								"com.prodyna.pac:pac-conference-talk-api",
								"com.prodyna.pac:pac-conference-common"))
				.withoutTransitivity().asFile()));

		war.addAsLibraries(libs.toArray(new File[0]));

		war.addAsResource("META-INF/test-persistence.xml",
				"META-INF/persistence.xml");
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
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testDeleteRoomInUse() throws Exception
	{
		Room r = roomService.getRoomById(1);
		roomService.deleteRoom(r);
	}

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
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
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
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
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testGetAllRooms() throws Exception
	{
		List<Room> rooms = roomService.getAllRooms();

		Assert.assertEquals(COUNT_ALL_ROOMS, rooms.size());
		Assert.assertEquals(rooms.get(0).getName(), "Room 1");
		Assert.assertEquals(rooms.get(0).getCapacity(), 50);
		Assert.assertEquals(rooms.get(1).getName(), "Room 2");
		Assert.assertEquals(rooms.get(1).getCapacity(), 100);
		Assert.assertEquals(rooms.get(2).getName(), "Room 3 - unreferenced");
		Assert.assertEquals(rooms.get(2).getCapacity(), 100);
		Assert.assertEquals(rooms.get(3).getName(), "Room 4");
		Assert.assertEquals(rooms.get(3).getCapacity(), 100);

	}
}

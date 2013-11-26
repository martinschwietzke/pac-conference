package com.prodyna.pac.conference.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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

import com.prodyna.pac.conference.conference.api.model.Conference;
import com.prodyna.pac.conference.room.api.model.Room;
import com.prodyna.pac.conference.talk.api.model.Talk;
import com.prodyna.pac.conference.talk.api.service.TalkService;

@RunWith(Arquillian.class)
public class TalkServiceTest {

	private static final String TEST_DATA_SET_JSON_FILE = "testDataSet.json";

	private static final long COUNT_ALL_TALKS = 2;

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
								"com.prodyna.pac:pac-conference-conference-api",
								"com.prodyna.pac:pac-conference-room-api",
								"com.prodyna.pac:pac-conference-common",
								"com.prodyna.pac:pac-conference-speaker-api",
								"com.prodyna.pac:pac-conference-speaker-impl",
								"com.prodyna.pac:pac-conference-talk-api",
								"com.prodyna.pac:pac-conference-talk-impl"))
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
	TalkService talkService;

	@Inject
	Logger log;

	@Inject
	EntityManager em;

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testCreateTalk() throws Exception
	{
		int expectedTalkCount = 2;
		long unusedRoomId = 3;
		long conferenceId = 1;

		Assert.assertEquals(expectedTalkCount, talkService.getAllTalks().size());

		Conference conf = em.find(Conference.class, conferenceId);

		Room room = em.find(Room.class, unusedRoomId);
		Calendar start = Calendar.getInstance();
		start.set(2013, 10, 1, 15, 0);

		Talk t = new Talk();
		t.setConference(conf);
		t.setDescription("description");
		t.setDuration(120);
		t.setStart(start.getTime());
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
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
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

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testGetAllConference() throws Exception
	{
		List<Talk> c = talkService.getAllTalks();
		Assert.assertEquals(COUNT_ALL_TALKS, c.size());

	}

}

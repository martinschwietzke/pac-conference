package com.prodyna.pac.conference.conference.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.prodyna.pac.conference.conference.api.service.ConferenceService;
import com.prodyna.pac.conference.conference.impl.service.bean.ConferenceServiceBean;

/**
 * Test class for {@link ConferenceServiceBean}.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@RunWith(Arquillian.class)
public class ConferenceServiceTest {

	private static final String TEST_DATA_SET_JSON_FILE = "testDataSet.json";

	private static final long COUNT_ALL_CONFERENCES = 4;

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
								"com.prodyna.pac:pac-conference-common",
								"com.prodyna.pac:pac-conference-conference-api",
								"com.prodyna.pac:pac-conference-conference-impl"))
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
	ConferenceService conferenceService;

	@Inject
	EntityManager entityManager;

	@Inject
	Logger log;

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testGetConferenceById() throws Exception
	{
		Conference c = this.conferenceService.getConferenceById(1);

		Calendar start = GregorianCalendar.getInstance();
		start.set(2013, 10, 1, 0, 0, 0);
		start.set(Calendar.MILLISECOND, 0);

		Calendar end = GregorianCalendar.getInstance();
		end.set(2013, 10, 15, 0, 0, 0);
		end.set(Calendar.MILLISECOND, 0);

		Assert.assertNotNull(c);
		Assert.assertEquals("Konferenz 1", c.getName());
		Assert.assertEquals("Eschborn", c.getLocation());
		Assert.assertEquals(start.getTime(), c.getStart());
		Assert.assertEquals(end.getTime(), c.getEnd());
		Assert.assertEquals(false, c.isArchived());

	}

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testCreateConference() throws Exception
	{

		Date start = new Date();
		Date end = new Date(start.getTime() + 10000);
		Conference c = new Conference();
		c.setArchived(false);
		c.setDescription("description");
		c.setStart(start);
		c.setEnd(end);
		c.setLocation("location");
		c.setName("name");

		Conference newConf = conferenceService.createConference(c);

		Assert.assertNotNull(newConf.getId());
		Assert.assertTrue(newConf.getId() > 0);
		Assert.assertEquals(c.isArchived(), newConf.isArchived());
		Assert.assertEquals(c.getStart(), newConf.getStart());
		Assert.assertEquals(c.getEnd(), newConf.getEnd());
		Assert.assertEquals(c.getDescription(), newConf.getDescription());
		Assert.assertEquals(c.getLocation(), newConf.getLocation());
		Assert.assertEquals(c.getName(), newConf.getName());

		long count = (Long) entityManager.createQuery(
				"select count(c) from Conference c").getSingleResult();
		Assert.assertEquals(COUNT_ALL_CONFERENCES + 1, count);

	}

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testGetAllConference() throws Exception
	{
		List<Conference> c = conferenceService.getAllConferences();
		Assert.assertEquals(COUNT_ALL_CONFERENCES, c.size());

	}

	@Test
	@UsingDataSet(TEST_DATA_SET_JSON_FILE)
	public void testDeleteConference() throws Exception
	{
		// delete conferencee 3 - by entity

		Conference conf3 = entityManager.find(Conference.class, 3L);

		Assert.assertNotNull(conf3);
		Assert.assertNotNull(conf3.getId());

		conferenceService.deleteConference(conf3);

		Conference conf3Del = entityManager.find(Conference.class, 3L);

		Assert.assertNull(conf3Del);

		// delete conference 4 - only by id

		Conference conf4 = entityManager.find(Conference.class, 4L);

		Assert.assertNotNull(conf4);
		Assert.assertNotNull(conf4.getId());

		conferenceService.deleteConference(conf4.getId());

		Conference conf4Del = entityManager.find(Conference.class, 4L);

		Assert.assertNull(conf4Del);

	}
}

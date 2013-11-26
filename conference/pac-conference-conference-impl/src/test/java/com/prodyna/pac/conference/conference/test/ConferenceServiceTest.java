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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.conference.model.Conference;
import com.prodyna.pac.conference.conference.service.ConferenceService;

@RunWith(Arquillian.class)
public class ConferenceServiceTest {

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
		// war.addAsResource("import.sql");

		return war;
	}

	@Inject
	ConferenceService conferenceService;

	@Inject
	EntityManager entityManager;

	@Inject
	UserTransaction utx;

	@Inject
	Logger log;

	@Before
	public void clear() throws NotSupportedException, SystemException
	{

	}

	@After
	public void clearAfter() throws NotSupportedException, SystemException,
			SecurityException, IllegalStateException, RollbackException,
			HeuristicMixedException, HeuristicRollbackException
	{
		// utx.begin();
		// entityManager.joinTransaction();
		// System.out.println("Dumping old records...");
		//
		// entityManager.createNativeQuery("delete from talkspeaker")
		// .executeUpdate();
		// entityManager.createNativeQuery("delete from talk").executeUpdate();
		// entityManager.createNativeQuery("delete from room").executeUpdate();
		// entityManager.createQuery("delete from Conference").executeUpdate();
		//
		// utx.commit();
	}

	@Test
	public void testGetConferenceById() throws Exception
	{
		Conference c = this.conferenceService.getConferenceById(1);

		Calendar instance = GregorianCalendar.getInstance();
		instance.set(2013, 9, 1, 0, 0, 0);
		instance.set(Calendar.MILLISECOND, 0);

		Assert.assertNotNull(c);
		Assert.assertEquals("Konferenz 1", c.getName());
		Assert.assertEquals("Eschborn", c.getLocation());
		// Assert.assertEquals(instance.getTime(), c.getStart());
		// Assert.assertEquals(false, c.isArchived());

	}

	@Test
	public void testCreateConference() throws Exception
	{
		int conferenceCount = conferenceService.getAllConferences().size();

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

		int conferenceCountNew = conferenceService.getAllConferences().size();

		Assert.assertEquals(conferenceCountNew, conferenceCount + 1);

	}
}

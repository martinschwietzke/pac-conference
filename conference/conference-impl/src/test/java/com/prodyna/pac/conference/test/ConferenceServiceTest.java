package com.prodyna.pac.conference.test;

import java.util.Date;
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

import com.prodyna.pac.conference.init.ResourcesProducer;
import com.prodyna.pac.conference.interceptor.Logged;
import com.prodyna.pac.conference.interceptor.Performance;
import com.prodyna.pac.conference.model.AbstractEntity;
import com.prodyna.pac.conference.model.Conference;
import com.prodyna.pac.conference.model.Entity;
import com.prodyna.pac.conference.service.ConferenceService;
import com.prodyna.pac.conference.service.bean.ConferenceServiceBean;

@RunWith(Arquillian.class)
public class ConferenceServiceTest {

	@Deployment
	public static Archive<?> createTestArchive()
	{

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
		war.addClass(Entity.class);
		war.addClass(AbstractEntity.class);
		war.addClass(Conference.class);
		war.addClass(ConferenceService.class);
		war.addClass(ConferenceServiceBean.class);
		war.addClass(ResourcesProducer.class);
		war.addClass(Logged.class);
		war.addClass(Performance.class);
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
	Logger log;

	@Test
	public void testCreateConference() throws Exception
	{
		int conferenceCount = conferenceService.getAllConferences().size();

		Date start = new Date();
		Date end = new Date();
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

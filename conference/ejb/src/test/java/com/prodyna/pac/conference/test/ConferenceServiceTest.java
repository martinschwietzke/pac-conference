package com.prodyna.pac.conference.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.conference.init.ResourcesProducer;
import com.prodyna.pac.conference.interceptor.Logged;
import com.prodyna.pac.conference.interceptor.Performance;
import com.prodyna.pac.conference.service.ConferenceService;
import com.prodyna.pac.conference.service.bean.ConferenceServiceBean;

@RunWith(Arquillian.class)
public class ConferenceServiceTest {

	public ConferenceServiceTest() {
		System.out.println("INNNNNNNNNNNNNNNNNNIT");
	}

	@Deployment
	public static Archive<?> createTestArchive()
	{

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
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

	// @Inject
	// ConferenceService conferenceService;

	// @Inject
	// Logger log;

	@Test
	@RunAsClient
	public void testFoo() throws Exception
	{
		System.out
				.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	@Test
	@RunAsClient
	public void testRegister() throws Exception
	{
		System.out
				.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		// Member newMember = new Member();
		// newMember.setName("Jane Doe");
		// newMember.setEmail("jane@mailinator.com");
		// newMember.setPhoneNumber("2125551234");
		// memberRegistration.register(newMember);
		// assertNotNull(newMember.getId());
		// // log.info(newMember.getName() + " was persisted with id " +
		// // newMember.getId());
	}

}

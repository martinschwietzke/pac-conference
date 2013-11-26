package com.prodyna.pac.conference.common.init;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ResourcesProducer {

	@Produces
	@PersistenceContext
	private EntityManager em;

	private QueueConnectionFactory queueConnectionFactory;

	private InitialContext initialContext;

	public ResourcesProducer() {
		try {
			initialContext = new InitialContext();
			queueConnectionFactory = (QueueConnectionFactory) initialContext
					.lookup("ConnectionFactory");
		} catch (NamingException e) {
			throw new RuntimeException("Error while looking up InitialContext",
					e);
		}
	}

	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint)
	{
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}

	@Produces
	public InitialContext produceInitialContext()
	{
		return initialContext;
	}

	@Produces
	public QueueConnectionFactory produceQueueConnectionFactory()
	{
		return queueConnectionFactory;
	}
}

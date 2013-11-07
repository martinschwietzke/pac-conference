package com.prodyna.pac.conference.service.decorator;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

import com.prodyna.pac.conference.model.Talk;
import com.prodyna.pac.conference.service.TalkService;

@Decorator
public abstract class TalkServiceDecorator implements TalkService {

	public static final String TALK_UPDATE_QUEUE_NAME = "queue/talkupdate";

	@Inject
	@Delegate
	@Any
	private TalkService talkService;

	@Inject
	private Logger logger;

	@Inject
	private InitialContext initialContext;

	@Inject
	private QueueConnectionFactory queueConnectionFactory;

	private QueueConnection queueConnection;

	private QueueSession queueSession;

	private QueueSender sender;

	@PostConstruct
	private void initQueue()
	{
		try {
			queueConnection = queueConnectionFactory.createQueueConnection();

			Queue queue = (Queue) initialContext.lookup(TALK_UPDATE_QUEUE_NAME);

			queueSession = queueConnection.createQueueSession(true,
					QueueSession.AUTO_ACKNOWLEDGE);

			sender = queueSession.createSender(queue);

		} catch (Exception e) {
			throw new RuntimeException("Error on creating queue ["
					+ TALK_UPDATE_QUEUE_NAME + "]", e);
		}
	}

	@PreDestroy
	private void stopQueue()
	{
		try {
			sender.close();
			queueSession.commit();
			queueSession.close();
			queueConnection.stop();
			queueConnection.close();

		} catch (JMSException e) {
			throw new RuntimeException("Error on stopping quere ["
					+ TALK_UPDATE_QUEUE_NAME + "]", e);
		}
	}

	@Override
	public void deleteTalk(Talk talk) throws Exception
	{
		talkService.deleteTalk(talk);

		try {
			MapMessage message = queueSession.createMapMessage();
			message.setString("action", "delete");
			message.setLong("talkId", talk.getId());
			sender.send(message);
		} catch (JMSException e) {
			logger.log(
					Level.SEVERE,
					"Error on sending message for deleting talk ["
							+ talk.getId() + "]", e);
		}
	}
}

package com.prodyna.pac.conference.talk.service.decorator;

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

import com.prodyna.pac.conference.common.exception.ConferenceServiceException;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.TalkService;

/**
 * Service {@link Decorator} for {@link TalkService} which sends JMS
 * notifications to queue if changes happens to {@link Talk} entities.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Decorator
public abstract class TalkServiceDecorator implements TalkService {

	public static final String TALK_CHANGE_QUEUE_NAME = "queue/talkchange";

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

			Queue queue = (Queue) initialContext.lookup(TALK_CHANGE_QUEUE_NAME);

			queueSession = queueConnection.createQueueSession(true,
					QueueSession.AUTO_ACKNOWLEDGE);

			sender = queueSession.createSender(queue);

		} catch (Exception e) {
			throw new RuntimeException("Error on creating queue ["
					+ TALK_CHANGE_QUEUE_NAME + "]", e);
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
			throw new RuntimeException("Error on stopping queue ["
					+ TALK_CHANGE_QUEUE_NAME + "]", e);
		}
	}

	@Override
	public Talk createTalk(Talk talk) throws ConferenceServiceException
	{
		Talk t = talkService.createTalk(talk);
		try {
			MapMessage message = queueSession.createMapMessage();
			message.setString("action", "create");
			message.setString("talkName", talk.getName());
			message.setLong("talkId", talk.getId());
			sender.send(message);
			queueSession.commit();
		} catch (JMSException e) {
			logger.log(
					Level.SEVERE,
					"Error on sending message for creating talk ["
							+ talk.getId() + "]", e);
		}

		return t;

	}

	@Override
	public Talk updateTalk(Talk talk) throws ConferenceServiceException
	{
		Talk t = talkService.updateTalk(talk);

		try {
			MapMessage message = queueSession.createMapMessage();
			message.setString("action", "update");
			message.setLong("talkId", talk.getId());
			message.setString("talkName", talk.getName());
			sender.send(message);
			queueSession.commit();
		} catch (JMSException e) {
			logger.log(
					Level.SEVERE,
					"Error on sending message for updating talk ["
							+ talk.getId() + "]", e);
		}

		return t;

	}

	@Override
	public void deleteTalk(Talk talk) throws ConferenceServiceException
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

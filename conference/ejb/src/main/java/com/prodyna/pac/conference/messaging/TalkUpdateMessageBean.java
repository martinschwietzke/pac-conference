package com.prodyna.pac.conference.messaging;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.prodyna.pac.conference.service.decorator.TalkServiceDecorator;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = TalkServiceDecorator.TALK_UPDATE_QUEUE_NAME) }, mappedName = TalkServiceDecorator.TALK_UPDATE_QUEUE_NAME)
public class TalkUpdateMessageBean implements MessageListener {

	@Inject
	private Logger log;

	public TalkUpdateMessageBean() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message)
	{
		log.info("Got message [" + message + "]");
	}
}

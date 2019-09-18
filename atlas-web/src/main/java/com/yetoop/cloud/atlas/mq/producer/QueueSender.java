package com.yetoop.cloud.atlas.mq.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yetoop.cloud.atlas.mq.MemberViewArtwork;
import com.yetoop.cloud.atlas.mq.MemberViewWorks;

@Component("queueSender")
public class QueueSender {
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
 

	private String worksQueue = "works.queue";
	private String artworksQueue = "artworks.queue";

	private static final Logger log = LoggerFactory.getLogger(QueueSender.class);

	public void send(MemberViewWorks memberViewWorks) {
		if (log.isDebugEnabled()) {
			log.debug("send works.memberViewWorks:", memberViewWorks);
		}
		final String message = JSON.toJSONString(memberViewWorks);
		if (log.isDebugEnabled()) {
			log.debug("send works.queue:", message);
		}
		jmsTemplate.send(worksQueue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	
	
	public void send(MemberViewArtwork memberViewArtwork) {
		final String message = JSON.toJSONString(memberViewArtwork);
		if (log.isDebugEnabled()) {
			log.debug("send artworks.queue:", message);
		}
		jmsTemplate.send(artworksQueue, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
}

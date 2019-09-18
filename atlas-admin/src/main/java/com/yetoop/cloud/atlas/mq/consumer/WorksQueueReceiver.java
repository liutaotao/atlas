package com.yetoop.cloud.atlas.mq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yetoop.cloud.atlas.mq.MemberViewWorks;
import com.yetoop.cloud.atlas.service.AtalasIndexService;

@Component
public class WorksQueueReceiver implements MessageListener {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(WorksQueueReceiver.class);

	@Autowired
	private AtalasIndexService atlasIndexService;

	public void onMessage(Message message) {
		try {
			String json = ((TextMessage) message).getText();
			if (log.isDebugEnabled()) {
				log.info("WorksQueueReceiver接收到消息：" + json);
			}
			MemberViewWorks memberViewWorks = JSON.parseObject(json, MemberViewWorks.class);
			if (log.isDebugEnabled()) {
				log.info("memberViewWorks:" + memberViewWorks);
			}

			this.atlasIndexService.logMemberViewWorks(memberViewWorks);

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
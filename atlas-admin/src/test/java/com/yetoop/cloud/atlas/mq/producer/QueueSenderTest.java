package com.yetoop.cloud.atlas.mq.producer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QueueSenderTest {
	
	@Before
	public void setUp() {
		String[] paths = { "classpath:spring/spring-service-test.xml" };
		ApplicationContext context = new ClassPathXmlApplicationContext(paths);
 	}

	@Test
	public void test() {
	}
}

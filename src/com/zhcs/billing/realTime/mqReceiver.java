package com.zhcs.billing.realTime;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.JmsException;

import com.zhcs.billing.threadPool.ThreadPool;

public class mqReceiver {
	private static class SingletonHolder {
		private static final mqReceiver INSTANCE = new mqReceiver();
	}

	private mqReceiver() {

	}

	public static final mqReceiver getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private ApplicationContext ctx = null;

	public void init() {
		this.ctx = new ClassPathXmlApplicationContext("file:"
				+ System.getProperty("user.dir")
				+ "/config/applicationContext.xml");
		while (true) {
		}
	}

	public void close() {

	}

	public void receive(TextMessage message) throws JmsException, JMSException {
		// System.out.println(message.getStringProperty("phrCode"));
		while(!ThreadPool.getInstance().AddTask(new ability(message.getText())))
		{
			
		}
		
	}

}

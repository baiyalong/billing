package com.zhcs.billing.realTime;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.JmsException;

import com.zhcs.billing.threadPool.ThreadPool;

public class MqReceiver {
	private static class SingletonHolder {
		private static final MqReceiver INSTANCE = new MqReceiver();
	}

	private MqReceiver() {

	}

	public static final MqReceiver getInstance() {
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

	public void receive(TextMessage message) throws JmsException, JMSException,
			InterruptedException {
		// System.out.println(message.getStringProperty("phrCode"));
		String li[] = Msg.liMsg(message.getText());
		for (String l : li) {
			while (!ThreadPool.getInstance().AddTask(new Ability(l))) {
				Thread.sleep(10);
			}

		}

	}

}

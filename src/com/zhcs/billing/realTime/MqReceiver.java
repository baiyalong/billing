package com.zhcs.billing.realTime;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.JmsException;

import com.zhcs.billing.threadPool.ThreadPool;

public class MqReceiver implements Runnable {
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
				+ System.getProperty("user.dir") + "/config/mq.xml");
		while (true) {
		}
	}

	public void close() {

	}

	// 能力
	public void receive(TextMessage message) throws JmsException, JMSException,
			InterruptedException {
		// System.out.println(message.getStringProperty("phrCode"));
		String li[] = Msg.liMsg(message.getText());
		for (String l : li) {
			while (!ThreadPool.getInstance().AddTask(new ThreadAbility(l))) {
				Thread.sleep(10);
			}

		}

	}

	// 流量
	public void receive1(TextMessage message) throws JmsException,
			JMSException, InterruptedException {
	}

	// 应用
	public void receive2(TextMessage message) throws JmsException,
			JMSException, InterruptedException {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.init();
	}

}

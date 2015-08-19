package com.zhcs.billing.realTime;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.main.Main;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.PropertieUtils;

public class ThreadMq implements Runnable {
	private static LoggerUtil logUtil = LoggerUtil.getLogger(ThreadMq.class);
	private static Logger log = LoggerFactory.getLogger(ThreadMq.class);

	private String prefix;
	private String url;
	private String topic;
	private String clientID;
	private String durableSubscriptionName;

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Topic destination;
	private TopicSubscriber consumer;

	public ThreadMq(String prefix) {
		// config
		if (prefix != "ability" && prefix != "flow" && prefix != "app") {
			log.error("MQ���ն˳�ʼ�������쳣��");
			logUtil.error("MQ���ն˳�ʼ�������쳣��");
			return;
		}
		this.prefix = prefix;
		Properties props = new PropertieUtils().read("mq.properties");
		this.url = props.getProperty(prefix + "_url");
		this.topic = props.getProperty(prefix + "_topic");
		this.clientID = props.getProperty(prefix + "_clientID");
		this.durableSubscriptionName = props.getProperty(prefix
				+ "_durableSubscriptionName");

	}

	public void init() {
		// init
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, this.url);
		try {
			this.connection = connectionFactory.createConnection();
			this.connection.setClientID(this.clientID);
			this.connection.start();
			this.session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			this.destination = session.createTopic(this.topic);
			this.consumer = session.createDurableSubscriber(destination,
					this.durableSubscriptionName);

			// listen
			// ---------------------------------------------------------------------------------------
			if (this.prefix == "ability") {
				consumer.setMessageListener(new MessageListener() {

					@Override
					public void onMessage(Message msg) {
						// TODO Auto-generated method stub
						try {
							TextMessage textMsg = (TextMessage) msg;
							String text = textMsg.getText();
							System.out.println(text);
							log.info("���գ�" + text);
							logUtil.info("���գ�" + text);

							// �����̴߳���
							int a = 0;
							while (true) {

								int count = Thread.activeCount();
								System.out.println("Thread.activeCount() "
										+ count);
								if (count > 1000) {
									try {
										Thread.sleep(1000);
										continue;
									} catch (InterruptedException e) { // TODO

										e.printStackTrace();
									}
								}

								boolean res = ThreadPool.getInstance().AddTask(
										new ThreadAbility(text));

								System.out.println(a++);
								if (!res) {
									try {
										Thread.sleep(100);
										System.out
												.println(a++
														+ "ThreadPool.getInstance().AddTask  false!");
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else 

									/*
									 * try { Thread.sleep(10); } catch
									 * (InterruptedException e) { // TODO
									 * 
									 * e.printStackTrace(); }
									 */
									break;

								
							}

						} catch (JMSException e) {
							e.printStackTrace();
							log.error("MQ������Ϣ�쳣��");
							logUtil.error("MQ������Ϣ�쳣��");
						}
					}
				});
				// ----------------------------------------------------------------------------------------
			} else if (this.prefix == "flow") {
				consumer.setMessageListener(new MessageListener() {

					@Override
					public void onMessage(Message msg) {
						// TODO Auto-generated method stub
						try {
							TextMessage textMsg = (TextMessage) msg;
							String text = textMsg.getText();
							System.out.println(text);
							log.info("���գ�" + text);
							logUtil.info("���գ�" + text);

							// �����̴߳���

							while (!ThreadPool.getInstance().AddTask(
									new ThreadDataFlow(text))) {
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} catch (JMSException e) {
							e.printStackTrace();
							log.error("MQ������Ϣ�쳣��");
							logUtil.error("MQ������Ϣ�쳣��");
						}
					}
				});
				// -------------------------------------------------------------------------------------------
			} else if (this.prefix == "app") {
				consumer.setMessageListener(new MessageListener() {

					@Override
					public void onMessage(Message msg) {
						// TODO Auto-generated method stub
						try {
							TextMessage textMsg = (TextMessage) msg;
							String text = textMsg.getText();
							System.out.println(text);
							log.info("���գ�" + text);
							logUtil.info("���գ�" + text);

							// �����̴߳���

							while (!ThreadPool.getInstance().AddTask(
									new ThreadApp(text))) {
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} catch (JMSException e) {
							e.printStackTrace();
							log.error("MQ������Ϣ�쳣��");
							logUtil.error("MQ������Ϣ�쳣��");
						}
					}
				});
			} else {
				log.error("MQ���ն˳�ʼ�������쳣��");
				logUtil.error("MQ���ն˳�ʼ�������쳣��");
				return;
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(this.prefix + "MQ���ն˳�ʼ��ʧ�ܣ�");
			logUtil.error(this.prefix + "MQ���ն˳�ʼ��ʧ�ܣ�");
		}
		log.info(this.prefix + "MQ���ն˳�ʼ���ɹ���");
		logUtil.info(this.prefix + "MQ���ն˳�ʼ���ɹ���");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			// ������Ϣ
			Message message = null;
			try {
				message = consumer.receive();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(this.prefix + "MQ������Ϣʧ�ܣ�");
				logUtil.error(this.prefix + "MQ������Ϣʧ�ܣ�");
			}
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = null;
				try {
					text = textMessage.getText();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error(this.prefix + "MQ��ȡ��Ϣ�ı�ʧ�ܣ�");
					logUtil.error(this.prefix + "MQ��ȡ��Ϣ�ı�ʧ�ܣ�");
				}
				// System.out.println(this.prefix + "����text��" + text);
				log.info(this.prefix + "����text��" + text);
				logUtil.info(this.prefix + "����text��" + text);

				// �����̴߳���
				if (this.prefix == "ability") {
					while (!ThreadPool.getInstance().AddTask(
							new ThreadAbility(text))) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (this.prefix == "flow") {
					while (!ThreadPool.getInstance().AddTask(
							new ThreadDataFlow(text))) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (this.prefix == "app") {
					while (!ThreadPool.getInstance().AddTask(
							new ThreadApp(text))) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					log.error("MQ�̳߳�ʼ�������쳣��");
					logUtil.error("MQ�̳߳�ʼ�������쳣��");
					return;
				}
			} else {
				// System.out.println("����message��" + message);
				log.info(this.prefix + "����message��" + message);
				logUtil.info(this.prefix + "����message��" + message);
			}
		}
	}
}

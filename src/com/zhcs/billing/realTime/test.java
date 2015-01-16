package com.zhcs.billing.realTime;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import net.sf.json.JSONObject;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.Connection;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.zhcs.billing.use.dao.BillingQuery;
import com.zhcs.billing.util.CalendarUtil;
import com.zhcs.billing.util.DbUtil;

public class test {

	public static void main(String[] args) {

		String url = "http://localhost:8080/api/file/test";

		HttpClient httpclient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);

		JSONObject jso = new JSONObject();
		jso.put("userAccount", "param1");
		jso.put("abiCode", "param2");
		String req = jso.toString();
		postMethod.addRequestHeader("Content-Type", "application/json");
		StringRequestEntity requestEntity = null;
		try {
			requestEntity = new StringRequestEntity(req, "application/json",
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		postMethod.setRequestEntity(requestEntity);

		/*
		 * postMethod. postMethod.addParameters(postData);
		 */
		String body = "";
		for (int i = 0; i < 10; i++) {

			try {
				httpclient.executeMethod(postMethod);
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				body = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			postMethod.releaseConnection();

			System.out.println(body);
			JSONObject job = JSONObject.fromObject(body);
			String orderCode = job.getString("orderCode");
			String containerId = job.getString("containerId");
			System.out.println(orderCode);
			System.out.println(containerId);

		}

		/*
		 * String url = "tcp://124.160.193.83:21018";//
		 * "failover://(tcp://222.161.197.250:61616,tcp://222.161.197.250:61616)?initialReconnectDelay=100&rando mize=false"
		 * String user = ActiveMQConnectionFactory.DEFAULT_USER; String password
		 * = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
		 * ActiveMQConnectionFactory connectionFactory = new
		 * ActiveMQConnectionFactory( user, password, url); javax.jms.Connection
		 * connection; try { connection = connectionFactory.createConnection();
		 * connection.setClientID("id1qw"); try { connection.start(); } catch
		 * (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } // Session Session session =
		 * connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		 * 
		 * Topic destination = session.createTopic("queueNode2");
		 * 
		 * TopicSubscriber Consumer = session.createDurableSubscriber(
		 * destination, "id1qw");
		 * 
		 * Consumer.setMessageListener(new MessageListener() {
		 * 
		 * @Override public void onMessage(Message msg) { // TODO Auto-generated
		 * method stub try { TextMessage textMsg = (TextMessage) msg;
		 * System.out.println(textMsg.getText()); } catch (JMSException e) {
		 * e.printStackTrace(); } } }); } catch (JMSException e) {
		 * e.printStackTrace(); }
		 */

		// String url = "tcp://124.160.193.83:21018";
		// String user = ActiveMQConnectionFactory.DEFAULT_USER;
		// String password = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
		// ActiveMQConnectionFactory connectionFactory = new
		// ActiveMQConnectionFactory(
		// user, password, url);
		// javax.jms.Connection connection;
		// try {
		// connection = connectionFactory.createConnection();
		// connection.start();
		//
		// Session session = connection.createSession(false,
		// Session.AUTO_ACKNOWLEDGE);
		//
		// Destination destination = session.createTopic("queueNode2");
		//
		// MessageConsumer consumer = session.createConsumer(destination);
		//
		// while (true) {
		// Message message = consumer.receive();
		// if (message instanceof TextMessage) {
		// TextMessage textMessage = (TextMessage) message;
		// String text = textMessage.getText();
		// System.out.println("接收：" + text);
		// } else {
		// System.out.println("接收：" + message);
		// }
		// }
		// // consumer.close();
		// // session.close();
		// // connection.close();
		// } catch (JMSException e) {
		// e.printStackTrace();
		// }
	}

	// public static void main(String[] args) {
	//
	// // Calendar ca = Calendar.getInstance();
	// // int year = ca.get(Calendar.YEAR);// 获取年份
	// // int month = ca.get(Calendar.MONTH);// 获取月份
	// // int day = ca.get(Calendar.DATE);// 获取日
	// // String t0 = year + "-" + month + "-01";
	// // String t1 = year + "-" + (month + 1) + "-01";
	// //
	// // System.out.println(t0);
	// // System.out.println(t1);
	//
	// String sql = "SELECT ORDERID,SPERIOD,STARTTIME,ENDTIME,VALUE FROM ("
	// + "SELECT ORDERID,SPERIOD,STARTTIME,ENDTIME,VALUE FROM CSERVER"
	// + " UNION "
	// + "SELECT ORDERID,SPERIOD,STARTTIME,ENDTIME,VALUE FROM CNETWORK"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM CSECURITY"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM  CSTORE"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM CDB"
	// + " UNION "
	// + "SELECT ORDERID, SPERIOD,STARTTIME,ENDTIME,VALUE FROM CMIDSOFT"
	// + " UNION "
	// + "SELECT ORDERID ,SPERIOD,STARTTIME,ENDTIME,VALUE FROM CENGINE) T "
	// +
	// "WHERE STARTTIME < ? <= ENDTIME AND SPERIOD = ? AND VALUE > 0 GROUP BY ORDERID ORDER BY ORDERID";
	//
	// System.out.println(sql.toLowerCase());
	// System.out.println(CalendarUtil.lastMonth());
	// System.out.println(CalendarUtil.thisMonth());
	// System.out.println(CalendarUtil.yestoday());
	// System.out.println(CalendarUtil.today());
	//
	// // TODO Auto-generated method stub
	// // String s = BillingQuery.getOrderID("1607140522000187");
	// // System.out.println(s);
	// // DbUtil.initCalculate();
	//
	// // MqReceiver.getInstance().init();
	//
	// String cdr =
	// "2459|02|20131126143952|902|104|20131126142452|20131126143952|0000000010|                                        |"
	// + "\n"
	// +
	// "201311261425040001|20131126142504||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142504|20131126142504|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425060002|20131126142506||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142506|20131126142506|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425200003|20131126142520||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142520|20131126142520|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425050004|20131126142505||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142505|20131126142505|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425080005|20131126142508||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142508|20131126142508|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425100006|20131126142510||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142510|20131126142510|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425090007|20131126142509||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142509|20131126142509|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425100008|20131126142510||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142510|20131126142510|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425110009|20131126142511||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142511|20131126142511|0|||1|2|10||"
	// + "\n"
	// +
	// "201311261425130010|20131126142513||62142|17360010|||SI000001|42|||9|140|140|1736001000|||15606503408|15606503408|15606503408|0|20131126142513|20131126142513|0|||1|2|10||";
	//
	// }
}

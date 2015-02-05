package com.zhcs.billing.quartz.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.service.Mouse;
import com.zhcs.billing.realTime.MqReceiver;
import com.zhcs.billing.realTime.ThreadMq;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.util.DbUtil;
import com.zhcs.billing.util.LoggerUtil;

public class Main {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(Main.class);
	private static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ���ݿ����ӳ�ʼ��
		DbUtil.initBuisness();
		DbUtil.initCalculate();
		DbUtil.initEstimate();

		/*
		 * // ����ʵʱ�Ʒѳ�ʼ�� new ThreadMq("ability").init();
		 */

		/*
		 * // ����ʵʱ�Ʒѳ�ʼ�� new ThreadMq("flow").init();
		 * 
		 * // Ӧ��ʵʱ�Ʒѳ�ʼ�� new ThreadMq("app").init();
		 */

		// // �����ԼƷ�
		/*
		 * Mouse.run(1); // TASK_ID��Ӧ����Ӧ�Ķ�ʱ���� Mouse.run(2); // TASK_ID��Ӧ����Ӧ�Ķ�ʱ����
		 * Mouse.run(3); // TASK_ID��Ӧ����Ӧ�Ķ�ʱ���� Mouse.run(4); // TASK_ID��Ӧ����Ӧ�Ķ�ʱ����
		 * Mouse.run(5); // TASK_ID��Ӧ����Ӧ�Ķ�ʱ����
		 *//*Mouse.run(10); // TEST_SIMPLE
*/		Mouse.run(12); // TEST_ACCOUNT CHECK
		logUtil.info("===�Ʒ�ϵͳ����������===");
		log.info("===�Ʒ�ϵͳ����������===");
		logUtil.info("billing start....");
		log.info("billing start....");

	}
}

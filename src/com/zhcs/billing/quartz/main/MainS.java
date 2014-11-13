package com.zhcs.billing.quartz.main;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.service.MonthsThread;
import com.zhcs.billing.quartz.service.Mouse;
import com.zhcs.billing.util.DbUtil;
import com.zhcs.billing.util.LoggerUtil;

public class MainS {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(MainVM.class);
	private static Logger log = LoggerFactory.getLogger(MonthsThread.class);

	// ���ݿ����ӳ�ʼ��
	public void init() {
		DbUtil.initBuisness();
		DbUtil.initCalculate();
		DbUtil.initEstimate();
	}

	/**
	 * �Ʒ���ϵͳ����
	 * 
	 * @param args
	 * @throws Exception
	 * @throws Exception
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws Exception {

		new MainVM().init();
		Mouse.run(5); //2ΪTASK_ID��Ӧ����Ӧ�Ķ�ʱ����
		logUtil.info("===�Ʒ�ϵͳ(��ʹ����������������Դ�۷�)����������===");
		log.info("===�Ʒ�ϵͳ(��ʹ����������������Դ�۷�)����������===");
		logUtil.info("billing start....");
		log.info("billing start....");

	}


}
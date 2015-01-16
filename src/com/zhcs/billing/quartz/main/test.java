package com.zhcs.billing.quartz.main;

import java.util.List;
import java.util.Random;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.realTime.MqReceiver;
import com.zhcs.billing.realTime.ThreadMq;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.DbUtil;
import com.zhcs.billing.util.EnvironmentUtils;
import com.zhcs.billing.util.LoggerUtil;

public class test {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(test.class);
	private static Logger log = LoggerFactory.getLogger(test.class);

	public static void main(String[] args) {

		// ���ݿ����ӳ�ʼ��
		DbUtil.initBuisness();
		DbUtil.initCalculate();
		DbUtil.initEstimate();

		// ����ʵʱ�Ʒѳ�ʼ��
		new ThreadMq("ability").init();

		// ����ʵʱ�Ʒѳ�ʼ��
		new ThreadMq("flow").init();

		// Ӧ��ʵʱ�Ʒѳ�ʼ��
		new ThreadMq("app").init();

	}
}

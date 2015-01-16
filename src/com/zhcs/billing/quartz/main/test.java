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

		// 数据库连接初始化
		DbUtil.initBuisness();
		DbUtil.initCalculate();
		DbUtil.initEstimate();

		// 能力实时计费初始化
		new ThreadMq("ability").init();

		// 流量实时计费初始化
		new ThreadMq("flow").init();

		// 应用实时计费初始化
		new ThreadMq("app").init();

	}
}

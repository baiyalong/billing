package com.zhcs.billing.quartz.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.service.AccountCheckTreatment;
import com.zhcs.billing.realTime.MqReceiver;
import com.zhcs.billing.realTime.ThreadMq;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.util.CalendarUtil;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.DbUtil;
import com.zhcs.billing.util.EnvironmentUtils;
import com.zhcs.billing.util.LoggerUtil;

public class test {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(test.class);
	private static Logger log = LoggerFactory.getLogger(test.class);

	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		// new Date()为获取当前系统时间

		String ss = CalendarUtil.thisMonth().replace("-", "").substring(0, 6);

		// String now = df.format(new Date());
		// System.out.println(java.util.UUID.randomUUID().toString());
		HashMap map = new HashMap();
		new AccountCheckTreatment().execute(map);
		return;
		// 数据库连接初始化
		/*
		 * DbUtil.initBuisness(); DbUtil.initCalculate(); DbUtil.initEstimate();
		 * 
		 * // 能力实时计费初始化 new ThreadMq("ability").init();
		 * 
		 * // 流量实时计费初始化 new ThreadMq("flow").init();
		 * 
		 * // 应用实时计费初始化 new ThreadMq("app").init();
		 */

	}
}

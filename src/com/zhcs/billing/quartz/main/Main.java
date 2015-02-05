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
		// 数据库连接初始化
		DbUtil.initBuisness();
		DbUtil.initCalculate();
		DbUtil.initEstimate();

		/*
		 * // 能力实时计费初始化 new ThreadMq("ability").init();
		 */

		/*
		 * // 流量实时计费初始化 new ThreadMq("flow").init();
		 * 
		 * // 应用实时计费初始化 new ThreadMq("app").init();
		 */

		// // 周期性计费
		/*
		 * Mouse.run(1); // TASK_ID对应的相应的定时任务 Mouse.run(2); // TASK_ID对应的相应的定时任务
		 * Mouse.run(3); // TASK_ID对应的相应的定时任务 Mouse.run(4); // TASK_ID对应的相应的定时任务
		 * Mouse.run(5); // TASK_ID对应的相应的定时任务
		 *//*Mouse.run(10); // TEST_SIMPLE
*/		Mouse.run(12); // TEST_ACCOUNT CHECK
		logUtil.info("===计费系统正常启动！===");
		log.info("===计费系统正常启动！===");
		logUtil.info("billing start....");
		log.info("billing start....");

	}
}

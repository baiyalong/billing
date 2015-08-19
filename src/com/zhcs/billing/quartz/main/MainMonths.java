package com.zhcs.billing.quartz.main;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.service.MonthsThread;
import com.zhcs.billing.quartz.service.Mouse;
import com.zhcs.billing.util.DbUtil;
import com.zhcs.billing.util.LoggerUtil;

public class MainMonths {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(MainVM.class);
	private static Logger log = LoggerFactory.getLogger(MonthsThread.class);

	// 数据库连接初始化
	public void init() {
		DbUtil.initBuisness();
		DbUtil.initCalculate();
		DbUtil.initEstimate();
	}

	/**
	 * 计费子系统启动
	 * 
	 * @param args
	 * @throws Exception
	 * @throws Exception
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws Exception {
		// System.out.println(System.getProperty("java.endorsed.dirs"));
		new MainVM().init();
		Mouse.run(6); // 3为TASK_ID对应的相应的定时任务
		logUtil.info("===计费系统(月租费用按天扣费)正常启动！===");
		log.info("===计费系统(月租费用按天扣费)正常启动！===");
		logUtil.info("billing start....");
		log.info("billing start....");

	}

}

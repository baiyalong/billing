package com.zhcs.billing.quartz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;

/**
 * 这个类是用来包装一下JOB接口的，用于记录日志
 * 
 * @author yuqingchao
 *
 */
public abstract class Task implements ITask, Job {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(Task.class);
	private static Logger log = LoggerFactory.getLogger(Task.class);

	// public Task() {
	//
	// }

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		log.info("任务日志系统运行中...");
		logUtil.info("任务日志系统运行中...");

		String num = "-1";
		BillingBaseDao bd = new BillingBaseDao();

		try {
			HashMap map = new HashMap();
			JobDataMap qzMap = context.getJobDetail().getJobDataMap();
			Iterator i = qzMap.keySet().iterator();
			while (i.hasNext()) {
				Object key = i.next();
				map.put(key, qzMap.get(key));
			}
			// 任务执行前，先记录个日志。
			// String inSql =
			// "INSERT INTO T_TASK_LOG(TASK_ID,REMARKS,START_DATE,STATE) VALUES(? , '执行中' , NOW() , 'R' )";
			String inSql = VariableConfigManager.Task_execute_insert;
			List inList = new ArrayList();
			inList.add(map.get("TASK_ID"));
			num = bd.doSaveOrUpdateID(inSql, inList);

			execute(map); // 任务执行
			// 执行完成更新下任务日志
			// String upSql =
			// "UPDATE T_TASK_LOG SET STATE='O',REMARKS='执行完成',FINISH_DATE=NOW() WHERE TASK_LOG_ID=?";
			String upSql = VariableConfigManager.Task_execute_update;
			List upList = new ArrayList();
			upList.add(num);
			bd.doSaveOrUpdate(upSql, upList);
		} catch (Exception e) {
			log.error("Task Class Logging Error !!", e);
			logUtil.error("Task Class Logging Error !!", e);
			e.printStackTrace();
			try {
				// 异常了，更新下任务日志，说明是异常的
				// String sql =
				// "UPDATE T_TASK_LOG SET STATE='E',FINISH_DATE=NOW(),REMARKS=? WHERE TASK_LOG_ID=? ";
				String sql = VariableConfigManager.Task_execute_update_error;
				List list = new ArrayList();
				list.add(e.getMessage());
				list.add(num);
				bd.doSaveOrUpdate(sql, list);
			} catch (Exception e1) {
				log.error("Task Class Update Log Error !!", e);
				logUtil.error("Task Class Update Log Error !!", e);
				e1.printStackTrace();
			}
		}
	}

}

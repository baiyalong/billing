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
 * �������������װһ��JOB�ӿڵģ����ڼ�¼��־
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

		log.info("������־ϵͳ������...");
		logUtil.info("������־ϵͳ������...");

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
			// ����ִ��ǰ���ȼ�¼����־��
			// String inSql =
			// "INSERT INTO T_TASK_LOG(TASK_ID,REMARKS,START_DATE,STATE) VALUES(? , 'ִ����' , NOW() , 'R' )";
			String inSql = VariableConfigManager.Task_execute_insert;
			List inList = new ArrayList();
			inList.add(map.get("TASK_ID"));
			num = bd.doSaveOrUpdateID(inSql, inList);

			execute(map); // ����ִ��
			// ִ����ɸ�����������־
			// String upSql =
			// "UPDATE T_TASK_LOG SET STATE='O',REMARKS='ִ�����',FINISH_DATE=NOW() WHERE TASK_LOG_ID=?";
			String upSql = VariableConfigManager.Task_execute_update;
			List upList = new ArrayList();
			upList.add(num);
			bd.doSaveOrUpdate(upSql, upList);
		} catch (Exception e) {
			log.error("Task Class Logging Error !!", e);
			logUtil.error("Task Class Logging Error !!", e);
			e.printStackTrace();
			try {
				// �쳣�ˣ�������������־��˵�����쳣��
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

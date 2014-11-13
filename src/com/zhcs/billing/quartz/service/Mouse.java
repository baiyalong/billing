package com.zhcs.billing.quartz.service;

import java.io.ByteArrayInputStream;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.dao.QuartzDao;
import com.zhcs.billing.use.bean.JobTaskBean;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;


/**
 * 系统如果需要启动的Job引擎， 只需要调用这个类的run接口就行。
 * @author yuqingchao
 */
public class Mouse {
	
	private static Logger log = LoggerFactory.getLogger(Mouse.class);
	private static LoggerUtil logUtil = LoggerUtil.getLogger(Mouse.class);
	public static void run(int taskid){
		try{
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			JobDetail job = null;
			JobDataMap map = null;
			CronTrigger trigger = null;
			/**
			 * 根据状态把所有需要处理的启动的任务读出来。。
			 */
			JobTaskBean[] bean = QuartzDao.getJobTaskBean(VariableConfigManager.run_select + taskid);
			if (bean == null){
				return;
			}
			for (int i = 0; i < bean.length; i++) {
				logUtil.info(bean[i].getTaskId());
				log.info(bean[i].getTaskImplClass());
				map = new JobDataMap();
				map.put("TASK_ID",bean[i].getTaskId());// 这里把任务ID放到map里面，用于记录执行日志的时候使用。
				/**************** 把T_JOB_TASK表的配置的变量传入JOB引擎 ***********************/
				if (bean[i].getParms() != null && !bean[i].getParms().equals("")) {
					SAXReader saxReader = new SAXReader();
					Document document = saxReader.read(new ByteArrayInputStream(("<root>"+ bean[i].getParms() + "</root>").getBytes()));
					List l = document.selectNodes("/root/item");
					Iterator iter = l.iterator();
					while (iter.hasNext()) {
						Element element = (Element) iter.next();
						Iterator iterator = element.elementIterator("key");
						String key = "";
						String value = "";
						while (iterator.hasNext()) {
							key = ((Element) iterator.next()).getTextTrim();
						}
						iterator = element.elementIterator("value");
						while (iterator.hasNext()) {
							value = ((Element) iterator.next()).getTextTrim();
						}
						map.put(key, value);
					}
				}
				/**************** 把T_JOB_TASK表的配置的变量传入JOB引擎 ***********************/
				job = new JobDetail(bean[i].getTaskCode(), bean[i].getTaskType(),Class.forName(bean[i].getTaskImplClass()));
				job.setJobDataMap(map);
				trigger = new CronTrigger(
                                                bean[i].getTaskCode() + "Trigger",
						bean[i].getTaskType() + "Trigger", 
                                                bean[i].getTaskCode(),
						bean[i].getTaskType(), 
                                                bean[i].getTaskExpress());
				sched.addJob(job, true);
				sched.scheduleJob(trigger);
			}
			// 启动。开始
			sched.start();
		}catch (Exception e) {
			log.error("Mouse Class !!", e);
			logUtil.error("Mouse Class !!", e);
			e.printStackTrace();
		}
	}


}

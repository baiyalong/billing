package com.zhcs.billing.quartz.service;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.dao.QuartzDao;
import com.zhcs.billing.use.bean.JobTaskBean;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;

public class JobEngine implements Job {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(JobEngine.class);
	private static Logger log = LoggerFactory.getLogger(JobEngine.class);
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			Scheduler inScheduler = context.getScheduler();
			JobDetail job = null;
			JobDataMap map = null;
			CronTrigger trigger = null;
			/**
			 * 这里根据状态U使用的，以及STATE_DATE日期大于当前日期，判断这个任务是刚更新的，如果你想使得修改的JOB立马生效。
			 * 你需要把T_JOB_TASK的字段的值改得大于当前日期，那样这里就会更新到了。
			 */
			JobTaskBean[] bean = QuartzDao.getJobTaskBean(VariableConfigManager.execute_select);
			if (bean == null){
				return;
			}
			for (int i = 0; i < bean.length; i++) {
				// 这里使得修改的Job更新，为了避免重复更新，需要吧STATE_DATE日期改过来。
				//BaseDao bd = new BaseDao();
				BillingBaseDao bd = new BillingBaseDao();
//				String sql = "UPDATE T_JOB_TASK SET STATE_DATE=now() WHERE TASK_ID=?";
				String sql = VariableConfigManager.execute_update;
				List list = new ArrayList();
				list.add(new Long(bean[i].getTaskId()));
				bd.doSaveOrUpdate(sql,list);
				map = new JobDataMap();
				map.put("TASK_ID", new Long(bean[i].getTaskId()));// 这里把任务ID放到map里面，用于记录执行日志的时候使用。
				if (bean[i].getParms() != null && !bean[i].getParms().equals("")) {
					/************* 把业务系统配置的变量读出来,放到job的上下文里面 *************/
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
				/****************** 把业务系统配置的变量读出来 ******************/
				try {
					/****************** 把老的任务给停止，然后删除 ******************/
					inScheduler.unscheduleJob(bean[i].getTaskCode() + "Trigger",bean[i].getTaskType() + "Trigger");
					inScheduler.deleteJob(bean[i].getTaskCode(),bean[i].getTaskType());
					/****************** 把老的任务给停止，然后删除 ******************/
				} catch (Exception e) {
					// 这里如果是在运行过程中，添加新的任务，而不是修改任务，这里会出错的，你说对不对，新加的任务怎么能删除呢？
					log.error("JobEngine Class During operation Insert Job Error !!",e);
					logUtil.error("JobEngine Class During operation Insert Job Error !!",e);
				}
				/****************** 重新添加任务 ******************/
				job = new JobDetail(bean[i].getTaskCode(),bean[i].getTaskType(), Class.forName(bean[i].getTaskImplClass()));
				job.setJobDataMap(map);
				trigger = new CronTrigger(bean[i].getTaskCode() + "Trigger",
						bean[i].getTaskType() + "Trigger",
						bean[i].getTaskCode(), bean[i].getTaskType(),
						bean[i].getTaskExpress());
				inScheduler.addJob(job, true);
				inScheduler.scheduleJob(trigger);
			}
		} catch (Exception e) {
			log.error("JobEngine Class Update Job Error !!",e);
			logUtil.error("JobEngine Class Update Job Error !!",e);
			e.printStackTrace();
		}
	}
	
	public static void test(int i){
		JobTaskBean[] bean = {};
		bean = QuartzDao.getJobTaskBean(VariableConfigManager.execute_select);
		
		System.out.println(bean+"---->"+i);
		bean=null;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10000000; i++) {
			test(i);
			
		}
	}

}

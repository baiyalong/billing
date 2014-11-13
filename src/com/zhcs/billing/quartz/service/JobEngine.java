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
			 * �������״̬Uʹ�õģ��Լ�STATE_DATE���ڴ��ڵ�ǰ���ڣ��ж���������Ǹո��µģ��������ʹ���޸ĵ�JOB������Ч��
			 * ����Ҫ��T_JOB_TASK���ֶε�ֵ�ĵô��ڵ�ǰ���ڣ���������ͻ���µ��ˡ�
			 */
			JobTaskBean[] bean = QuartzDao.getJobTaskBean(VariableConfigManager.execute_select);
			if (bean == null){
				return;
			}
			for (int i = 0; i < bean.length; i++) {
				// ����ʹ���޸ĵ�Job���£�Ϊ�˱����ظ����£���Ҫ��STATE_DATE���ڸĹ�����
				//BaseDao bd = new BaseDao();
				BillingBaseDao bd = new BillingBaseDao();
//				String sql = "UPDATE T_JOB_TASK SET STATE_DATE=now() WHERE TASK_ID=?";
				String sql = VariableConfigManager.execute_update;
				List list = new ArrayList();
				list.add(new Long(bean[i].getTaskId()));
				bd.doSaveOrUpdate(sql,list);
				map = new JobDataMap();
				map.put("TASK_ID", new Long(bean[i].getTaskId()));// ���������ID�ŵ�map���棬���ڼ�¼ִ����־��ʱ��ʹ�á�
				if (bean[i].getParms() != null && !bean[i].getParms().equals("")) {
					/************* ��ҵ��ϵͳ���õı���������,�ŵ�job������������ *************/
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
				/****************** ��ҵ��ϵͳ���õı��������� ******************/
				try {
					/****************** ���ϵ������ֹͣ��Ȼ��ɾ�� ******************/
					inScheduler.unscheduleJob(bean[i].getTaskCode() + "Trigger",bean[i].getTaskType() + "Trigger");
					inScheduler.deleteJob(bean[i].getTaskCode(),bean[i].getTaskType());
					/****************** ���ϵ������ֹͣ��Ȼ��ɾ�� ******************/
				} catch (Exception e) {
					// ��������������й����У�����µ����񣬶������޸�������������ģ���˵�Բ��ԣ��¼ӵ�������ô��ɾ���أ�
					log.error("JobEngine Class During operation Insert Job Error !!",e);
					logUtil.error("JobEngine Class During operation Insert Job Error !!",e);
				}
				/****************** ����������� ******************/
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

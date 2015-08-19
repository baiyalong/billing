package com.zhcs.billing.quartz.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.JobTaskBean;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 作业调度Dao
 * 
 * @author yuqingchao
 *
 */
public class QuartzDao implements IQuartzDao {

	private static Logger log = LoggerFactory.getLogger(QuartzDao.class);
	private static LoggerUtil loggerUtil = LoggerUtil
			.getLogger(QuartzDao.class);

	/**
	 * 根据sql 查询对应的数据
	 * 
	 * @param sql
	 * @return JobTaskBean[]
	 */
	public static JobTaskBean[] getJobTaskBean(String sql) {
		List<JobTaskBean> jobList = new ArrayList<JobTaskBean>();

		try {
			BillingBaseDao bd = new BillingBaseDao();
			jobList = bd.doExecuteQuery(sql);
			bd = null;
		} catch (Exception e) {
			log.error("QuartzDao Class !!", e);
			loggerUtil.error("QuartzDao Class !!", e);
			e.printStackTrace();
		}
		JobTaskBean[] jobTaskBean = new JobTaskBean[jobList.size()];
		for (int i = 0; i < jobList.size(); i++) {
			jobTaskBean[i] = jobList.get(i);
		}

		return jobList.size() == 0 ? null : jobTaskBean;
	}

}

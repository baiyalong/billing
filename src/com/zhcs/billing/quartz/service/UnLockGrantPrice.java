package com.zhcs.billing.quartz.service;

import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;

public class UnLockGrantPrice implements Job {
	//添加日志管理
	private static LoggerUtil logUtil=LoggerUtil.getLogger(UnLockGrantPrice.class);
	private static Logger log = LoggerFactory.getLogger(UnLockGrantPrice.class);
	//计费basedao数据库连接
//	private BillingBaseDao billing;
//	public BillingBaseDao GetBillingBaseDao() {
//		if (billing == null) {
//			billing = new BillingBaseDao();
//		}
//		return billing;
//	}
	//修改锁定使用量表 ，如果当前时间与创建时间相差4个小时15分钟，就更改状态 改为 已解锁
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BillingBaseDao billing = new BillingBaseDao();
		int i = 0;
		try {
			i = billing.doSaveOrUpdate("UPDATE T_USAGE_BAND_TOTAL SET STATE='0' WHERE STATE NOT IN(0) AND TIMESTAMPDIFF(MINUTE,CREATE_DATE,current_timestamp())>215");
			if(i>0){
				log.info("数据更改状态成功 共 "+i+" 条数据");
				logUtil.info("数据更改状态成功 共 "+i+" 条数据");
			}else{
				log.info("没有需要更改的数据");
				logUtil.info("没有需要更改的数据");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("更改状态失败 ："+e.getMessage());
			logUtil.info("更改状态失败 ："+e.getMessage());
		}
	}

}

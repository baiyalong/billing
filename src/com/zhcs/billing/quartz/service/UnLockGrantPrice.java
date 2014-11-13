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
	//�����־����
	private static LoggerUtil logUtil=LoggerUtil.getLogger(UnLockGrantPrice.class);
	private static Logger log = LoggerFactory.getLogger(UnLockGrantPrice.class);
	//�Ʒ�basedao���ݿ�����
//	private BillingBaseDao billing;
//	public BillingBaseDao GetBillingBaseDao() {
//		if (billing == null) {
//			billing = new BillingBaseDao();
//		}
//		return billing;
//	}
	//�޸�����ʹ������ �������ǰʱ���봴��ʱ�����4��Сʱ15���ӣ��͸���״̬ ��Ϊ �ѽ���
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BillingBaseDao billing = new BillingBaseDao();
		int i = 0;
		try {
			i = billing.doSaveOrUpdate("UPDATE T_USAGE_BAND_TOTAL SET STATE='0' WHERE STATE NOT IN(0) AND TIMESTAMPDIFF(MINUTE,CREATE_DATE,current_timestamp())>215");
			if(i>0){
				log.info("���ݸ���״̬�ɹ� �� "+i+" ������");
				logUtil.info("���ݸ���״̬�ɹ� �� "+i+" ������");
			}else{
				log.info("û����Ҫ���ĵ�����");
				logUtil.info("û����Ҫ���ĵ�����");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.info("����״̬ʧ�� ��"+e.getMessage());
			logUtil.info("����״̬ʧ�� ��"+e.getMessage());
		}
	}

}

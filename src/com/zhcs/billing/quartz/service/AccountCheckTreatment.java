package com.zhcs.billing.quartz.service;

import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.AccountCheckAllBean;
import com.zhcs.billing.use.bean.AccountCheckBean;
import com.zhcs.billing.use.bean.AccountInfoBean;
import com.zhcs.billing.use.dao.BillingInsert;
import com.zhcs.billing.use.dao.BillingQuery;
import com.zhcs.billing.util.LoggerUtil;

public class AccountCheckTreatment extends Task implements Job {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(AccountCheckTreatment.class);
	private static Logger log = LoggerFactory
			.getLogger(AccountCheckTreatment.class);

	@Override
	public void execute(HashMap map) {
		// TODO Auto-generated method stub
		// ÿ�춨ʱ����ǰһ�������˻�����֧��ϸ
		// -- ɨ�������˻�
		List<AccountInfoBean> beans = BillingQuery.AccountInfo();
		// -- �����˻�����ѯ��ǰһ����֧���������¼
		for (AccountInfoBean bean : beans) {
			// -- �鿴���˻�ǰһ����û����֧��¼
			if (BillingQuery.AccountTransactionCount(bean) > 0) {
				// -- ����
				AccountCheckBean b = new AccountCheckBean();
				b.setACCOUNT_ID(bean.getACCOUNT_ID());
				b.setCUSTOMER_ID(bean.getCUSTOMER_ID());
				// -- ��ѯ��֧��ϸ
				b = BillingQuery.AccountCheck(b);
				// ����
				if (b.getBALANCE_Y() + b.getINCOME() - b.getOUTCOME() == b
						.getBALANCE()) {
					b.setRESULT(true);
				} else {
					b.setRESULT(false);
				}

				// -- ��¼
				try {
					BillingInsert.AccountCheck(b);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// -- recon �������˵�
				try {

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// ÿ�������ɺ󣬸������˼�¼
		// ��ѯ
		AccountCheckAllBean bean = BillingQuery.AccountCheckAll();
		// ����
		if (bean.getBALANCE_Y() + bean.getINCOME() - bean.getOUTCOME() == bean
				.getBALANCE()) {
			bean.setRESULT(true);
		} else {
			bean.setRESULT(false);
		}
		// ��¼
		try {
			BillingInsert.AccountCheckAll(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

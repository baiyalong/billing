package com.zhcs.billing.quartz.service;

import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.AccountCheckAllBean;
import com.zhcs.billing.use.bean.AccountCheckBean;
import com.zhcs.billing.use.dao.BillingInsert;
import com.zhcs.billing.use.dao.BillingQuery;
import com.zhcs.billing.util.LoggerUtil;

public class AccountCheckAllTreatment extends Task implements Job {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(AccountCheckAllTreatment.class);
	private static Logger log = LoggerFactory
			.getLogger(AccountCheckAllTreatment.class);

	@Override
	public void execute(HashMap map) {
		// TODO Auto-generated method stub
		// ≤È—Ø
		AccountCheckAllBean bean = BillingQuery.AccountCheckAll();
		// ∫À’À
		if (bean.getBALANCE_Y() + bean.getINCOME() - bean.getOUTCOME() == bean
				.getBALANCE()) {
			bean.setRESULT(true);
		} else {
			bean.setRESULT(false);
		}
		// º«¬º
		try {
			BillingInsert.AccountCheckAll(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

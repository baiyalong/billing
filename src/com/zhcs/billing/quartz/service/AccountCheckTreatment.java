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
		// 每天定时核算前一天所有账户的收支明细
		// -- 扫描所有账户
		List<AccountInfoBean> beans = BillingQuery.AccountInfo();
		// -- 遍历账户，查询其前一天收支情况，并记录
		for (AccountInfoBean bean : beans) {
			// -- 查看该账户前一天有没有收支记录
			if (BillingQuery.AccountTransactionCount(bean) > 0) {
				// -- 核账
				AccountCheckBean b = new AccountCheckBean();
				b.setACCOUNT_ID(bean.getACCOUNT_ID());
				b.setCUSTOMER_ID(bean.getCUSTOMER_ID());
				// -- 查询收支明细，核账
				BillingQuery.AccountCheck(b);
				// -- 记录
				BillingInsert.AccountCheck(b);
			}
		}
		// 每天核账完成后，更新总账记录
		AccountCheckAllBean bean = BillingQuery.AccountCheckAll();
		try {
			BillingInsert.AccountCheckAll(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.zhcs.billing.quartz.service;

import java.util.ArrayList;
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
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.CalendarUtil;

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
				// -- 查询收支明细
				b = BillingQuery.AccountCheck(b);
				// 核账
				if (b.getBALANCE_Y() + b.getINCOME() - b.getOUTCOME() == b
						.getBALANCE()) {
					b.setRESULT(true);
				} else {
					b.setRESULT(false);
				}

				// -- 记录
				try {
					BillingInsert.AccountCheck(b);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// -- recon 更新月账单
				try {

					if (// this.isMonthOpen() &&
					!this.existRecon(b.getACCOUNT_ID())) {
						// 月初 插入新账单
						this.newRecon(b);
					}

					// 更新账单
					this.updateRecon(b);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// 每天核账完成后，更新总账记录
		// 查询
		AccountCheckAllBean bean = BillingQuery.AccountCheckAll();
		// 核账
		if (bean.getBALANCE_Y() + bean.getINCOME() - bean.getOUTCOME() == bean
				.getBALANCE()) {
			bean.setRESULT(true);
		} else {
			bean.setRESULT(false);
		}
		// 记录
		try {
			BillingInsert.AccountCheckAll(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateRecon(AccountCheckBean b) { // 更新月账单
		// TODO Auto-generated method stub
		// update ACCOUNT_BOOK set BALANCE = BALANCE - ?,CREDIT_AMOUNT =
		// CREDIT_AMOUNT + ? where BOOK_ID = ?;
		String sql = "update ACCOUNT_RECON set"
				+ "DEBIT_AMOUNT = DEBIT_AMOUNT + ?,CREDIT_AMOUNT = CREDIT_AMOUNT + ?,CLOSING_BALANCE = ?"
				+ "where ACCOUNT_ID = ? and BILL_MONTH = ?;";
		List params = new ArrayList();
		params.add(b.getINCOME());
		params.add(b.getOUTCOME());
		params.add(b.getBALANCE());

		params.add(b.getACCOUNT_ID());
		params.add(this.isMonthOpen() ? CalendarUtil.lastMonth()
				.replace("-", "").substring(0, 6) : CalendarUtil.thisMonth()
				.replace("-", "").substring(0, 6)); // 月份

		int rs;
		try {
			rs = new BaseDao().doSaveOrUpdate(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void newRecon(AccountCheckBean b) { // 插入新账单
		// TODO Auto-generated method stub
		String sql = "insert into ACCOUNT_RECON "
				+ "(BILL_ID,ACCOUNT_ID,CUSTOMER_ID,BILL_MONTH,OPENNING_BALANCE,DEBIT_AMOUNT,CREDIT_AMOUNT,CLOSING_BALANCE,CREATE_TIME) "
				+ "values " + "(?,?,?,?,?,?,?,?,now());";
		List params = new ArrayList();
		params.add(java.util.UUID.randomUUID().toString());
		params.add(b.getACCOUNT_ID());
		params.add(b.getCUSTOMER_ID());
		params.add(CalendarUtil.thisMonth().replace("-", "").substring(0, 6));
		params.add(b.getBALANCE());
		params.add(0);
		params.add(0);
		params.add(b.getBALANCE());

		int rs;
		try {
			rs = new BaseDao().doSaveOrUpdate(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean existRecon(String accountID) { // 本月账单已存在
		// TODO Auto-generated method stub
		int res = 0;
		String sql = "select count(*) from ACCOUNT_RECON where ACCOUNT_ID = ? and BILL_MONTH = ?;";
		List params = new ArrayList();
		params.add(accountID);
		params.add(CalendarUtil.thisMonth().replace("-", "").substring(0, 6)); // 月份

		List<HashMap<String, Object>> li = new BaseDao().doSelect(sql, params);
		if (li != null && !li.isEmpty()) {
			res = new Integer(String.valueOf(li.get(0).get("count(*)")));
		}

		return res > 0;
	}

	private boolean isMonthOpen() { // 今天是月初
		// TODO Auto-generated method stub
		String today = CalendarUtil.today();
		String thisMonth = CalendarUtil.thisMonth();

		return today.equals(thisMonth);

	}

}

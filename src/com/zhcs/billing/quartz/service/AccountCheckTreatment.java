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

					if (// this.isMonthOpen() &&
					!this.existRecon(b.getACCOUNT_ID())) {
						// �³� �������˵�
						this.newRecon(b);
					}

					// �����˵�
					this.updateRecon(b);

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

	private void updateRecon(AccountCheckBean b) { // �������˵�
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
				.replace("-", "").substring(0, 6)); // �·�

		int rs;
		try {
			rs = new BaseDao().doSaveOrUpdate(sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void newRecon(AccountCheckBean b) { // �������˵�
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

	private boolean existRecon(String accountID) { // �����˵��Ѵ���
		// TODO Auto-generated method stub
		int res = 0;
		String sql = "select count(*) from ACCOUNT_RECON where ACCOUNT_ID = ? and BILL_MONTH = ?;";
		List params = new ArrayList();
		params.add(accountID);
		params.add(CalendarUtil.thisMonth().replace("-", "").substring(0, 6)); // �·�

		List<HashMap<String, Object>> li = new BaseDao().doSelect(sql, params);
		if (li != null && !li.isEmpty()) {
			res = new Integer(String.valueOf(li.get(0).get("count(*)")));
		}

		return res > 0;
	}

	private boolean isMonthOpen() { // �������³�
		// TODO Auto-generated method stub
		String today = CalendarUtil.today();
		String thisMonth = CalendarUtil.thisMonth();

		return today.equals(thisMonth);

	}

}

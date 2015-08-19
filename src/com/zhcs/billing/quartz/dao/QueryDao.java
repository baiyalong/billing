package com.zhcs.billing.quartz.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.service.MonthsThread;
import com.zhcs.billing.use.bean.AccountPayBean;
import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.MeteringBaseDao;
import com.zhcs.billing.util.VariableConfigManager;

public class QueryDao implements IQueryDao {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(QueryDao.class);
	private static Logger log = LoggerFactory.getLogger(QueryDao.class);
	/*
	 * static MeteringBaseDao meteringBaseDao; // ��ѯ������ static BaseDao baseDao;
	 * // ��ѯҵ��� static BillingBaseDao billingBaseDao;
	 */// ��ѯҵ���

	private MeteringBaseDao meteringBaseDao;
	private BaseDao baseDao;
	private BillingBaseDao billingBaseDao;

	public MeteringBaseDao GetMeteringBaseDao() {
		if (meteringBaseDao == null) {
			meteringBaseDao = new MeteringBaseDao();
		}
		return meteringBaseDao;
	}

	public BaseDao GetBaseDao() {
		if (baseDao == null) {
			baseDao = new BaseDao();
		}
		return baseDao;
	}

	public BillingBaseDao GetBillingBaseDao() {
		if (billingBaseDao == null) {
			billingBaseDao = new BillingBaseDao();
		}
		return billingBaseDao;
	}

	/**
	 * ��ѯ�������ݿ��ж���������Ҫ����
	 * 
	 * @param estOrderBean
	 * @return
	 */
	public List<HashMap<String, Object>> getEstOrderNumOne(
			EstOrderBean estOrderBean) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = VariableConfigManager.getOrderNumSql;
		List params = new ArrayList();
		params.add(estOrderBean.getColltime());
		params.add(estOrderBean.getSperiod());
		list = GetMeteringBaseDao().doSelectOrders(sql, params);
		params = null;
		// GetMeteringBaseDao().closed();
		return list;
	}

	/**
	 * ��ѯ�������ݿ��ж���������Ҫ����
	 * 
	 * @param estOrderBean
	 * @return
	 */
	public List<HashMap<String, Object>> getEstOrderNumFive(
			EstOrderBean estOrderBean) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = VariableConfigManager.getOrderNumSql;
		List params = new ArrayList();
		params.add(estOrderBean.getTimeTip());
		params.add(estOrderBean.getSperiod());
		list = GetMeteringBaseDao().doSelectOrders(sql, params);
		// GetMeteringBaseDao().closed();
		params = null;
		return list;
	}

	/**
	 * ��ѯҵ�����ƽ̨������Ϣ�����ж���������Ҫ����
	 * 
	 * @param usageMonthsBean
	 * @return
	 */
	public List<HashMap<String, Object>> getMonthsOrderNum(
			OrderInfoBean orderInfoBean) {
		String sql = VariableConfigManager.getOrderNumSql_Months;
		List params = new ArrayList();
		params.add(orderInfoBean.getPRODUCT_CATEGORY());
		params.add(orderInfoBean.getORDER_STATUS());
		List<HashMap<String, Object>> list = GetBaseDao().doSelectOrder(sql,
				params);
		// GetBaseDao().closed();
		params = null;
		return list;
	}

	/**
	 * ���ݶ�����Ų�ѯ�ͻ����
	 * 
	 * @param orderid
	 * @return
	 */
	public String getCustomerIdByOrderId(String orderid) {
		String sql = VariableConfigManager.getCustomerId;
		List params = new ArrayList();
		params.add(orderid);
		List<HashMap<String, Object>> list = GetBaseDao().doSelectOrder(sql,
				params);
		String customerid = "";
		if (list != null && list.size() > 0) {
			customerid = list.get(0).get("ACCOUNT_CODE") != null ? list.get(0)
					.get("ACCOUNT_CODE").toString() : null;
		} else {
			customerid = null;
		}
		params = null;
		list = null;
		return customerid;
	}

	public void insertAccountPay(AccountPayBean accountPayBean) {
		String sql = VariableConfigManager.insertAccountPaySql;
		List params = new ArrayList();
		params.add(Common.createAccountID(accountPayBean.getORDER_ID()));
		params.add(accountPayBean.getTIME_STAMP());
		params.add(accountPayBean.getBILLING_ID());
		params.add(accountPayBean.getCUSTOMER_ID());
		params.add(accountPayBean.getORDER_ID());
		params.add(accountPayBean.getAMOUNT());
		params.add(accountPayBean.getCDR_TYPE());
		params.add(accountPayBean.isRESULT());
		params.add(accountPayBean.getDESCRIPTION());
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if (num != 0) {
				log.info("������ţ�" + accountPayBean.getORDER_ID()
						+ " ����T_ACCOUNTPAY�����ݳɹ���");
				logUtil.info("������ţ�" + accountPayBean.getORDER_ID()
						+ " ����T_ACCOUNTPAY�����ݳɹ���");
			}
		} catch (Exception e) {
			log.error("QueryDao Class insertAccountPay Method" + e);
			logUtil.error("QueryDao Class insertAccountPay Method" + e);
			e.printStackTrace();
		}
		if (num == 0) {
			log.info("������ţ�" + accountPayBean.getORDER_ID()
					+ " ����T_ACCOUNTPAY������ʧ�ܣ�");
			logUtil.info("������ţ�" + accountPayBean.getORDER_ID()
					+ " ����T_ACCOUNTPAY������ʧ�ܣ�");
			return;
		}
		params = null;
	}

}

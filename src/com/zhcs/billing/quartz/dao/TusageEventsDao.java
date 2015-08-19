package com.zhcs.billing.quartz.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.TUsageEventsBean;
import com.zhcs.billing.use.bean.UsageMonthlyBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.VariableConfigManager;

public class TusageEventsDao implements ITusageEventsDao {

	private static Logger log = LoggerFactory.getLogger(TusageEventsDao.class);
	private BaseDao baseDao;
	private BillingBaseDao billing;

	public BaseDao GetBaseDao() {
		if (baseDao == null) {
			baseDao = new BaseDao();
		}
		return baseDao;
	}

	public BillingBaseDao GetBillingBaseDao() {
		if (billing == null) {
			billing = new BillingBaseDao();
		}
		return billing;
	}

	public int insertTUsageEvents(TUsageEventsBean tUsageEventsBean,
			String dateStr, String billingID) {
		int insertId = 0;
		String sqlI = "INSERT INTO T_USAGE_EVENTS_"
				+ dateStr
				+ " "
				+ "(REQ_TIME,CUSTOMER_ID,PRUDUCT_ID,REALOCKSTAT,BILLING_ID,UNIT_RATE,FEE_TYPE) "
				+ "values (?,?,?,?,?,?,?)";
		List paramsI = new ArrayList();
		paramsI.add(tUsageEventsBean.getREQ_TIME());
		paramsI.add(tUsageEventsBean.getCUSTOMER_ID());
		paramsI.add(tUsageEventsBean.getPRUDUCT_ID());
		paramsI.add(tUsageEventsBean.getREALOCKSTAT());
		paramsI.add(billingID);
		paramsI.add(tUsageEventsBean.getACCOUNT());
		paramsI.add(tUsageEventsBean.isFEE_TYPE());
		try {
			insertId = GetBillingBaseDao().doSaveOrUpdate(sqlI, paramsI);
			log.info("详单表T_Usage_Events_" + dateStr + "成功插入: " + insertId
					+ " 条数据!");
		} catch (Exception e) {
			log.info("详单表T_Usage_Events_" + dateStr + "插入数据失败 "
					+ e.getMessage());
			e.printStackTrace();
		}
		paramsI = null;
		return insertId;
	}

	public List<UsageMonthlyBean> listRate(UsageMonthlyBean usageMonthlyBean) {
		String sql = "SELECT * FROM T WHERE FIXED_OR_MONTHLY = ?";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getFIXED_OR_MONTHLY());// 0:按天扣,1:月初扣
		List<HashMap<String, Object>> l = GetBillingBaseDao().doSelect(sql,
				params);
		List<UsageMonthlyBean> list = chageToObject(l);
		log.info("查询需要处理的月租数据T_Usage_Monthly：" + list.size());
		params = null;
		return list;
	}

	/**
	 * 根据订单编号 查询资费月租表(T_USAGE_MONTHLY)
	 * 
	 * @param orderId
	 * @return
	 */
	public List<HashMap<String, Object>> getUsageMonthly(String orderId,
			String billCycleId) {
		// String sql = "SELECT * FROM T_USAGE_MONTHLY WHERE ORDER_ID=?";
		String sql = VariableConfigManager.getUsageMonthly;
		List<String> params = new ArrayList<String>();
		params.add(orderId);
		params.add(billCycleId);
		return GetBillingBaseDao().doSelect(sql, params);
	}

	/**
	 * 将查询返回的结果转换成UsageMonthlyBean列表对象
	 * 
	 * @param l
	 * @return
	 */
	private List<UsageMonthlyBean> chageToObject(List<HashMap<String, Object>> l) {
		List<UsageMonthlyBean> list = new ArrayList<UsageMonthlyBean>();
		for (HashMap<String, Object> d : l) {
			UsageMonthlyBean t = new UsageMonthlyBean();
			t.setUNIT_RATE(d.get("UNIT_RATE") != null ? d.get("UNIT_RATE")
					.toString() : null);
			t.setCUSTOMER_ID(d.get("CUSTOMER_ID") != null ? d
					.get("CUSTOMER_ID").toString() : null);
			t.setCOMPLETE_STATE(d.get("COMPLETE_STATE") != null ? d.get(
					"COMPLETE_STATE").toString() : null);
			list.add(t);
		}
		return list;
	}

}

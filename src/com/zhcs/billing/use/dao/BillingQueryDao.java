package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 计费订单查询使用Dao
 * 
 * @author hefa
 * 
 */
public class BillingQueryDao {
	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(BillingQueryDao.class);
	private static Logger log = LoggerFactory.getLogger(BillingQueryDao.class);
	static BillingBaseDao billingBaseDao;

	public BillingBaseDao GetBillingBaseDao() {
		if (billingBaseDao == null) {
			billingBaseDao = new BillingBaseDao();
		}
		return billingBaseDao;
	}

	// 根据订单号查询产品编号（有效申购产品）
	public List<HashMap<String, Object>> getProductNumber(String ORDER_ID) {
		BaseDao basedao = new BaseDao();
		List params = new ArrayList();
		params.add(ORDER_ID);
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getProductNumber, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * 根据产品编号查找有效资源
	 * 
	 * @param infoBeans
	 * @return
	 */
	public List<HashMap<String, Object>> getProductResource(String PRODUCT_ID) {
		BaseDao basedao = new BaseDao();
		// 资源编号,产品编号,资源名称,NODE_TYPE,父资源编号
		List params = new ArrayList();
		params.add(PRODUCT_ID);
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getProductResource, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * 根据产品找纬度
	 * 
	 * @param resourceBeans
	 * @return
	 */
	public List<HashMap<String, Object>> getProductItem(String PRODUCT_ID) {
		BaseDao basedao = new BaseDao();
		// 维度编号，产品编号，资源编号，维度编码，维度名称，单价
		List params = new ArrayList();
		params.add(PRODUCT_ID);// 产品编号
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getProductItem, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * 根据订单、扫描方式、时间获取该产品下所有纬度的累积量
	 * 
	 * @param orderInfoBean
	 * @param timeInterval
	 * @param sCANNING_WAY
	 * @return
	 */
	public List<HashMap<String, Object>> getCumulants(
			OrderInfoBean orderInfoBean, String timeInterval,
			String sCANNING_WAY) {
		BillingBaseDao billingBaseDao = new BillingBaseDao();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// 订单号，维度名称，维度编码，使用量，累积量
		StringBuffer sql = new StringBuffer(
				"SELECT ORDER_ID,PRODUCT_ID,RESOURCE_ID,WD_NAME,WD_NO,WD_ADD_TOTAL,CURRENT_ADD_TOTAL,SCANNING_WAY FROM T_SCANNING_ADD_TOTAL T WHERE T.SCANNING_WAY = ?  AND T.ORDER_ID = ?");
		try {
			List params = new ArrayList();
			params.add(sCANNING_WAY);// 扫描方式
			params.add(orderInfoBean.getORDER_ID());// 订单号
			if ("5".equals(sCANNING_WAY)) {
				sql.append(" AND T.START_TIME < ? AND T.END_TIME >= ? ");
				params.add(timeInterval);// 时间
				params.add(timeInterval);// 时间
			} else if ("1".equals(sCANNING_WAY)) {
				sql.append(" AND date_format(T.START_TIME,'%Y-%m-%d') = date_format(?,'%Y-%m-%d') AND date_format(T.END_TIME,'%Y-%m-%d') = date_format(?,'%Y-%m-%d')");// date_format(now(),'%Y-%m-%d')
				params.add(timeInterval);// 时间
				params.add(timeInterval);// 时间
			}
			list = billingBaseDao.doSelect(sql.toString(), params);
			params = null;
		} catch (Exception e) {
			log.error("根据订单、扫描方式、时间获取该产品下所有纬度的累积量dao出现异常：" + e.getMessage()
					+ "SQL语句:" + sql);
			logUtil.error("根据订单、扫描方式、时间获取该产品下所有纬度的累积量dao出现异常：" + e.getMessage()
					+ "SQL语句:" + sql);
		}
		sql = null;
		billingBaseDao = null;
		// log.info("根据订单、扫描方式、时间获取该产品下所有纬度的累积量dao：" + list.size() + "条");
		// logUtil.info("根据订单、扫描方式、时间获取该产品下所有纬度的累积量dao：" + list.size() + "条");
		return list;
	}

	/**
	 * 根据订单查询本月上次扣费情况
	 * 
	 * @param order_ID
	 * @param timeInterval
	 * @param sCANNING_WAY
	 * @return
	 */
	public List<HashMap<String, Object>> getCulOrderDetailBeans(
			String order_ID, String time, String sCANNING_WAY) {
		BillingBaseDao billingBaseDao = new BillingBaseDao();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// 订单编号，扫描方式，扫描时间
		List params = new ArrayList();
		params.add(order_ID);// 订单
		params.add(sCANNING_WAY);// 扫描方式
		params.add(time);// 扫描时间
		list = billingBaseDao.doSelect(SqlString.getCulOrderDetail, params);
		params = null;
		billingBaseDao = null;
		return list;
	}

	/**
	 * 根据产品编号、申购编号查询申购明细
	 * 
	 * @param sUBSCRIBER_ID
	 *            申购编号
	 * @param pRODUCT_ID
	 *            产品编号
	 * @param time
	 *            计费时间
	 * @return
	 */
	public List<HashMap<String, Object>> getSubscriptionItems(
			String sUBSCRIBER_ID, String pRODUCT_ID) {
		BaseDao basedao = new BaseDao();
		// 产品、产品资源、产品维度、包内剩余量、申购明细编号
		List params = new ArrayList();
		params.add(sUBSCRIBER_ID);// 申购编号
		params.add(pRODUCT_ID);// 产品编号
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getSubscriptionItem, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * 获取初装费
	 * 
	 * @param ORDER_ID
	 * @param time
	 * @return
	 */
	public String getCZF(String ORDER_ID, String time) {
		BaseDao basedao = new BaseDao();
		List params = new ArrayList();
		params.add(ORDER_ID);
		params.add(time);
		List<HashMap<String, Object>> list = basedao.doSelect(SqlString.getCZF,
				params);
		params = null;
		basedao = null;
		String sum = "0";
		if (list != null && list.size() > 0) {
			sum = list.get(0).get("SUM") != null ? list.get(0).get("SUM")
					.toString() : "0";
		}
		list = null;
		return sum;
	}

	/**
	 * 获取月租费
	 * 
	 * @param ORDER_ID
	 * @param time
	 * @return
	 */
	public String getYZF(String ORDER_ID, String time) {
		BaseDao basedao = new BaseDao();
		List params = new ArrayList();
		params.add(ORDER_ID);
		params.add(time);
		List<HashMap<String, Object>> list = basedao.doSelect(SqlString.getYZF,
				params);
		params = null;
		basedao = null;
		String sum = "0";
		if (list != null && list.size() > 0) {
			sum = list.get(0).get("SUM") != null ? list.get(0).get("SUM")
					.toString() : "0";
		}
		list = null;
		return sum;
	}

	/**
	 * 根据订单获取产品资费
	 * 
	 * @param ORDER_ID
	 * @param time
	 * @return
	 */
	public String getTCZF(String ORDER_ID, String time) {
		BaseDao basedao = new BaseDao();
		List params = new ArrayList();
		params.add(ORDER_ID);
		params.add(time);
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getTCZF, params);
		params = null;
		basedao = null;
		String sum = "0";
		if (list != null && list.size() > 0) {
			sum = list.get(0).get("SUM") != null ? list.get(0).get("SUM")
					.toString() : "0";
		}
		list = null;
		return sum;
	}

	/**
	 * 获取月租费
	 * 
	 * @param ORDER_ID
	 * @param time
	 * @return
	 */
	public List<HashMap<String, Object>> getYZFR(String ORDER_ID, String time) {
		BaseDao basedao = new BaseDao();
		List params = new ArrayList();
		params.add(ORDER_ID);
		params.add(time);
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getYZFR, params);
		params = null;
		basedao = null;
		/*
		 * String sum = "0"; if (list!=null&&list.size()>0) { sum =
		 * list.get(0).get("SUM") != null ? list.get(0).get("SUM").toString() :
		 * "0"; }
		 */
		// list = null;
		return list;
	}
}

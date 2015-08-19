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
 * �ƷѶ�����ѯʹ��Dao
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

	// ���ݶ����Ų�ѯ��Ʒ��ţ���Ч�깺��Ʒ��
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
	 * ���ݲ�Ʒ��Ų�����Ч��Դ
	 * 
	 * @param infoBeans
	 * @return
	 */
	public List<HashMap<String, Object>> getProductResource(String PRODUCT_ID) {
		BaseDao basedao = new BaseDao();
		// ��Դ���,��Ʒ���,��Դ����,NODE_TYPE,����Դ���
		List params = new ArrayList();
		params.add(PRODUCT_ID);
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getProductResource, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * ���ݲ�Ʒ��γ��
	 * 
	 * @param resourceBeans
	 * @return
	 */
	public List<HashMap<String, Object>> getProductItem(String PRODUCT_ID) {
		BaseDao basedao = new BaseDao();
		// ά�ȱ�ţ���Ʒ��ţ���Դ��ţ�ά�ȱ��룬ά�����ƣ�����
		List params = new ArrayList();
		params.add(PRODUCT_ID);// ��Ʒ���
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getProductItem, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * ���ݶ�����ɨ�跽ʽ��ʱ���ȡ�ò�Ʒ������γ�ȵ��ۻ���
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
		// �����ţ�ά�����ƣ�ά�ȱ��룬ʹ�������ۻ���
		StringBuffer sql = new StringBuffer(
				"SELECT ORDER_ID,PRODUCT_ID,RESOURCE_ID,WD_NAME,WD_NO,WD_ADD_TOTAL,CURRENT_ADD_TOTAL,SCANNING_WAY FROM T_SCANNING_ADD_TOTAL T WHERE T.SCANNING_WAY = ?  AND T.ORDER_ID = ?");
		try {
			List params = new ArrayList();
			params.add(sCANNING_WAY);// ɨ�跽ʽ
			params.add(orderInfoBean.getORDER_ID());// ������
			if ("5".equals(sCANNING_WAY)) {
				sql.append(" AND T.START_TIME < ? AND T.END_TIME >= ? ");
				params.add(timeInterval);// ʱ��
				params.add(timeInterval);// ʱ��
			} else if ("1".equals(sCANNING_WAY)) {
				sql.append(" AND date_format(T.START_TIME,'%Y-%m-%d') = date_format(?,'%Y-%m-%d') AND date_format(T.END_TIME,'%Y-%m-%d') = date_format(?,'%Y-%m-%d')");// date_format(now(),'%Y-%m-%d')
				params.add(timeInterval);// ʱ��
				params.add(timeInterval);// ʱ��
			}
			list = billingBaseDao.doSelect(sql.toString(), params);
			params = null;
		} catch (Exception e) {
			log.error("���ݶ�����ɨ�跽ʽ��ʱ���ȡ�ò�Ʒ������γ�ȵ��ۻ���dao�����쳣��" + e.getMessage()
					+ "SQL���:" + sql);
			logUtil.error("���ݶ�����ɨ�跽ʽ��ʱ���ȡ�ò�Ʒ������γ�ȵ��ۻ���dao�����쳣��" + e.getMessage()
					+ "SQL���:" + sql);
		}
		sql = null;
		billingBaseDao = null;
		// log.info("���ݶ�����ɨ�跽ʽ��ʱ���ȡ�ò�Ʒ������γ�ȵ��ۻ���dao��" + list.size() + "��");
		// logUtil.info("���ݶ�����ɨ�跽ʽ��ʱ���ȡ�ò�Ʒ������γ�ȵ��ۻ���dao��" + list.size() + "��");
		return list;
	}

	/**
	 * ���ݶ�����ѯ�����ϴο۷����
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
		// ������ţ�ɨ�跽ʽ��ɨ��ʱ��
		List params = new ArrayList();
		params.add(order_ID);// ����
		params.add(sCANNING_WAY);// ɨ�跽ʽ
		params.add(time);// ɨ��ʱ��
		list = billingBaseDao.doSelect(SqlString.getCulOrderDetail, params);
		params = null;
		billingBaseDao = null;
		return list;
	}

	/**
	 * ���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸ
	 * 
	 * @param sUBSCRIBER_ID
	 *            �깺���
	 * @param pRODUCT_ID
	 *            ��Ʒ���
	 * @param time
	 *            �Ʒ�ʱ��
	 * @return
	 */
	public List<HashMap<String, Object>> getSubscriptionItems(
			String sUBSCRIBER_ID, String pRODUCT_ID) {
		BaseDao basedao = new BaseDao();
		// ��Ʒ����Ʒ��Դ����Ʒά�ȡ�����ʣ�������깺��ϸ���
		List params = new ArrayList();
		params.add(sUBSCRIBER_ID);// �깺���
		params.add(pRODUCT_ID);// ��Ʒ���
		List<HashMap<String, Object>> list = basedao.doSelect(
				SqlString.getSubscriptionItem, params);
		params = null;
		basedao = null;
		return list;
	}

	/**
	 * ��ȡ��װ��
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
	 * ��ȡ�����
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
	 * ���ݶ�����ȡ��Ʒ�ʷ�
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
	 * ��ȡ�����
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

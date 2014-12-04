package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.AccountCheckAllBean;
import com.zhcs.billing.use.bean.AccountCheckBean;
import com.zhcs.billing.use.bean.AccountInfoBean;
import com.zhcs.billing.use.bean.OrderDetailBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.PartnerSettlementRuleBean;
import com.zhcs.billing.use.bean.ProductItemBean;
import com.zhcs.billing.use.bean.ProductResourceBean;
import com.zhcs.billing.use.bean.RDetailRecordAbilityBean;
import com.zhcs.billing.use.bean.SubscriptionItemBean;
import com.zhcs.billing.use.bean.TCulOrderDetailBean;
import com.zhcs.billing.use.bean.TScanningAddTotalBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.CalendarUtil;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 
 * @author hefa
 * 
 */
public class BillingQuery {
	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(BillingQuery.class);
	private static Logger log = LoggerFactory.getLogger(BillingQuery.class);

	/**
	 * ��������ID��ѯ������ϵ
	 * 
	 * @param orderInfoBean
	 * @param objects
	 * @return
	 */
	public static String getOrderID(String CONTAINER_ID) {
		String res = null;
		BaseDao basedao = new BaseDao();
		List params = new ArrayList();
		params.add(CONTAINER_ID);
		String sql = "SELECT ORDER_ID FROM ORDER_INFO where CONTAINER_ID = ?; ";
		List<HashMap<String, Object>> list = basedao.doSelect(sql, params);
		params = null;
		basedao = null;
		if (list != null && !list.isEmpty()) {
			res = (String) list.get(0).get("ORDER_ID");
		}
		return res;
	}

	/**
	 * ���ݶ�����Ų�ѯ��Ч�깺��Ʒ
	 * 
	 * @param orderInfoBean
	 * @param objects
	 * @return
	 */
	public static OrderDetailBean getProductNumber(String ORDER_ID) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getProductNumber(ORDER_ID);
		dao = null;
		OrderDetailBean bean = new OrderDetailBean();
		try {
			if (list != null) {
				List<OrderDetailBean> beans = bean.changeToObject(list);// ��object����ת����OrderDetailBean
				list = null;
				for (OrderDetailBean orderDetailBean : beans) {
					bean = orderDetailBean;
				}
				beans = null;
			}
		} catch (Exception e) {
			logUtil.error("���ݶ�����ѯ������ϸObjectתBean�������Ϊ" + ORDER_ID + "�����쳣��"
					+ e.getMessage());
			log.error("���ݶ�����ѯ������ϸObjectתBean�������Ϊ" + ORDER_ID + "�����쳣��"
					+ e.getMessage());
		}
		// logUtil.info("���ݶ�����ѯ������ϸObjectתBean�������Ϊ"+ORDER_ID+"�Ķ�����ϸ��ţ�" +
		// bean.getDETAIL_ID());
		// log.info("���ݶ�����ѯ������ϸObjectתBean�������Ϊ"+ORDER_ID+"�Ķ�����ϸ��ţ�" +
		// bean.getDETAIL_ID());
		return bean;
	}

	/**
	 * ���ݲ�Ʒ��Ϣ��ѯ������Դ��Ϣ
	 * 
	 * @param infoBean
	 * @param objects
	 */
	public static List<ProductResourceBean> getResourceInfo(String PRODUCT_ID) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getProductResource(PRODUCT_ID);// ���ݲ�Ʒ��Ų�ѯ��Դ��Ϣ
		dao = null;
		ProductResourceBean bean = new ProductResourceBean();
		List<ProductResourceBean> beans = new ArrayList<ProductResourceBean>();
		try {
			if (list != null) {
				beans = bean.changeToObject(list);// ��object����ת����ProductResourceBean
			}
		} catch (Exception e) {
			log.error("���ݲ�Ʒ��Ϣ��ѯ������Դ��ϢObjectתBean��Ʒ���Ϊ" + PRODUCT_ID + "�����쳣��"
					+ e.getMessage());
			logUtil.error("���ݲ�Ʒ��Ϣ��ѯ������Դ��ϢObjectתBean��Ʒ���Ϊ" + PRODUCT_ID
					+ "�����쳣��" + e.getMessage());
		}
		bean = null;
		// log.info("���ݲ�Ʒ��Ϣ��ѯ������Դ��ϢObjectתBean��Ʒ���Ϊ"+PRODUCT_ID+"�Ĳ�Ʒ��Դ��" +
		// beans.size() + "��");
		// logUtil.info("���ݲ�Ʒ��Ϣ��ѯ������Դ��ϢObjectתBean��Ʒ���Ϊ"+PRODUCT_ID+"�Ĳ�Ʒ��Դ��" +
		// beans.size() + "��");

		for (ProductResourceBean resourceBean : beans) {
			List<ProductResourceBean> listBeans = new ArrayList<ProductResourceBean>();
			for (ProductResourceBean resourceBean2 : beans) {
				try {
					if (resourceBean.getRESOURCE_ID().equals(
							resourceBean2.getPARENT_ID())) {
						listBeans.add(resourceBean2);
					}
				} catch (Exception e) {
					log.error("��Ʒ��Դ���Ϊ��" + resourceBean.getRESOURCE_ID()
							+ "��ȡ����Դ�����쳣��" + e.getMessage());
					logUtil.error("��Ʒ��Դ���Ϊ��" + resourceBean.getRESOURCE_ID()
							+ "��ȡ����Դ�����쳣��" + e.getMessage());
				}

			}
			resourceBean.setProductResourceBeans(listBeans);
		}
		return beans;
	}

	/**
	 * ���ݲ�Ʒ��Ϣ��ѯγ����Ϣ
	 * 
	 * @param resourceBean
	 * @param objects
	 */
	public static List<ProductItemBean> getProductItem(String PRODUCT_ID) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getProductItem(PRODUCT_ID);
		dao = null;
		ProductItemBean bean = new ProductItemBean();
		List<ProductItemBean> beans = new ArrayList<ProductItemBean>();
		try {
			if (list != null) {
				beans = bean.changeToObject(list);// ��object����ת����ProductResourceBean
			}
		} catch (Exception e) {
			log.error("���ݲ�Ʒ��Ϣ��ѯγ����ϢObjectתBean��Ʒ���Ϊ" + PRODUCT_ID + "�����쳣��"
					+ e.getMessage());
			logUtil.error("���ݲ�Ʒ��Ϣ��ѯγ����ϢObjectתBean��Ʒ���Ϊ" + PRODUCT_ID + "�����쳣��"
					+ e.getMessage());
		}
		bean = null;
		// log.info("���ݲ�Ʒ��Ϣ��ѯγ����ϢObjectתBean��Ʒ���Ϊ"+PRODUCT_ID+"�Ĳ�Ʒά���У�" +
		// beans.size() + "��");
		// logUtil.info("���ݲ�Ʒ��Ϣ��ѯγ����ϢObjectתBean��Ʒ���Ϊ"+PRODUCT_ID+"�Ĳ�Ʒά���У�" +
		// beans.size() + "��");
		return beans;
	}

	/**
	 * ���ݶ�����ȡγ�Ȼ�����
	 * 
	 * @param orderInfoBean
	 * @param timeInterval
	 * @param sCANNING_WAY
	 * @return
	 */
	public static List<TScanningAddTotalBean> getCumulants(
			OrderInfoBean orderInfoBean, String timeInterval,
			String sCANNING_WAY) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getCumulants(orderInfoBean,
				timeInterval, sCANNING_WAY);
		dao = null;
		TScanningAddTotalBean bean = new TScanningAddTotalBean();
		List<TScanningAddTotalBean> beans = new ArrayList<TScanningAddTotalBean>();
		try {
			if (list != null) {
				beans = bean.changeToObject(list);
			}
		} catch (Exception e) {
			log.error("���ݶ�����ȡγ�Ȼ���ObjectתBean����Ϊ" + orderInfoBean.getORDER_ID()
					+ "�����쳣��" + e.getMessage());
			logUtil.error("���ݶ�����ȡγ�Ȼ�����ObjectתBean����Ϊ"
					+ orderInfoBean.getORDER_ID() + "�����쳣��" + e.getMessage());
		}
		bean = null;
		// log.info("���ݶ�����ȡγ�Ȼ���ObjectתBean����Ϊ"+orderInfoBean.getORDER_ID()+"���ۻ�����ά�ȣ�"
		// + beans.size()+"��");
		// logUtil.info("���ݶ�����ȡγ�Ȼ�����ObjectתBean����Ϊ"+orderInfoBean.getORDER_ID()+"���ۻ�����ά�ȣ�"
		// + beans.size()+"��");
		return beans;
	}

	/**
	 * ���ݶ�����ѯ�����ϴο۷����
	 * 
	 * @param order_ID
	 * @param timeInterval
	 * @param SCANNING_WAY
	 * @return
	 */
	public static List<TCulOrderDetailBean> getCulOrderDetailBeans(
			String order_ID, String time, String SCANNING_WAY) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getCulOrderDetailBeans(
				order_ID, time, SCANNING_WAY);
		dao = null;
		TCulOrderDetailBean bean = new TCulOrderDetailBean();
		List<TCulOrderDetailBean> beans = new ArrayList<TCulOrderDetailBean>();
		try {
			if (list != null) {
				beans = bean.changeToObject(list);
			}
		} catch (Exception e) {
			log.error("���ݶ�����ѯ�����ϴο۷����ObjectתBean����Ϊ" + order_ID + "�����쳣��"
					+ e.getMessage());
			logUtil.error("���ݶ�����ѯ�����ϴο۷����ObjectתBean����Ϊ" + order_ID + "�����쳣��"
					+ e.getMessage());
		}
		bean = null;
		// log.info("���ݶ�����ѯ�����ϴο۷����ObjectתBean����Ϊ"+order_ID+"�����ϴο۷����" +
		// beans.size()+"��");
		// logUtil.info("���ݶ�����ѯ�����ϴο۷����ObjectתBean����Ϊ"+order_ID+"�����ϴο۷����"
		// +beans.size()+"��");
		return beans;
	}

	/**
	 * ���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸ
	 * 
	 * @param SUBSCRIBER_ID
	 *            �깺���
	 * @param PRODUCT_ID
	 *            ��Ʒ���
	 * @param time
	 *            ɨ��ʱ��
	 * @return
	 */
	public static List<SubscriptionItemBean> getSubscriptionItems(
			String SUBSCRIBER_ID, String PRODUCT_ID) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getSubscriptionItems(
				SUBSCRIBER_ID, PRODUCT_ID);
		dao = null;
		SubscriptionItemBean bean = new SubscriptionItemBean();
		List<SubscriptionItemBean> beans = new ArrayList<SubscriptionItemBean>();
		try {
			if (list != null) {
				beans = bean.changeToObject(list);
			}
		} catch (Exception e) {
			log.error("���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸObjectתBean�깺���Ϊ" + SUBSCRIBER_ID
					+ "�����쳣��" + e.getMessage());
			logUtil.error("���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸObjectתBean�깺���Ϊ" + SUBSCRIBER_ID
					+ "�����쳣��" + e.getMessage());
		}
		bean = null;
		// log.info("���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸObjectתBean�깺���Ϊ"+SUBSCRIBER_ID+"��Ʒ�ײͣ�" +
		// beans.size()+"��");
		// logUtil.info("���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸObjectתBean�깺���Ϊ"+SUBSCRIBER_ID+"��Ʒ�ײͣ�"
		// + beans.size()+"��");
		return beans;
	}

	/**
	 * �޸��깺��ϸ������ײ�ʣ����
	 * 
	 * @param si_ID
	 * @param item_AMOUNT
	 */
	public static void updateSubscriptionItem(String si_ID, Integer item_AMOUNT) {
		boolean flat = false;
		String sql = "UPDATE SUBSCRIPTION_ITEM SET REMAINING_AMOUNT = ? WHERE SI_ID = ?";
		List params = new ArrayList();
		params.add(item_AMOUNT);// �ײ�ʣ����
		params.add(si_ID);// �깺��ϸ���
		try {
			BaseDao basedao = new BaseDao();
			flat = basedao.doSaveOrUpdate(sql, params) > 0;
		} catch (Exception e) {
			log.error(" �޸��깺��ϸ������ײ�ʣ����dao�����쳣��" + e.getMessage() + "SQL���:"
					+ sql);
			logUtil.error(" �޸��깺��ϸ������ײ�ʣ����dao�����쳣��" + e.getMessage() + "SQL���:"
					+ sql);
		}
		params = null;
		sql = null;
		// log.info("�޸��깺��ϸ������ײ�ʣ����"+si_ID+"��Ʒ�ײͣ�" + flat);
		// logUtil.info("�޸��깺��ϸ������ײ�ʣ����"+si_ID+"��Ʒ�ײͣ�" +flat);
	}

	/**
	 * ��ѯ��Ʒ���ڵ���Դ
	 * 
	 * @param product_ID
	 * @return
	 */
	public static String getRootResourceInfo(String product_ID) {
		String sql = "SELECT RESOURCE_ID FROM SCBM.PRODUCT_RESOURCE WHERE PRODUCT_ID = ? AND NODE_TYPE = '2' AND RESOURCE_STATUS = '1'";
		List params = new ArrayList();
		params.add(product_ID);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			BaseDao basedao = new BaseDao();
			list = basedao.doSelect(sql, params);
		} catch (Exception e) {
			log.error(" ��ѯ��Ʒ���ڵ���Դdao�����쳣��" + e.getMessage() + "SQL���:" + sql);
			logUtil.error(" ��ѯ��Ʒ���ڵ���Դdao�����쳣��" + e.getMessage() + "SQL���:"
					+ sql);
		}
		params = null;
		sql = null;
		String root = null;
		for (HashMap<String, Object> hashMap : list) {
			root = hashMap.get("RESOURCE_ID") != null ? hashMap.get(
					"RESOURCE_ID").toString() : null;
		}
		return root;
	}

	public static void rInfoAbility(RDetailRecordAbilityBean bean) throws Exception {
		// TODO Auto-generated method stub
		BaseDao basedao = new BaseDao();
		String sql = "";
		List params = new ArrayList();
		List<HashMap<String, Object>> list;

		// ������: ��������ID��ȡ������� �ͻ����;
		sql = "select ORDER_ID,CUSTOMER_ID from ORDER_INFO where CONTAINER_ID = ?;";
		params.clear();
		params.add(bean.getCOUNTAINER_ID());
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setORDER_ID((String) list.get(0).get("ORDER_ID"));
			bean.setCUSTOMER_ID((String) list.get(0).get("CUSTOMER_ID"));
		}

		// ���ݶ�����ȡ��Ʒ���.
		sql = SqlString.getProductNumber;
		params.clear();
		params.add(bean.getORDER_ID());
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setPRODUCT_ID((String) list.get(0).get("PRODUCT_ID"));
			bean.setSUBSCRIBER_ID((String) list.get(0).get("SUBSCRIBER_ID"));
		}

		// // ���ݲ�Ʒ��ȡ����γ����Ϣ
		sql = SqlString.getProductItem + " AND ITEM_CODE = ?;";
		params.clear();
		params.add(bean.getPRODUCT_ID());
		params.add(BillingQuery.rMsgType(bean.getMSG_TYPE()));
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setITEM_ID((String) list.get(0).get("ITEM_ID"));
			bean.setPRICE(Integer.parseInt((String) list.get(0).get("PRICE")));
		}

		// ���ݲ�Ʒ��š��깺��Ų�ѯ�깺��ϸ
		sql = SqlString.getSubscriptionItem + " AND ITEM_ID = ?";
		params.clear();
		params.add(bean.getSUBSCRIBER_ID());
		params.add(bean.getPRODUCT_ID());
		params.add(bean.getITEM_ID());
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setSI_ID((String) list.get(0).get("SI_ID"));
			bean.setPD_ID((String) list.get(0).get("PD_ID"));
			bean.setREMAINING_AMOUNT(Integer.parseInt((String) list.get(0).get(
					"REMAINING_AMOUNT")));
		}

		// �Ƿ񶩹��ײ�
		// 0�� û���ײͣ�û���Żݣ�����
		// 1�� ���ײͣ��ײ������꣬����
		// 2�� ���ײͣ��ײ�δ���꣬���ײ�
		bean.setDEDUCTION_TYPE(bean.getPD_ID() == null ? 0 : (bean
				.getREMAINING_AMOUNT() > 0 ? 2 : 1));

		// ���㲿�� :�������� Ԥ����/�󸶷� �˻�ID �˱�ID ʡ�ݱ��� ��������
		bean.setPAYMENT_TYPE(1); // ���ݶ� Ĭ�Ϻ󸶷�
		sql = "select ACCOUNT_ID,PROVINCE_CODE,AREA_CODE from ACCOUNT_INFO where CUSTOMER_ID = ? and ACCOUNT_TYPE = ?;";
		params.clear();
		params.add(bean.getCUSTOMER_ID());
		params.add(bean.getPAYMENT_TYPE() + 1);// 0��1 -> 1��2
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setACCOUNT_ID((String) list.get(0).get("ACCOUNT_ID"));
			bean.setPROVINCE_CODE((String) list.get(0).get("PROVINCE_CODE"));
			bean.setAREA_CODE((String) list.get(0).get("REMAINING_AMOUNT"));
		}

		sql = "select BOOK_ID from ACCOUNT_BOOK where ACCOUNT_ID = ?;";
		params.clear();
		params.add(bean.getACCOUNT_ID());
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setBOOK_ID((String) list.get(0).get("BOOK_ID"));
		}

	}

	// ���ֵ��ѯ�������ͣ�ά�ȱ��룩
	public static String rMsgType(int type) {
		String res = null;
		BillingBaseDao dao = new BillingBaseDao();
		List params = new ArrayList();
		params.add(Integer.toString(type));
		String sql = "SELECT DICT_VALUE FROM R_DICT where DICT_KEY = ?; ";
		List<HashMap<String, Object>> list = dao.doSelect(sql, params);
		params = null;
		dao = null;
		if (list != null && !list.isEmpty()) {
			res = (String) list.get(0).get("DICT_VALUE");
		}
		return res;
	}

	public static List<PartnerSettlementRuleBean> PartnerSettlementRule()
			throws Exception {
		// TODO Auto-generated method stub
		String sql;
		BillingBaseDao dao = new BillingBaseDao();

		sql = "select * from PARTNER_SETTLEMENT_RULE where STATUS = 0;";
		List<HashMap<String, Object>> list = dao.doSelect(sql);

		List<PartnerSettlementRuleBean> beans = PartnerSettlementRuleBean
				.changeToObject(list);

		return beans;
	}

	public static int PartnerSettlementCount(PartnerSettlementRuleBean bean) {
		// ��ȡʱ����
		int res = 0;

		// TODO Auto-generated method stub
		String sql = "select count(*) from R_DETAIL_RECORD_ABILITY where MSG_TYPE = ? and ORIGINAL_RECORD_TIME > ? and ORIGINAL_RECORD_TIME < ?;";
		List params = new ArrayList();
		params.add(bean.getABILITY_TYPE());
		params.add(CalendarUtil.lastMonth());
		params.add(CalendarUtil.thisMonth());

		List<HashMap<String, Object>> li = new BillingBaseDao().doSelect(sql,
				params);
		if (li != null && !li.isEmpty()) {
			res = Integer.parseInt((String) li.get(0).get("count(*)"));
		}

		return res;
	}

	public static int PartnerSettlementProfit(PartnerSettlementRuleBean bean) {
		// TODO Auto-generated method stub
		int res = 0;
		// TODO
		return res;
	}

	public static AccountCheckAllBean AccountCheckAll() {
		// TODO Auto-generated method stub
		AccountCheckAllBean bean = new AccountCheckAllBean();
		String sql = "select sum(INCOME),sum(OUTCOME),sum(BALANCE) from ACCOUNT_CHECK where CHECK_DATE between ? and ?;";
		List params = new ArrayList();
		params.add(CalendarUtil.yestoday());
		params.add(CalendarUtil.today());

		List<HashMap<String, Object>> li = new BillingBaseDao().doSelect(sql,
				params);
		if (li != null && !li.isEmpty()) {
			bean.setINCOME(Integer.parseInt((String) li.get(0).get(
					"sum(INCOME)")));
			bean.setOUTCOME(Integer.parseInt((String) li.get(0).get(
					"sum(OUTCOME)")));
			bean.setBALANCE(Integer.parseInt((String) li.get(0).get(
					"sum(BALANCE)")));
		}
		return bean;
	}

	public static List<AccountCheckBean> AccountCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<AccountInfoBean> AccountInfo() {
		// TODO Auto-generated method stub
		List<AccountInfoBean> res = null;
		String sql = "select ACCOUNT_ID,CUSTOMER_ID from ACCOUNT_INFO";
		List<HashMap<String, Object>> li = new BaseDao().doSelect(sql);
		if (li != null && !li.isEmpty()) {
			res = AccountInfoBean.changeToObject(li);
		}
		return res;
	}

	public static int AccountTransactionCount(AccountInfoBean bean) {
		// TODO Auto-generated method stub
		int res = 0;
		String sql = "select count(*) from ACCOUNT_TRANSACTION where ACCOUNT_ID = ? and DEAL_TIME between ? and ?;";
		List params = new ArrayList();
		params.add(bean.getACCOUNT_ID());
		params.add(CalendarUtil.yestoday());
		params.add(CalendarUtil.today());
		List<HashMap<String, Object>> li = new BaseDao().doSelect(sql, params);
		if (li != null && !li.isEmpty()) {
			res = Integer.parseInt((String) li.get(0).get("count(*)"));
		}

		return res;
	}

	public static void AccountCheck(AccountCheckBean b) {
		// TODO Auto-generated method stub
		BaseDao dao = new BaseDao();
		List params = new ArrayList();
		String sql;
		List<HashMap<String, Object>> li;

		// -- income
		sql = "select sum(AMOUNT) from ACCOUNT_TRANSACTION where ACCOUNT_ID = ? and TRANSANCTION_TYPE = 1 and DEAL_TIME between ? and ?;";
		params.clear();
		params.add(b.getACCOUNT_ID());
		params.add(CalendarUtil.yestoday());
		params.add(CalendarUtil.today());
		li = dao.doSelect(sql, params);
		if (li != null && !li.isEmpty()) {
			b.setINCOME(Integer.parseInt((String) li.get(0).get("sum(AMOUNT)")));
		}

		// -- outcome
		sql = "select sum(AMOUNT) from ACCOUNT_TRANSACTION where ACCOUNT_ID = ? and TRANSANCTION_TYPE = 2 and DEAL_TIME between ? and ?;";
		params.clear();
		params.add(b.getACCOUNT_ID());
		params.add(CalendarUtil.yestoday());
		params.add(CalendarUtil.today());
		li = dao.doSelect(sql, params);
		if (li != null && !li.isEmpty()) {
			b.setOUTCOME(Integer
					.parseInt((String) li.get(0).get("sum(AMOUNT)")));
		}

		// -- balance
		sql = "select BALANCE from ACCOUNT_TRANSACTION where ACCOUNT_ID = ? and DEAL_TIME between ? and ?  order by DEAL_TIME desc limit 1;";
		params.clear();
		params.add(b.getACCOUNT_ID());
		params.add(CalendarUtil.yestoday());
		params.add(CalendarUtil.today());
		li = dao.doSelect(sql, params);
		if (li != null && !li.isEmpty()) {
			b.setBALANCE(Integer.parseInt((String) li.get(0).get("BALANCE")));
		}
	}
}

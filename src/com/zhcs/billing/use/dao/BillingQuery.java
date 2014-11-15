package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.OrderDetailBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.ProductItemBean;
import com.zhcs.billing.use.bean.ProductResourceBean;
import com.zhcs.billing.use.bean.SubscriptionItemBean;
import com.zhcs.billing.use.bean.TCulOrderDetailBean;
import com.zhcs.billing.use.bean.TScanningAddTotalBean;
import com.zhcs.billing.util.BaseDao;
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
}

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
	 * 根据订单编号查询有效申购产品
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
				List<OrderDetailBean> beans = bean.changeToObject(list);// 将object对象转换成OrderDetailBean
				list = null;
				for (OrderDetailBean orderDetailBean : beans) {
					bean = orderDetailBean;
				}
				beans = null;
			}
		} catch (Exception e) {
			logUtil.error("根据订单查询订单详细Object转Bean订单编号为" + ORDER_ID + "出现异常："
					+ e.getMessage());
			log.error("根据订单查询订单详细Object转Bean订单编号为" + ORDER_ID + "出现异常："
					+ e.getMessage());
		}
		// logUtil.info("根据订单查询订单详细Object转Bean订单编号为"+ORDER_ID+"的订单明细编号：" +
		// bean.getDETAIL_ID());
		// log.info("根据订单查询订单详细Object转Bean订单编号为"+ORDER_ID+"的订单明细编号：" +
		// bean.getDETAIL_ID());
		return bean;
	}

	/**
	 * 根据产品信息查询所属资源信息
	 * 
	 * @param infoBean
	 * @param objects
	 */
	public static List<ProductResourceBean> getResourceInfo(String PRODUCT_ID) {
		BillingQueryDao dao = new BillingQueryDao();
		List<HashMap<String, Object>> list = dao.getProductResource(PRODUCT_ID);// 根据产品编号查询资源信息
		dao = null;
		ProductResourceBean bean = new ProductResourceBean();
		List<ProductResourceBean> beans = new ArrayList<ProductResourceBean>();
		try {
			if (list != null) {
				beans = bean.changeToObject(list);// 将object对象转换成ProductResourceBean
			}
		} catch (Exception e) {
			log.error("根据产品信息查询所属资源信息Object转Bean产品编号为" + PRODUCT_ID + "出现异常："
					+ e.getMessage());
			logUtil.error("根据产品信息查询所属资源信息Object转Bean产品编号为" + PRODUCT_ID
					+ "出现异常：" + e.getMessage());
		}
		bean = null;
		// log.info("根据产品信息查询所属资源信息Object转Bean产品编号为"+PRODUCT_ID+"的产品资源：" +
		// beans.size() + "条");
		// logUtil.info("根据产品信息查询所属资源信息Object转Bean产品编号为"+PRODUCT_ID+"的产品资源：" +
		// beans.size() + "条");

		for (ProductResourceBean resourceBean : beans) {
			List<ProductResourceBean> listBeans = new ArrayList<ProductResourceBean>();
			for (ProductResourceBean resourceBean2 : beans) {
				try {
					if (resourceBean.getRESOURCE_ID().equals(
							resourceBean2.getPARENT_ID())) {
						listBeans.add(resourceBean2);
					}
				} catch (Exception e) {
					log.error("产品资源编号为：" + resourceBean.getRESOURCE_ID()
							+ "获取子资源出现异常：" + e.getMessage());
					logUtil.error("产品资源编号为：" + resourceBean.getRESOURCE_ID()
							+ "获取子资源出现异常：" + e.getMessage());
				}

			}
			resourceBean.setProductResourceBeans(listBeans);
		}
		return beans;
	}

	/**
	 * 根据产品信息查询纬度信息
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
				beans = bean.changeToObject(list);// 将object对象转换成ProductResourceBean
			}
		} catch (Exception e) {
			log.error("根据产品信息查询纬度信息Object转Bean产品编号为" + PRODUCT_ID + "出现异常："
					+ e.getMessage());
			logUtil.error("根据产品信息查询纬度信息Object转Bean产品编号为" + PRODUCT_ID + "出现异常："
					+ e.getMessage());
		}
		bean = null;
		// log.info("根据产品信息查询纬度信息Object转Bean产品编号为"+PRODUCT_ID+"的产品维度有：" +
		// beans.size() + "条");
		// logUtil.info("根据产品信息查询纬度信息Object转Bean产品编号为"+PRODUCT_ID+"的产品维度有：" +
		// beans.size() + "条");
		return beans;
	}

	/**
	 * 根据订单获取纬度积量表
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
			log.error("根据订单获取纬度积量Object转Bean订单为" + orderInfoBean.getORDER_ID()
					+ "出现异常：" + e.getMessage());
			logUtil.error("根据订单获取纬度积量表Object转Bean订单为"
					+ orderInfoBean.getORDER_ID() + "出现异常：" + e.getMessage());
		}
		bean = null;
		// log.info("根据订单获取纬度积量Object转Bean订单为"+orderInfoBean.getORDER_ID()+"有累积量的维度："
		// + beans.size()+"条");
		// logUtil.info("根据订单获取纬度积量表Object转Bean订单为"+orderInfoBean.getORDER_ID()+"有累积量的维度："
		// + beans.size()+"条");
		return beans;
	}

	/**
	 * 根据订单查询本月上次扣费情况
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
			log.error("根据订单查询本月上次扣费情况Object转Bean订单为" + order_ID + "出现异常："
					+ e.getMessage());
			logUtil.error("根据订单查询本月上次扣费情况Object转Bean订单为" + order_ID + "出现异常："
					+ e.getMessage());
		}
		bean = null;
		// log.info("根据订单查询本月上次扣费情况Object转Bean订单为"+order_ID+"本月上次扣费情况" +
		// beans.size()+"条");
		// logUtil.info("根据订单查询本月上次扣费情况Object转Bean订单为"+order_ID+"本月上次扣费情况"
		// +beans.size()+"条");
		return beans;
	}

	/**
	 * 根据产品编号、申购编号查询申购明细
	 * 
	 * @param SUBSCRIBER_ID
	 *            申购编号
	 * @param PRODUCT_ID
	 *            产品编号
	 * @param time
	 *            扫描时间
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
			log.error("根据产品编号、申购编号查询申购明细Object转Bean申购编号为" + SUBSCRIBER_ID
					+ "出现异常：" + e.getMessage());
			logUtil.error("根据产品编号、申购编号查询申购明细Object转Bean申购编号为" + SUBSCRIBER_ID
					+ "出现异常：" + e.getMessage());
		}
		bean = null;
		// log.info("根据产品编号、申购编号查询申购明细Object转Bean申购编号为"+SUBSCRIBER_ID+"产品套餐：" +
		// beans.size()+"条");
		// logUtil.info("根据产品编号、申购编号查询申购明细Object转Bean申购编号为"+SUBSCRIBER_ID+"产品套餐："
		// + beans.size()+"条");
		return beans;
	}

	/**
	 * 修改申购详细表里的套餐剩余量
	 * 
	 * @param si_ID
	 * @param item_AMOUNT
	 */
	public static void updateSubscriptionItem(String si_ID, Integer item_AMOUNT) {
		boolean flat = false;
		String sql = "UPDATE SUBSCRIPTION_ITEM SET REMAINING_AMOUNT = ? WHERE SI_ID = ?";
		List params = new ArrayList();
		params.add(item_AMOUNT);// 套餐剩余量
		params.add(si_ID);// 申购明细编号
		try {
			BaseDao basedao = new BaseDao();
			flat = basedao.doSaveOrUpdate(sql, params) > 0;
		} catch (Exception e) {
			log.error(" 修改申购详细表里的套餐剩余量dao出现异常：" + e.getMessage() + "SQL语句:"
					+ sql);
			logUtil.error(" 修改申购详细表里的套餐剩余量dao出现异常：" + e.getMessage() + "SQL语句:"
					+ sql);
		}
		params = null;
		sql = null;
		// log.info("修改申购详细表里的套餐剩余量"+si_ID+"产品套餐：" + flat);
		// logUtil.info("修改申购详细表里的套餐剩余量"+si_ID+"产品套餐：" +flat);
	}

	/**
	 * 查询产品根节点资源
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
			log.error(" 查询产品根节点资源dao出现异常：" + e.getMessage() + "SQL语句:" + sql);
			logUtil.error(" 查询产品根节点资源dao出现异常：" + e.getMessage() + "SQL语句:"
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

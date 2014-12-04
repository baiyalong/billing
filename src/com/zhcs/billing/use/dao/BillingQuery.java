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
	 * 根据容器ID查询订购关系
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

	public static void rInfoAbility(RDetailRecordAbilityBean bean) throws Exception {
		// TODO Auto-generated method stub
		BaseDao basedao = new BaseDao();
		String sql = "";
		List params = new ArrayList();
		List<HashMap<String, Object>> list;

		// 订单表: 根据容器ID获取订单编号 客户编号;
		sql = "select ORDER_ID,CUSTOMER_ID from ORDER_INFO where CONTAINER_ID = ?;";
		params.clear();
		params.add(bean.getCOUNTAINER_ID());
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setORDER_ID((String) list.get(0).get("ORDER_ID"));
			bean.setCUSTOMER_ID((String) list.get(0).get("CUSTOMER_ID"));
		}

		// 根据订单获取产品编号.
		sql = SqlString.getProductNumber;
		params.clear();
		params.add(bean.getORDER_ID());
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setPRODUCT_ID((String) list.get(0).get("PRODUCT_ID"));
			bean.setSUBSCRIBER_ID((String) list.get(0).get("SUBSCRIBER_ID"));
		}

		// // 根据产品获取所有纬度信息
		sql = SqlString.getProductItem + " AND ITEM_CODE = ?;";
		params.clear();
		params.add(bean.getPRODUCT_ID());
		params.add(BillingQuery.rMsgType(bean.getMSG_TYPE()));
		list = basedao.doSelect(sql, params);
		if (list != null && !list.isEmpty()) {
			bean.setITEM_ID((String) list.get(0).get("ITEM_ID"));
			bean.setPRICE(Integer.parseInt((String) list.get(0).get("PRICE")));
		}

		// 根据产品编号、申购编号查询申购明细
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

		// 是否订购套餐
		// 0， 没有套餐，没有优惠，扣余额；
		// 1， 有套餐，套餐已用完，扣余额；
		// 2， 有套餐，套餐未用完，扣套餐
		bean.setDEDUCTION_TYPE(bean.getPD_ID() == null ? 0 : (bean
				.getREMAINING_AMOUNT() > 0 ? 2 : 1));

		// 结算部分 :付费类型 预付费/后付费 账户ID 账本ID 省份编码 地区编码
		bean.setPAYMENT_TYPE(1); // ！暂定 默认后付费
		sql = "select ACCOUNT_ID,PROVINCE_CODE,AREA_CODE from ACCOUNT_INFO where CUSTOMER_ID = ? and ACCOUNT_TYPE = ?;";
		params.clear();
		params.add(bean.getCUSTOMER_ID());
		params.add(bean.getPAYMENT_TYPE() + 1);// 0、1 -> 1、2
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

	// 从字典查询能力类型（维度编码）
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
		// 获取时间间隔
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

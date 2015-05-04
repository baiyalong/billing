package com.zhcs.billing.quartz.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.dao.BillingCumulative;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.CalendarUtil;
import com.zhcs.billing.util.LoggerUtil;

public class SimpleTreatment extends Task implements Job {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(SimpleTreatment.class);
	private static Logger log = LoggerFactory.getLogger(SimpleTreatment.class);

	public SimpleTreatment() {

	}

	private static HttpClient httpclient = null;
	private static PostMethod postMethod = null;
	private static String NoticeUrl = null;

	@Override
	public void execute(HashMap map) {
		// TODO Auto-generated method stub

		// 循环遍历所有满足条件的未处理订单
		while (getOrder(1)) { // 取一条?未处理订单

			for (OrderInfoBean bean : list) {

				// 计费
				bean = calculate(bean);
				if (bean.getPRICE() <= 0) {
					continue;
				}

				// 查询
				bean = query(bean);

				// 扣费
				if (!charge(bean)) {
					continue;
				}

				// 若账户余额不足 或 欠费 ，通知业务管理平台
				long balance = getBalance(bean);
				if (balance < bean.getPRICE() || balance < 0) {
					notice(bean, balance);
				}
			}
		}
	}

	private OrderInfoBean query(OrderInfoBean bean) {
		// TODO Auto-generated method stub
		try {
			BaseDao dao = new BaseDao();
			String sql = "select * from ACCOUNT_BOOK where ACCOUNT_ID=(select ACCOUNT_ID from ACCOUNT_INFO where CUSTOMER_ID = ? and ACCOUNT_TYPE = ?);";
			List params = new ArrayList();
			params.add(bean.getACCOUNT_CODE());
			params.add(1);// 预付费
			List<HashMap<String, Object>> list = dao.doSelect(sql, params);
			if (list != null && !list.isEmpty()) {
				bean.setBOOK_ID((String) list.get(0).get("BOOK_ID"));
				bean.setPROVINCE_CODE((String) list.get(0).get("PROVINCE_CODE"));
				bean.setAREA_CODE((String) list.get(0).get("AREA_CODE"));
				bean.setCUSTOMER_ID((String) list.get(0).get("CUSTOMER_ID"));
				bean.setACCOUNT_ID((String) list.get(0).get("ACCOUNT_ID"));
				bean.setBALANCE((Integer) list.get(0).get("BALANCE"));
			}
		} catch (Exception e) {
			log.error(bean.getORDER_ID() + "  SimpleTreatment--查询异常！");
			logUtil.error(bean.getORDER_ID() + "  SimpleTreatment--查询异常！");
			e.printStackTrace();
		}
		return bean;
	}

	private void notice(OrderInfoBean bean, long balance) {
		// account id
		String accountID = bean.getACCOUNT_ID();
		/*
		 * try { BaseDao dao = new BaseDao(); String sql =
		 * "select ACCOUNT_ID from ACCOUNT_INFO where ACCOUNT_CODE = ?"; List
		 * params = new ArrayList(); params.add(bean.getACCOUNT_CODE());
		 * List<HashMap<String, Object>> list = dao.doSelect(sql, params); if
		 * (list != null && !list.isEmpty()) { accountID = (String)
		 * list.get(0).get("ACCOUNT_ID"); } } catch (Exception e) {
		 * log.error(bean.getORDER_ID() + "  SimpleTreatment--查ACCOUNT_ID异常！");
		 * logUtil.error(bean.getORDER_ID() +
		 * "  SimpleTreatment--查ACCOUNT_ID异常！"); e.printStackTrace(); return; }
		 */

		// http post
		JSONObject jso = new JSONObject();
		jso.put("accountID", accountID);
		jso.put("balance", balance);
		String req = jso.toString();

		PostMethod postMethod = new PostMethod(getNoticeUrl());// getPostMethod();
		HttpClient httpclient = new HttpClient();// getHttpclient();
		postMethod.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.IGNORE_COOKIES);
		postMethod.setRequestHeader("Content-Type", "application/json");

		try {
			StringRequestEntity requestEntity = new StringRequestEntity(req,
					"application/json", "UTF-8");

			postMethod.setRequestEntity(requestEntity);
			httpclient.executeMethod(postMethod);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
			httpclient.getHttpConnectionManager().closeIdleConnections(0);
		}

	}

	private int getBalance(OrderInfoBean bean) {
		int res = 0;
		try {
			BaseDao dao = new BaseDao();
			String sql = "select BALANCE from ACCOUNT_BOOK where ACCOUNT_ID=?;";
			List params = new ArrayList();
			params.add(bean.getACCOUNT_ID());
			List<HashMap<String, Object>> list = dao.doSelect(sql, params);
			if (list != null && !list.isEmpty()) {
				res = (Integer) list.get(0).get("BALANCE");
			}
		} catch (Exception e) {
			log.error(bean.getORDER_ID() + "  SimpleTreatment--查余额异常！");
			logUtil.error(bean.getORDER_ID() + "  SimpleTreatment--查余额异常！");
			e.printStackTrace();
		}
		return res;
	}

	private boolean charge(OrderInfoBean bean) {
		// TODO Auto-generated method stub
		boolean res = false;

		try {
			BaseDao dao = new BaseDao();
			List<String> sql = new ArrayList();
			List params = new ArrayList();

			// 扣费-----------------------
			// 1。生成流水
			String serialNo = UUID.randomUUID().toString();
			String sq1 = "insert into TRADE_SERIAL (SERIAL_NO,CREATE_TIME) values (?,now());";
			List param1 = new ArrayList();
			param1.add(serialNo);
			sql.add(sq1);
			params.add(param1);

			// 2。添加账本收支记录
			String sq2 = "insert into ACCOUNT_TRANSACTION "
					+ "(TRANSACTION_ID,BOOK_ID,PROVINCE_CODE,AREA_CODE,CUSTOMER_ID,ACCOUNT_ID,SERIAL_NO,TRANSACTION_TYPE,INOUT_FLAG,AMOUNT,BALANCE,LOCK_AMOUNT,DEAL_TIME,DESCRIPTION)"
					+ "values " + "(?,?,?,?,?,?,?,2,4,?,?,0,now(),'Daily');";
			List param2 = new ArrayList();
			param2.add(UUID.randomUUID().toString());
			param2.add(bean.getBOOK_ID());
			param2.add(bean.getPROVINCE_CODE());
			param2.add(bean.getAREA_CODE());
			param2.add(bean.getCUSTOMER_ID());
			param2.add(bean.getACCOUNT_ID());
			param2.add(serialNo);
			param2.add(bean.getPRICE());
			param2.add(bean.getBALANCE() - bean.getPRICE()); // 收支记录里记录当前余额（扣完费的余额）
			sql.add(sq2);
			params.add(param2);

			// 3.账本扣费
			String sq3 = "update ACCOUNT_BOOK set BALANCE = BALANCE - ?,CREDIT_AMOUNT = CREDIT_AMOUNT + ? where BOOK_ID = ?;";
			List param3 = new ArrayList();
			param3.add(bean.getPRICE());
			param3.add(bean.getPRICE());
			param3.add(bean.getBOOK_ID());
			sql.add(sq3);
			params.add(param3);
			// -----------------------------------------

			/*
			 * String sql_1 =
			 * "update ACCOUNT_BOOK set BALANCE = BALANCE-?,CREDIT_AMOUNT=CREDIT_AMOUNT+? where ACCOUNT_ID=(select ACCOUNT_ID from ACCOUNT_INFO where CUSTOMER_ID = ? and ACCOUNT_TYPE=?);"
			 * ; List params_1 = new ArrayList(); params_1.add(amount);
			 * params_1.add(amount); params_1.add(bean.getACCOUNT_CODE());
			 * params_1.add(1);// 预付费 sql.add(sql_1); params.add(params_1);
			 */

			// 订单打时间戳
			String sql_2 = "update ORDER_INFO set BILLING_TIME = now() where ORDER_ID = ?;";
			List params_2 = new ArrayList();
			params_2.add(bean.getORDER_ID());
			sql.add(sql_2);
			params.add(params_2);

			// 话单记录
			String sql_3 = "insert into DETAIL_RECORD_D (AMOUNT,CUSTOMER_ID,CONTAINER_ID,PAYMENT_TYPE,RECORD_TIME) values (?,?,?,?,now());";
			List params_3 = new ArrayList();
			params_3.add(bean.getPRICE());
			params_3.add(bean.getCUSTOMER_ID());
			params_3.add(bean.getCONTAINER_ID());
			params_3.add(0);// 付费类型：0预付费；1后付费
			sql.add(sql_3);
			params.add(params_3);

			// -------
			int rs = dao.doSaveOrUpdateS(sql, params);
			if (rs == 0) {
				throw new Exception("SimpleTreatment--结算失败");
			}
			res = true;
		} catch (Exception e) {
			log.error(bean.getORDER_ID() + "  SimpleTreatment--结算异常！");
			logUtil.error(bean.getORDER_ID() + "  SimpleTreatment--结算异常！");
			e.printStackTrace();
		}

		return res;
	}

	private OrderInfoBean calculate(OrderInfoBean bean) {
		// TODO Auto-generated method stub
		int amount = 0;
		try {
			// 每月金额(厘)
			int Amount = bean.getAMOUNT();

			// 时间间隔(天)
			int between = 0;

			Calendar t = Calendar.getInstance();
			Date today = t.getTime();

			Calendar y = Calendar.getInstance();
			y.add(Calendar.DATE, -1);
			Date yestoday = y.getTime();

			Calendar l = Calendar.getInstance();
			Date lastBillingDay = bean.getBILLING_TIME();
			if (lastBillingDay == null) { // 第一天
				between = 1;
			} else {
				l.setTime(lastBillingDay);
				between = t.get(Calendar.DAY_OF_YEAR)
						- l.get(Calendar.DAY_OF_YEAR);
			}

			// 计费结果(厘) -- 每月最后一天补齐差额 -- 计算前一天（几天）的钱
			int days = y.getActualMaximum(Calendar.DAY_OF_MONTH);
			int day = y.get(Calendar.DAY_OF_MONTH) + 1;// 从0开始

			if (between == 1) {
				// 一天（ 昨天）
				amount = day != days ? Amount / days : Amount - Amount / days
						* (days - 1);
			} else if ((y.get(Calendar.MONTH) == l.get(Calendar.MONTH))) {
				// 多天（不跨月）
				amount = day != days ? Amount / days * between : Amount
						- Amount / days * (days - 1) + Amount / days
						* (between - 1);
			} else {
				// 多天（跨月）
				amount = day != days ? Amount / days * day : Amount;
				int daysl = l.getActualMaximum(Calendar.DAY_OF_MONTH);
				int dayl = l.get(Calendar.DAY_OF_MONTH);
				amount += dayl != daysl ? Amount - Amount / daysl * (daysl - 1)
						+ Amount / daysl * (daysl - dayl) : Amount - Amount
						/ daysl * (daysl - 1);
				int mbetween = y.get(Calendar.MONTH) - l.get(Calendar.MONTH);
				amount += Amount * (mbetween - 1);
			}

		} catch (Exception e) {
			log.error(bean.getORDER_ID() + "  SimpleTreatment--计费异常！");
			logUtil.error(bean.getORDER_ID() + "  SimpleTreatment--计费异常！");
			e.printStackTrace();
		}
		bean.setPRICE(amount);
		return bean;
	}

	/* private OrderInfoBean bean = null; */
	private List<OrderInfoBean> list = null;

	private boolean getOrder(int count) {
		// TODO Auto-generated method stub
		boolean res = false;
		try {
			String sql = "select * from ORDER_INFO where AMOUNT > 0 and ORDER_STATUS = ? and  PRODUCT_CATEGORY = ? and (BILLING_TIME < ? or BILLING_TIME is null) limit ?;";
			List params = new ArrayList();
			params.add(3);// 正常状态
			params.add(1);// 容器类产品
			params.add(Timestamp.valueOf(CalendarUtil.today() + " 00:00:00"));// 当前日期
			params.add(count);
			List<HashMap<String, Object>> li = new BaseDao().doSelect(sql,
					params);
			if (li != null && !li.isEmpty()) {
				this.list = OrderInfoBean.changeToObject(li);
				res = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("SimpleTreatment -- 获取未处理订单失败！");
			logUtil.error("SimpleTreatment -- 获取未处理订单失败！");
			res = false;
		}

		return res;
	}

	public static HttpClient getHttpclient() {
		return httpclient == null ? httpclient = new HttpClient() : httpclient;
	}

	/*
	 * public static void setHttpclient(HttpClient httpclient) { Msg.httpclient
	 * = httpclient; }
	 */

	public static PostMethod getPostMethod() {
		return postMethod == null ? postMethod = new PostMethod(getNoticeUrl())
				: postMethod;
	}

	/*
	 * public static void setPostMethod(PostMethod postMethod) { Msg.postMethod
	 * = postMethod; }
	 */

	public static String getNoticeUrl() {
		return NoticeUrl == null ? NoticeUrl = BillingCumulative
				.readProperties("NoticeUrl") : NoticeUrl;
	}

	/*
	 * public static void setRurl(String rurl) { Rurl = rurl; }
	 */
}

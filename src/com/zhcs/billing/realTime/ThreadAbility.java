package com.zhcs.billing.realTime;

import java.lang.Runnable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.RDetailRecordAbilityBean;
import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingCumulative;
import com.zhcs.billing.use.dao.BillingInsert;
import com.zhcs.billing.use.dao.BillingQuery;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.LoggerUtil;

public class ThreadAbility implements Runnable {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(ThreadAbility.class);
	private static Logger log = LoggerFactory.getLogger(ThreadAbility.class);

	private String msg = null;// 原始话单

	public ThreadAbility(String msg) {
		this.msg = msg;
		bean = new RDetailRecordAbilityBean();
		bean.setORIGINAL_RECORD(msg);
	}

	public void run() {

		if (!(this.GetData() && this.Billing() && this.Persistence())) {
			Msg.ErrorRecord(new RErrorRecordBean(Msg.MsgType.Ability.value(),
					this.msg, "实时计费-能力话单-异常"));
		}
	}

	private RDetailRecordAbilityBean bean;

	private static HttpClient httpclient = null;
	private static PostMethod postMethod = null;
	private static String NoticeUrl = null;

	// 获取计费所需的相关数据，并进行数据检验
	private boolean GetData() {
		boolean res = false;
		try {
			// 从原始话单中提取容器ID和话单类型
			bean.setCOUNTAINER_ID(Msg.containerID(msg));// 容器ID
			bean.setMSG_TYPE(Msg.msgType(msg)); // 能力类型
			bean.setITEM_CODE(Msg.itemCode(bean.getMSG_TYPE())); // 纬度类型 短彩等
			bean.setORIGINAL_RECORD_TIME(Msg.recordTime(msg)); // 原始话单生成时间

			// 从数据库中获取订购关系、用户信息、产品资费
			// 租户ID 付费类型 产品资费
			res = BillingQuery.rInfoAbility(bean);

		} catch (Exception e) {
			// TODO: handle exception
			res = false;
		}
		return res;
	}

	// 扣费
	private boolean Billing() {
		boolean res = true;
		try {
			// 优先扣除套餐
			if (!BillingInsert.RPackage(bean)) {
				// 扣账户余额
				BillingInsert.RBalance(bean);

				// 查阅
				String accountID = bean.getACCOUNT_ID();
				int balance = this.getBalance(accountID);
				if (balance < 0) {
					// 欠费通知
					// -----------------------------------------------------------------

					JSONObject jso = new JSONObject();
					jso.put("accountID", accountID);
					jso.put("balance", balance);
					String req = jso.toString();

					PostMethod postMethod = new PostMethod(getNoticeUrl());// getPostMethod();
					HttpClient httpclient = new HttpClient();// getHttpclient();
					postMethod.getParams().setParameter(
							"http.protocol.cookie-policy",
							CookiePolicy.IGNORE_COOKIES);

					postMethod.setRequestHeader("Content-Type",
							"application/json");

					try {
						StringRequestEntity requestEntity = new StringRequestEntity(
								req, "application/json", "UTF-8");

						postMethod.setRequestEntity(requestEntity);
						httpclient.executeMethod(postMethod);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						postMethod.releaseConnection();
						httpclient.getHttpConnectionManager()
								.closeIdleConnections(0);
					}

					log.info("余额不足，欠费，通知业务管理平台。。。");
					logUtil.info("余额不足，欠费，通知业务管理平台。。。");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}

		return res;
	}

	private int getBalance(String id) {
		int res = 0;
		try {
			BaseDao dao = new BaseDao();
			String sql = "select BALANCE from ACCOUNT_BOOK where ACCOUNT_ID=?;";
			List params = new ArrayList();
			params.add(id);
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

	// 详单入库
	private boolean Persistence() {
		boolean res = true;
		try {
			BillingInsert.RDetailRecordAbility(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

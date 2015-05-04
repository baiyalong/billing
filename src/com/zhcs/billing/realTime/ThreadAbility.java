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

	private String msg = null;// ԭʼ����

	public ThreadAbility(String msg) {
		this.msg = msg;
		bean = new RDetailRecordAbilityBean();
		bean.setORIGINAL_RECORD(msg);
	}

	public void run() {

		if (!(this.GetData() && this.Billing() && this.Persistence())) {
			Msg.ErrorRecord(new RErrorRecordBean(Msg.MsgType.Ability.value(),
					this.msg, "ʵʱ�Ʒ�-��������-�쳣"));
		}
	}

	private RDetailRecordAbilityBean bean;

	private static HttpClient httpclient = null;
	private static PostMethod postMethod = null;
	private static String NoticeUrl = null;

	// ��ȡ�Ʒ������������ݣ����������ݼ���
	private boolean GetData() {
		boolean res = false;
		try {
			// ��ԭʼ��������ȡ����ID�ͻ�������
			bean.setCOUNTAINER_ID(Msg.containerID(msg));// ����ID
			bean.setMSG_TYPE(Msg.msgType(msg)); // ��������
			bean.setITEM_CODE(Msg.itemCode(bean.getMSG_TYPE())); // γ������ �̲ʵ�
			bean.setORIGINAL_RECORD_TIME(Msg.recordTime(msg)); // ԭʼ��������ʱ��

			// �����ݿ��л�ȡ������ϵ���û���Ϣ����Ʒ�ʷ�
			// �⻧ID �������� ��Ʒ�ʷ�
			res = BillingQuery.rInfoAbility(bean);

		} catch (Exception e) {
			// TODO: handle exception
			res = false;
		}
		return res;
	}

	// �۷�
	private boolean Billing() {
		boolean res = true;
		try {
			// ���ȿ۳��ײ�
			if (!BillingInsert.RPackage(bean)) {
				// ���˻����
				BillingInsert.RBalance(bean);

				// ����
				String accountID = bean.getACCOUNT_ID();
				int balance = this.getBalance(accountID);
				if (balance < 0) {
					// Ƿ��֪ͨ
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

					log.info("���㣬Ƿ�ѣ�֪ͨҵ�����ƽ̨������");
					logUtil.info("���㣬Ƿ�ѣ�֪ͨҵ�����ƽ̨������");
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
			log.error(bean.getORDER_ID() + "  SimpleTreatment--������쳣��");
			logUtil.error(bean.getORDER_ID() + "  SimpleTreatment--������쳣��");
			e.printStackTrace();
		}
		return res;
	}

	// �굥���
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

package com.zhcs.billing.realTime;

import java.lang.Runnable;
import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.RDetailRecordAbilityBean;
import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingCumulative;
import com.zhcs.billing.use.dao.BillingInsert;
import com.zhcs.billing.use.dao.BillingQuery;
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
				if (!BillingInsert.RBalance(bean)) {
					// Ƿ��֪ͨ
					// -----------------------------------------------------------------
					String accountID = bean.getACCOUNT_ID();
					int balance = bean.getREMAINING_AMOUNT();
					JSONObject jso = new JSONObject();
					jso.put("accountID", accountID);
					jso.put("balance", balance);
					String req = jso.toString();
					getPostMethod().addRequestHeader("Content-Type",
							"application/json");

					try {
						StringRequestEntity requestEntity = new StringRequestEntity(
								req, "application/json", "UTF-8");

						getPostMethod().setRequestEntity(requestEntity);
						getHttpclient().executeMethod(getPostMethod());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		return httpclient == null ? new HttpClient() : httpclient;
	}

	/*
	 * public static void setHttpclient(HttpClient httpclient) { Msg.httpclient
	 * = httpclient; }
	 */

	public static PostMethod getPostMethod() {
		return postMethod == null ? new PostMethod(getNoticeUrl()) : postMethod;
	}

	/*
	 * public static void setPostMethod(PostMethod postMethod) { Msg.postMethod
	 * = postMethod; }
	 */

	public static String getNoticeUrl() {
		return NoticeUrl == null ? BillingCumulative
				.readProperties("NoticeUrl") : NoticeUrl;
	}

	/*
	 * public static void setRurl(String rurl) { Rurl = rurl; }
	 */

}

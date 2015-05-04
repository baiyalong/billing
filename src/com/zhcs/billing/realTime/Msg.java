package com.zhcs.billing.realTime;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingCumulative;
import com.zhcs.billing.use.dao.BillingInsert;

public class Msg {

	private String msg = null;

	public Msg(String msg) {
		this.msg = msg;
	}

	public static int count(String msg) {
		int res = 0;
		try {
			res = Integer.parseInt(msg.split("\n")[0].split("\\|")[7]);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}

	public static String[] liMsg(String msg) {
		String[] li = new String[] {};
		try {
			String[] m = msg.split("\n");
			int count = m.length - 1;
			if (count != Msg.count(msg)) {
				// 异常话单
				// TODO: handle exception

			}
			li = Arrays.copyOfRange(m, 1, count + 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return li;
	}

	// 获取容器ID
	public static String containerID(String msg) {
		String ua = msg.split("\\|")[3];
		int code = Msg.msgType(msg);
		String Code = "";
		switch (code) {
		case 10:
			Code = "SMS";
			break;
		case 11:
			Code = "MMS";
			break;
		case 12:
			Code = "LBS";
			break;
		case 13:
			Code = "GIS";
			break;
		default:
			break;
		}
		return Msg.getContainerID(ua, Code);
	}

	private static HttpClient httpclient = null;
	private static PostMethod postMethod = null;
	private static String Rurl = null;

	// 从容器引擎获取容器ID
	public static String getContainerID(String ua, String code) {
		String res = null;
		JSONObject jso = new JSONObject();
		jso.put("userAccount", ua);
		jso.put("abiCode", code);
		String req = jso.toString();

		PostMethod postMethod = new PostMethod(getRurl());// getPostMethod();
		HttpClient httpclient = new HttpClient();// getHttpclient();
		postMethod.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.IGNORE_COOKIES);
		postMethod.setRequestHeader("Content-Type", "application/json");

		try {
			StringRequestEntity requestEntity = new StringRequestEntity(req,
					"application/json", "UTF-8");

			postMethod.setRequestEntity(requestEntity);
			httpclient.executeMethod(postMethod);
			String resp = postMethod.getResponseBodyAsString();

			JSONObject job = JSONObject.fromObject(resp);
			String orderCode = job.getString("orderCode");
			String containerId = job.getString("containerId");
			res = containerId;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
			httpclient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return res;
	}

	// 获取话单类型
	// 10 短信
	// 11 彩信
	// 12 定位
	// 13 GIS
	public static int msgType(String msg) {
		return Integer.parseInt(msg.split("\\|")[8]);
	}

	public enum MsgType {
		// 利用构造函数传参
		Ability(0), App(1), DataFlow(2);

		// 定义私有变量
		private int nCode;

		// 构造函数，枚举类型只能为私有
		private MsgType(int _nCode) {
			this.nCode = _nCode;
		}

		public int value() {
			return this.nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}

	public static Timestamp recordTime(String msg) {
		DateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
		DateFormat fff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = msg.split("\\|")[1];
		try {
			return Timestamp.valueOf(fff.format(ff.parse(str)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 记录异常话单
	public static void ErrorRecord(RErrorRecordBean bean) {
		try {
			BillingInsert.RErrorRecord(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HttpClient getHttpclient() {
		return httpclient == null ? httpclient = new HttpClient() : httpclient;
	}

	/*
	 * public static void setHttpclient(HttpClient httpclient) { Msg.httpclient
	 * = httpclient; }
	 */

	public static PostMethod getPostMethod() {
		return postMethod == null ? postMethod = new PostMethod(getRurl())
				: postMethod;
	}

	/*
	 * public static void setPostMethod(PostMethod postMethod) { Msg.postMethod
	 * = postMethod; }
	 */

	public static String getRurl() {
		return Rurl == null ? Rurl = BillingCumulative.readProperties("Rurl")
				: Rurl;
	}

	public static String itemCode(int msg_TYPE) {
		// TODO Auto-generated method stub
		String Code = "";
		switch (msg_TYPE) {
		case 10:
			Code = "WD-SMS-T";
			break;
		case 11:
			Code = "WD-MMS-T";
			break;
		case 12:
			Code = "WD-LBS-T";
			break;
		case 13:
			Code = "WD-GIS-T";
			break;
		default:
			break;
		}

		return Code;
	}

	/*
	 * public static void setRurl(String rurl) { Rurl = rurl; }
	 */
}

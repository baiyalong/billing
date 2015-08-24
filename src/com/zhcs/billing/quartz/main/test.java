package com.zhcs.billing.quartz.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.service.AccountCheckTreatment;
import com.zhcs.billing.realTime.MqReceiver;
import com.zhcs.billing.realTime.ThreadMq;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.util.CalendarUtil;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.DbUtil;
import com.zhcs.billing.util.EnvironmentUtils;
import com.zhcs.billing.util.LoggerUtil;

public class test {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(test.class);
	private static Logger log = LoggerFactory.getLogger(test.class);

	public static void main(String[] args) throws IOException {


		GetMethod getMethod = new GetMethod(
				"http://baiyalong.net:4000/api/certApply/"
				//"http://10.192.18.164:8080/ctnrDefCtrl/rest/CtnrDefine/getCtnrPublicIp/000"
				);// getPostMethod();
		HttpClient httpclient = new HttpClient();// getHttpclient();
		getMethod.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.IGNORE_COOKIES);
		getMethod.setRequestHeader("Content-Type", "application/json");

		try {

			httpclient.executeMethod(getMethod);
			String resp = getMethod.getResponseBodyAsString();

						
			JSONArray ja = JSONArray.fromObject(resp);

			System.out.println(ja.toArray().length);
			for(Object o:ja.toArray()){
				System.out.println(o.toString());
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
			httpclient.getHttpConnectionManager().closeIdleConnections(0);
		}




	}
	
	
	
}

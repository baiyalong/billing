package com.zhcs.billing.use.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.MeteringBaseDao;

/**
 * 
 * @author yangke
 * 
 */
public class BillingCumulative {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(BillingCumulative.class);
	private static Logger log = LoggerFactory
			.getLogger(BillingCumulative.class);
	public static BillingBaseDao billing = new BillingBaseDao();
	public static MeteringBaseDao metering = new MeteringBaseDao();

	public static String readProperties(String name) {
		String fileName = "/url.properties";
		Properties properties = new Properties();
		String url = "";
		try {
			InputStream is = InputStream.class.getResourceAsStream(fileName);
			properties.load(is);
			is.close();
			if (properties.containsKey(name)) {
				url = properties.getProperty(name);
			}
		} catch (IOException e) {
			log.info(e.getMessage());
			logUtil.info(e.getMessage());
		}
		properties = null;
		return url;
	}

	/**
	 * 获取json内容
	 * 
	 * @param ORDER_ID
	 *            订单id
	 * @param ScanningWay
	 *            扫描方式（暂时无用）
	 * @param date
	 *            日期
	 * @return
	 */
	public static void getJson(String ORDER_ID, String ScanningWay, String date) {

		String json = null;
		// json = getStr(readProperties("url"), ORDER_ID);
		// json="{\"areaCode\":\"\",\"configAbilities\":[],\"configNetworks\":[{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"gateway\":\"\",\"id\":491,\"lifeOrder\":\"\",\"networkName\":\"123\",\"physicalSafe\":null,\"physicalSafeId\":\"\",\"ports\":\"8080\",\"resourceId\":\"89b24ed4-9a28-4ad6-ae41-a7088fb66935\",\"resourceStatus\":\"06\",\"useStatus\":0,\"userId\":\"\",\"vlanId\":0}],\"configPhysicalSafes\":[],\"configStorages\":[{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"displayName\":\"ceshi3_sw_2\",\"email\":\"\",\"id\":1184,\"lifeOrder\":\"\",\"resourceId\":\"769fd869ca5f452484d5bef470502bcd\",\"resourceStatus\":\"06\",\"safeType\":\"unsafe\",\"storageSize\":5,\"userId\":\"AP000264\"}],\"configVms\":[{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"cpunumber\":1,\"disk\":\"40\",\"flavorId\":\"\",\"id\":2772,\"imageId\":\"6251c407-3abf-4ce8-a7f4-0046148e72ea\",\"imageName\":\"CentOS\",\"ip\":\"10.2.32.38\",\"lifeOrder\":\"\",\"memory\":\"512\",\"middlewareId\":\"Tomcat\",\"middlewareName\":\"\",\"name\":\"1230\",\"networkId\":\"89b24ed4-9a28-4ad6-ae41-a7088fb66935\",\"networkName\":\"123\",\"password\":\"\",\"resourceId\":\"ef65d89c-b345-4d3b-864c-0610b060f5eb\",\"resourceStatus\":\"06\",\"useStatus\":1,\"userAccount\":\"\",\"vmRole\":\"123\"}],\"containerConfigId\":1540,\"containerName\":\"ceshi3\",\"containerTypeId\":\"ContainnerSaleManage\",\"createTime\":{\"date\":7,\"day\":3,\"hours\":0,\"minutes\":0,\"month\":4,\"nanos\":0,\"seconds\":0,\"time\":1399392000000,\"timezoneOffset\":-480,\"year\":114},\"destroyTime\":null,\"edition\":\"1\",\"editionStatus\":0,\"orderCode\":\"1494140507000118\",\"orderCount\":2,\"pid\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"pool\":\"TJ\",\"statusCode\":\"06\",\"user\":null,\"userId\":\"AP000264\",\"userName\":\"ceshi3\"}";
		json = "{\"areaCode\":\"\",\"configAbilities\":[],\"configNetworks\":[{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"gateway\":\"\",\"id\":491,\"lifeOrder\":\"\",\"networkName\":\"123\",\"physicalSafe\":null,\"physicalSafeId\":\"\",\"ports\":\"8080\",\"resourceId\":\"89b24ed4-9a28-4ad6-ae41-a7088fb66935\",\"resourceStatus\":\"06\",\"useStatus\":0,\"userId\":\"\",\"vlanId\":0}],\"configPhysicalSafes\":[],\"configStorages\":[{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"displayName\":\"ceshi3_sw_2\",\"email\":\"\",\"id\":1184,\"lifeOrder\":\"\",\"resourceId\":\"769fd869ca5f452484d5bef470502bcd\",\"resourceStatus\":\"06\",\"safeType\":\"unsafe\",\"storageSize\":5,\"userId\":\"AP000264\"}],\"configVms\":[{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"cpunumber\":1,\"disk\":\"40\",\"flavorId\":\"\",\"id\":2772,\"imageId\":\"6251c407-3abf-4ce8-a7f4-0046148e72ea\",\"imageName\":\"CentOS\",\"ip\":\"10.2.32.38\",\"lifeOrder\":\"\",\"memory\":\"512\",\"middlewareId\":\"Tomcat\",\"middlewareName\":\"\",\"name\":\"1230\",\"networkId\":\"89b24ed4-9a28-4ad6-ae41-a7088fb66935\",\"networkName\":\"123\",\"password\":\"\",\"resourceId\":\"ef65d89c-b345-4d3b-864c-0610b060f5eb\",\"resourceStatus\":\"06\",\"useStatus\":1,\"userAccount\":\"\",\"vmRole\":\"123\"},{\"containerId\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"cpunumber\":1,\"disk\":\"40\",\"flavorId\":\"\",\"id\":2772,\"imageId\":\"6251c407-3abf-4ce8-a7f4-0046148e72ea\",\"imageName\":\"CentOS\",\"ip\":\"10.2.32.38\",\"lifeOrder\":\"\",\"memory\":\"512\",\"middlewareId\":\"Tomcat\",\"middlewareName\":\"\",\"name\":\"1230\",\"networkId\":\"89b24ed4-9a28-4ad6-ae41-a7088fb66935\",\"networkName\":\"123\",\"password\":\"\",\"resourceId\":\"ef65d89c-b345-4d3b-864c-0610b060f5eb\",\"resourceStatus\":\"06\",\"useStatus\":1,\"userAccount\":\"\",\"vmRole\":\"123\"}],\"containerConfigId\":1540,\"containerName\":\"ceshi3\",\"containerTypeId\":\"ContainnerSaleManage\",\"createTime\":{\"date\":7,\"day\":3,\"hours\":0,\"minutes\":0,\"month\":4,\"nanos\":0,\"seconds\":0,\"time\":1399392000000,\"timezoneOffset\":-480,\"year\":114},\"destroyTime\":null,\"edition\":\"1\",\"editionStatus\":0,\"orderCode\":\"1494140507000118\",\"orderCount\":2,\"pid\":\"e40f33a37dc74ebcb9d4e8d7105631c7\",\"pool\":\"TJ\",\"statusCode\":\"06\",\"user\":null,\"userId\":\"AP000264\",\"userName\":\"ceshi3\"}";
		log.info(json);
		logUtil.info(json);

		String productNo = "";

		try {
			if ("".equals(json) || null == json) {
				return;
			}
			String code = json.substring(2, 6);
			if ("code".equals(code)) {
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			logUtil.info(e.getMessage());
			return;
		}
		JSONArray jarray = JSONArray.fromObject("[" + json + "]");
		String orderCode = ""; // 订单编号
		String configVms = ""; // 虚拟机资源----json
		String configNetworks = ""; // 网络资源----json
		String configAbilities = "";// 能力资源
		String configStorages = "";// 对象存储资源
		String configPhysicalSafes = "";// 物理机资源
		for (Object obj : jarray) {
			JSONObject job = JSONObject.fromObject(obj);
			// containerConfigId = job.get("containerConfigId") + "";
			// containerName = job.get("containerName") + "";
			// containerTypeId = job.get("containerTypeId") + "";
			orderCode = job.get("orderCode") + "";
			// 能力资源
			configAbilities = job.get("configAbilities") + "";
			commonResloveJson(configAbilities, orderCode, date, productNo,
					"NL_NO");
			// 虚拟机资源
			configVms = job.get("configVms") + "";
			commonResloveJson(configVms, orderCode, date, productNo, "XNJ_NO");
			// 网络资源
			configNetworks = job.get("configNetworks") + "";
			commonResloveJson(configNetworks, orderCode, date, productNo,
					"WL_NO");
			// 对象存储资源
			configStorages = job.get("configStorages") + "";
			commonResloveJson(configStorages, orderCode, date, productNo,
					"DXCC_NO");
			// 物理机资源
			configPhysicalSafes = job.get("configPhysicalSafes") + "";
			commonResloveJson(configPhysicalSafes, orderCode, date, productNo,
					"WLJ_NO");

		}
		// -----------------------清空内存占用----------------------
		orderCode = null; // 订单编号
		configVms = null; // 虚拟机资源----json
		configNetworks = null; // 网络资源----json
		configAbilities = null;// 能力资源
		configStorages = null;// 对象存储资源
		configPhysicalSafes = null;// 物理机资源

	}

	/**
	 * 公共解析业务容器引擎json方法
	 * 
	 * @param resourceGroupName
	 *            资源组名称
	 * @param orderCode
	 *            订单编号
	 * @param date
	 *            日期
	 * @param productNo
	 *            产品编号
	 * @param resourceGroupNo
	 *            资源组编号
	 * @author whn
	 */
	public static void commonResloveJson(String resourceGroupName,
			String orderCode, String date, String productNo,
			String resourceGroupNo) {
		if (!"".equals(resourceGroupName)) {
			JSONArray array = JSONArray.fromObject(resourceGroupName);
			for (int i = 0; i < array.size(); i++) {
				String jsonElement = array.getString(i);
				JSONArray jsonArray = JSONArray.fromObject("[" + jsonElement
						+ "]");
				for (Object obj : jsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					String resourceId = jsonObject.get("id") + "";
					List<HashMap<String, Object>> list = getResourceById(resourceGroupNo);
					String selTableName = "";
					String selBuswdNo = "";
					String selCountValue = "";
					String selScanningWay = "";
					String selEstwdValue = "";
					String selSperiod = "";
					String selStartTime = "";
					String selEndtime = "";
					// String selMaxEstValue = "";
					String selAvgEstValue = "";
					if (list != null && list.size() > 0) {
						for (HashMap<String, Object> map : list) {
							selTableName = map.get("SEL_TABLE_NAME") + ""; // 查询表名称
							selBuswdNo = map.get("SEL_BUSWD_NO") + ""; // 维度编号
							selCountValue = map.get("SEL_COUNT_VALUE") + ""; // 累计量
							selScanningWay = map.get("SCANNING_WAY") + ""; // 扫描方式
							selEstwdValue = map.get("SEL_ESTWD_VALUE") + ""; // 计量维度使用量字段
							selSperiod = map.get("SEL_SPERIOD") + ""; // 计量时间间隔字段
							selStartTime = map.get("SEL_START_TIME") + ""; // 开始时间
							selEndtime = map.get("SEL_END_TIME") + ""; // 结束时间
							// selMaxEstValue = map.get("SEL_ESTWD_NO_MAX") +
							// "";
							selAvgEstValue = map.get("SEL_ESTWD_NO_AVG") + "";

							List<HashMap<String, Object>> tableList = getTableByResAndOrder(
									selTableName, resourceId, orderCode, date,
									selEstwdValue, selSperiod, selStartTime,
									selEndtime, selAvgEstValue);
							// 执行插入操作
							if (tableList != null && tableList.size() > 0) {
								insertScanning(selBuswdNo, selCountValue,
										selScanningWay, orderCode, productNo,
										resourceId, tableList, selEstwdValue,
										selSperiod, selStartTime, selEndtime);
							}
							tableList = null;
						}
						selTableName = null;
						selBuswdNo = null;
						selCountValue = null;
						selScanningWay = null;
						selEstwdValue = null;
						selSperiod = null;
						selStartTime = null;
						selEndtime = null;
						selAvgEstValue = null;
						list = null;
					}

				}
			}
		}
	}

	/**
	 * 
	 * @param orderId
	 * @param productNo
	 * @param resourceId
	 * @param tableList
	 */
	public static void insertScanning(String selBuswdNo, String selCountValue,
			String selScanningWay, String orderId, String productNo,
			String resourceId, List<HashMap<String, Object>> tableList,
			String selEstwdValue, String selSperiod, String selStartTime,
			String selEndtime) {
		// 表(T_SCANNING_ADD_TOTAL)包含字段
		// (主键id,订单编号,产品编号,资源编号,维度名称,维度编号,维度的当前累积量,扫描方式,开始时间,结束时间,创建日期,更新日期)
		String sql = "INSERT INTO T_SCANNING_ADD_TOTAL SET ID=?, ORDER_ID=? , PRODUCT_ID=?, RESOURCE_ID=?, WD_NAME=?, WD_NO=?, WD_ADD_TOTAL=?, CURRENT_ADD_TOTAL=?, TIME_SPERIOD=?, START_TIME=?, END_TIME=?, CREATE_TIME=?, UPDATE_TIME=?, SCANNING_WAY=?";
		for (HashMap<String, Object> map : tableList) {
			Common comm = new Common();
			// tableList 表 字段包括(objectid, oname, packid, customid, starttime,
			// endtime, speriod, avapent, vartype, value)
			try {
				/*
				 * //根据维度编号 获得 维度名称 String itemID = map.get("vartype")+"";
				 * List<HashMap<String, Object>> ItemIDList = new
				 * GetAllRecords().getPriceByItemId(itemID, ""); String itemName
				 * = ItemIDList.get(0).get("ITEM_NAME")+"";
				 */
				List<String> params = new ArrayList<String>();
				params.add(comm.getStrSSS()); // 主键id (随机)
				params.add(orderId); // 订单编号
				params.add(productNo); // 产品编号
				params.add(resourceId); // 资源编号
				params.add(""); // 维度名称
				params.add(selBuswdNo); // 维度编号
				params.add(map.get(selEstwdValue) + ""); // 维度使用量
				params.add("null".equals(selCountValue) ? "0" : selCountValue); // 维度的累积量
				params.add(map.get(selSperiod) + ""); // 扫描方式(已经改为 时间间隔)
				params.add(map.get(selStartTime) + ""); // 开始时间
				params.add(map.get(selEndtime) + ""); // 结束时间
				params.add(comm.getDateTimeSSS()); // 创建日期
				params.add(comm.getDateTimeSSS()); // 更新日期
				params.add("1440".equals(map.get(selSperiod) + "") ? "1" : "5"); // 扫描方式
				int i = billing.doSaveOrUpdate(sql, params);
				if (i > 0) {
					log.info("insert success " + "\n sql = " + sql);
				} else {
					log.info("insert fail");
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				logUtil.error(e.getMessage());
			}
		}
	}

	/**
	 * 根据 资源编码和订单编号查询 传入的表的数据
	 * 
	 * @param tableName
	 * @param resourceCode
	 * @param orderId
	 * @return
	 */
	public static List<HashMap<String, Object>> getTableByResAndOrder(
			String selTableName, String resourceID, String orderCode,
			String date, String selEstwdValue, String selSperiod,
			String selStartTime, String selEndtime, String selAvgEstValue) {
		String newDate = date.replace('-', '/');
		// 要查的字段 （维度使用量，时间间隔，开始时间，结束时间）
		String sql = "SELECT "
				+ selEstwdValue
				+ ","
				+ selSperiod
				+ ","
				+ selStartTime
				+ ","
				+ selEndtime
				+ " FROM "
				+ selTableName
				+ " WHERE objectid=? AND orderid=? AND vartype=? AND starttime < '"
				+ newDate + "' AND endtime >= '" + newDate + "'"; // packid
																	// --订单编号
		List<String> params = new ArrayList<String>();
		params.add(resourceID);
		params.add(orderCode);
		params.add(selAvgEstValue);
		List<HashMap<String, Object>> list = metering.doSelect(sql, params);
		if (list != null && list.size() > 0) {
			log.info("根据 资源编码和订单编号查询 传入的表的数据 " + list.size() + "\nsql = " + sql);
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 根据 资源组id 查询 计费与计量数据配置表
	 * 
	 * @param id
	 * @return
	 */
	public static List<HashMap<String, Object>> getResourceById(String id) {
		BillingBaseDao billing = new BillingBaseDao();
		String sql = "SELECT SEL_TABLE_NAME,SEL_PACK_ID,SEL_ESTWD_VALUE,SEL_TABLE_RENO_EST,SEL_WDENG_NAME,SEL_START_TIME,SEL_END_TIME,SEL_SPERIOD,SCANNING_WAY,SEL_BUSWD_NO,SEL_ESTWD_NO_MAX,SEL_ESTWD_NO_AVG FROM T_ESTIMATE_CONFIG WHERE RESOURCE_GROUP_ID=? ";
		List<String> params = new ArrayList<String>();
		params.add(id);
		List<HashMap<String, Object>> l = billing.doSelect(sql, params);
		if (l.size() > 0) {
			return l;
		}
		l = null;
		return l;
	}

	public static String getStr(String url, String orderCode) {
		String resultStr = "";
		String str = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url + orderCode);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				str = getStringFromHttp(response.getEntity());
			}
			resultStr = new String(str.getBytes(), "UTF-8");
		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
			logUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			logUtil.error(e.getMessage());
		}
		return resultStr;
	}

	public static String getStringFromHttp(HttpEntity entity) throws Exception {
		StringBuffer buffer = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			isr = new InputStreamReader(entity.getContent(), "GBK");
			reader = new BufferedReader(isr);
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		/*
		 * String json = "{\"code\",\"other\"}"; String code = json.substring(2,
		 * 6); System.out.println(code);
		 */
		/*
		 * new BillingCumulative();
		 * BillingCumulative.getJson("1494140507000118", "1",
		 * "2014/05/06 12:00:00");
		 */
	}
}

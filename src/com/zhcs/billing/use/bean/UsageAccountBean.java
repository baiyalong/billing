package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 使用量资费表Bean
 * 
 * @author yuqingchao
 *
 */
public class UsageAccountBean {

	private String COMPLETE_STATE; // 扫描状态
	private String CUSTOMER_ID; // 客户编号
	private String SCANNING_WAY; // 扫描方式
	private String ORDER_ID; // 订单编号
	private String PRODUCT_ID; // 产品编号
	private String SCANNING_TIME_POINT; // 扫描时间点
	private int PRODUCT_CATEGORY; // 产品大类
	private String STATE; // 状态
	private int SERVERS_NO_STATE; // 服务器编号状态

	public String getCOMPLETE_STATE() {
		return COMPLETE_STATE;
	}

	public void setCOMPLETE_STATE(String cOMPLETESTATE) {
		COMPLETE_STATE = cOMPLETESTATE;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMERID) {
		CUSTOMER_ID = cUSTOMERID;
	}

	public String getSCANNING_WAY() {
		return SCANNING_WAY;
	}

	public void setSCANNING_WAY(String sCANNINGWAY) {
		SCANNING_WAY = sCANNINGWAY;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public int getSERVERS_NO_STATE() {
		return SERVERS_NO_STATE;
	}

	public void setSERVERS_NO_STATE(int sERVERSNOSTATE) {
		SERVERS_NO_STATE = sERVERSNOSTATE;
	}

	public void setPRODUCT_CATEGORY(int pRODUCT_CATEGORY) {
		PRODUCT_CATEGORY = pRODUCT_CATEGORY;
	}

	public int getPRODUCT_CATEGORY() {
		return PRODUCT_CATEGORY;
	}

	/**
	 * 将返回结果转成UsageAccountBean列表对象
	 * 
	 * @param list
	 * @return
	 */
	public List<UsageAccountBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<UsageAccountBean> l = new ArrayList<UsageAccountBean>();
		for (HashMap<String, Object> d : list) {
			UsageAccountBean usageAccountBean = new UsageAccountBean();
			usageAccountBean.setCUSTOMER_ID(d.get("CUSTOMER_ID") != null ? d
					.get("CUSTOMER_ID").toString() : null);
			usageAccountBean.setORDER_ID(d.get("ORDER_ID") != null ? d.get(
					"ORDER_ID").toString() : null);
			usageAccountBean
					.setCOMPLETE_STATE(d.get("COMPLETE_STATE") != null ? d.get(
							"COMPLETE_STATE").toString() : null);
			usageAccountBean.setSCANNING_TIME_POINT(d
					.get("SCANNING_TIME_POINT") != null ? d.get(
					"SCANNING_TIME_POINT").toString() : null);
			usageAccountBean.setSCANNING_WAY(d.get("SCANNING_WAY") != null ? d
					.get("SCANNING_WAY").toString() : null);
			usageAccountBean
					.setSERVERS_NO_STATE(d.get("SERVERS_NO_STATE") != null ? Integer
							.parseInt(d.get("SERVERS_NO_STATE").toString())
							: null);
			usageAccountBean.setSTATE(d.get("STATE") != null ? d.get("STATE")
					.toString() : null);
			l.add(usageAccountBean);
		}
		return l;
	}

	public void setSCANNING_TIME_POINT(String sCANNING_TIME_POINT) {
		SCANNING_TIME_POINT = sCANNING_TIME_POINT;
	}

	public String getSCANNING_TIME_POINT() {
		return SCANNING_TIME_POINT;
	}

}

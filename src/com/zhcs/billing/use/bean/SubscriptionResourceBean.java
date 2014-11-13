package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Éê¹º×ÊÔ´±í
 * @author yanglili
 * 
 */
public class SubscriptionResourceBean {

	public SubscriptionResourceBean() {
	}

	private String SR_ID;// Éê¹º×ÊÔ´±àºÅ
	private String SUBSCRIBER_ID;// Éê¹º±àºÅ
	private String PRODUCT_ID;// ²úÆ·±àºÅ
	private String RESOURCE_ID;// ×ÊÔ´±àºÅ
	private String SP_ID;// Éê¹ºÌ×²Í±àºÅ
	private String C_RESOURCE_ID;// ÈÝÆ÷×ÊÔ´±àºÅ
	
	public SubscriptionResourceBean(String sRID, String sUBSCRIBERID,
			String pRODUCTID, String rESOURCEID, String sPID, String cRESOURCEID) {
		super();
		SR_ID = sRID;
		SUBSCRIBER_ID = sUBSCRIBERID;
		PRODUCT_ID = pRODUCTID;
		RESOURCE_ID = rESOURCEID;
		SP_ID = sPID;
		C_RESOURCE_ID = cRESOURCEID;
	}
	
	public String getSR_ID() {
		return SR_ID;
	}
	public void setSR_ID(String sRID) {
		SR_ID = sRID;
	}
	public String getSUBSCRIBER_ID() {
		return SUBSCRIBER_ID;
	}
	public void setSUBSCRIBER_ID(String sUBSCRIBERID) {
		SUBSCRIBER_ID = sUBSCRIBERID;
	}
	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(String pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}
	public String getRESOURCE_ID() {
		return RESOURCE_ID;
	}
	public void setRESOURCE_ID(String rESOURCEID) {
		RESOURCE_ID = rESOURCEID;
	}
	public String getSP_ID() {
		return SP_ID;
	}
	public void setSP_ID(String sPID) {
		SP_ID = sPID;
	}
	public String getC_RESOURCE_ID() {
		return C_RESOURCE_ID;
	}
	public void setC_RESOURCE_ID(String cRESOURCEID) {
		C_RESOURCE_ID = cRESOURCEID;
	}
	
	public List<SubscriptionResourceBean> changeToObject(List<HashMap<String, Object>> list){
		List<SubscriptionResourceBean> beans = new ArrayList<SubscriptionResourceBean>();
		for (int i = 0; i < list.size(); i++) {
			SubscriptionResourceBean bean = new SubscriptionResourceBean();
			
			bean.setSR_ID(list.get(i).get("SR_ID")!=null?list.get(i).get("SR_ID").toString():null);       
			bean.setSUBSCRIBER_ID(list.get(i).get("SUBSCRIBER_ID")!=null?list.get(i).get("SUBSCRIBER_ID").toString():null);       
			bean.setPRODUCT_ID(list.get(i).get("PRODUCT_ID")!=null?list.get(i).get("PRODUCT_ID").toString():null);       
			bean.setRESOURCE_ID(list.get(i).get("RESOURCE_ID")!=null?list.get(i).get("RESOURCE_ID").toString():null);       
			bean.setSP_ID(list.get(i).get("SP_ID")!=null?list.get(i).get("SP_ID").toString():null);       
			bean.setC_RESOURCE_ID(list.get(i).get("C_RESOURCE_ID")!=null?list.get(i).get("C_RESOURCE_ID").toString():null); 
			beans.add(bean);
		}
		return beans;
	}
}

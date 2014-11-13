package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 资费月租中间表Bean
 * @author yuqingchao
 *
 */
public class UsageMonthsBean {

	private String BILLSEQ_ID;		//账期
	private String CUSTOMER_ID;		//客户编号
	private Integer FIXED_OR_MONTHLY;	//0：按天扣，1：月初扣
	private String SCANNING_WAY;	//扫描方式
	private String UNIT_RATE;		//月扣金额
	private String ORDER_ID;		//订单编号
	private String PRODUCT_ID;		//产品编号
	private String PRODUCT_TYPE;	//产品类型
	private Integer PRODUCT_CATEGORY;	//产品大类（1：容器类 2：应用类）
	private String COMPLETE_STATE;	//扫描状态
	private String STATE;			//状态
	private Integer SERVERS_NO_STATE;//服务器编号状态
	
	public String getUNIT_RATE() {
		return UNIT_RATE;
	}
	public void setUNIT_RATE(String uNITRATE) {
		UNIT_RATE = uNITRATE;
	}
	public Integer getFIXED_OR_MONTHLY() {
		return FIXED_OR_MONTHLY;
	}
	public void setFIXED_OR_MONTHLY(Integer fIXEDORMONTHLY) {
		FIXED_OR_MONTHLY = fIXEDORMONTHLY;
	}
	public String getCOMPLETE_STATE() {
		return COMPLETE_STATE;
	}
	public void setCOMPLETE_STATE(String cOMPLETESTATE) {
		COMPLETE_STATE = cOMPLETESTATE;
	}
	public String getBILLSEQ_ID() {
		return BILLSEQ_ID;
	}
	public void setBILLSEQ_ID(String bILLSEQID) {
		BILLSEQ_ID = bILLSEQID;
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
	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}
	public void setPRODUCT_TYPE(String pRODUCTTYPE) {
		PRODUCT_TYPE = pRODUCTTYPE;
	}
	public Integer getPRODUCT_CATEGORY() {
		return PRODUCT_CATEGORY;
	}
	public void setPRODUCT_CATEGORY(Integer pRODUCTCATEGORY) {
		PRODUCT_CATEGORY = pRODUCTCATEGORY;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public Integer getSERVERS_NO_STATE() {
		return SERVERS_NO_STATE;
	}
	public void setSERVERS_NO_STATE(Integer sERVERSNOSTATE) {
		SERVERS_NO_STATE = sERVERSNOSTATE;
	}
	
	/**
	 * 将返回结果转成UsageMonthsBean列表对象
	 * @param list
	 * @return
	 */
	public List<UsageMonthsBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<UsageMonthsBean> l = new ArrayList<UsageMonthsBean>();
		for(HashMap<String,Object> d:list){
			UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
			usageMonthsBean.setBILLSEQ_ID(d.get("BILLSEQ_ID")!=null?d.get("BILLSEQ_ID").toString():null);
			usageMonthsBean.setCUSTOMER_ID(d.get("CUSTOMER_ID")!=null?d.get("CUSTOMER_ID").toString():null);
			usageMonthsBean.setFIXED_OR_MONTHLY(d.get("FIXED_OR_MONTHLY")!=null?Integer.parseInt(d.get("FIXED_OR_MONTHLY").toString()):null);
			usageMonthsBean.setSCANNING_WAY(d.get("SCANNING_WAY")!=null?d.get("SCANNING_WAY").toString():null);
			usageMonthsBean.setUNIT_RATE(d.get("UNIT_RATE")!=null?d.get("UNIT_RATE").toString():null);
			usageMonthsBean.setORDER_ID(d.get("ORDER_ID")!=null?d.get("ORDER_ID").toString():null);
			usageMonthsBean.setPRODUCT_ID(d.get("PRODUCT_ID")!=null?d.get("PRODUCT_ID").toString():null);
			usageMonthsBean.setPRODUCT_TYPE(d.get("PRODUCT_TYPE")!=null?d.get("PRODUCT_TYPE").toString():null);
			usageMonthsBean.setCOMPLETE_STATE(d.get("COMPLETE_STATE")!=null?d.get("COMPLETE_STATE").toString():null);
			usageMonthsBean.setSTATE(d.get("STATE")!=null?d.get("STATE").toString():null);
			usageMonthsBean.setSERVERS_NO_STATE(d.get("SERVERS_NO_STATE")!=null?Integer.parseInt(d.get("SERVERS_NO_STATE").toString()):null);
			usageMonthsBean.setPRODUCT_CATEGORY(d.get("PRODUCT_CATEGORY")!=null?Integer.parseInt(d.get("PRODUCT_CATEGORY").toString()):null);
			l.add(usageMonthsBean);
		}
		return l;
	}
	
}

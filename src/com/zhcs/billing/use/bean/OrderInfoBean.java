package com.zhcs.billing.use.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 订单表
 * 
 * @author hefa
 * 
 */
public class OrderInfoBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderInfoBean() {

	}

	public OrderInfoBean(String oRDERID, String cUSTOMERID, String sERIALNO,
			Integer aMOUNT, Date cREATETIME, String cREATORID,
			Integer cREATORTYPE, String aUDITERID, String aUDITERTYPE,
			Date aUDITTIME, Integer oRDERSTATUS, String sYNCFLAG,
			int pRODUCT_CATEGORY) {
		super();
		ORDER_ID = oRDERID;
		CUSTOMER_ID = cUSTOMERID;
		SERIAL_NO = sERIALNO;
		AMOUNT = aMOUNT;
		CREATE_TIME = cREATETIME;
		CREATOR_ID = cREATORID;
		CREATOR_TYPE = cREATORTYPE;
		AUDITER_ID = aUDITERID;
		AUDITER_TYPE = aUDITERTYPE;
		AUDIT_TIME = aUDITTIME;
		ORDER_STATUS = oRDERSTATUS;
		SYNC_FLAG = sYNCFLAG;
		PRODUCT_CATEGORY = pRODUCT_CATEGORY;
	}

	private static final Integer dict = 1;// 节点类型

	public static Integer getDict() {
		return dict;
	}

	private String ORDER_ID;// 订单编号
	private String CUSTOMER_ID;// 客户编号
	private String SERIAL_NO;// 交易流水号
	private Integer AMOUNT;// 订单总金额
	private Date CREATE_TIME;// 创建时间
	private String CREATOR_ID;// 订单创建人
	private Integer CREATOR_TYPE;// 创建人类型
	private String AUDITER_ID;// 审批人
	private String AUDITER_TYPE;// 审批人类型
	private Date AUDIT_TIME;// 审批时间
	private Integer ORDER_STATUS;// 订单状态
	private String SYNC_FLAG;// 同步标识
	private int PRODUCT_CATEGORY;// 产品大类

	private int MONEY;// 标准
	private int pattern;// 资费模型
	private int reality;// 实际

	private java.sql.Timestamp BILLING_TIME;// 最后一次结算时间
	private String ACCOUNT_CODE;// 账本编号
	private String CONTAINER_ID;// 容器ID

	private String BOOK_ID;
	private String PROVINCE_CODE;
	private String AREA_CODE;
	private String ACCOUNT_ID;
	private long PRICE;
	private long BALANCE;

	public String getACCOUNT_CODE() {
		return ACCOUNT_CODE;
	}

	public void setACCOUNT_CODE(String ac) {
		ACCOUNT_CODE = ac;
	}

	public java.sql.Timestamp getBILLING_TIME() {
		return BILLING_TIME;
	}

	public void setBILLING_TIME(java.sql.Timestamp bt) {
		BILLING_TIME = bt;
	}

	private List<OrderDetailBean> orderDetailBeans = new ArrayList<OrderDetailBean>();// 订单详细表

	public List<OrderDetailBean> getOrderDetailBeans() {
		return orderDetailBeans;
	}

	public void setOrderDetailBeans(List<OrderDetailBean> orderDetailBeans) {
		this.orderDetailBeans = orderDetailBeans;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMERID) {
		CUSTOMER_ID = cUSTOMERID;
	}

	public String getSERIAL_NO() {
		return SERIAL_NO;
	}

	public void setSERIAL_NO(String sERIALNO) {
		SERIAL_NO = sERIALNO;
	}

	public Integer getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(Integer aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public Date getcREATE_TIME() {
		return CREATE_TIME;
	}

	public void setcREATE_TIME(Date cREATETIME) {
		CREATE_TIME = cREATETIME;
	}

	public String getCREATOR_ID() {
		return CREATOR_ID;
	}

	public void setCREATOR_ID(String cREATORID) {
		CREATOR_ID = cREATORID;
	}

	public Integer getCREATOR_TYPE() {
		return CREATOR_TYPE;
	}

	public void setCREATOR_TYPE(Integer cREATORTYPE) {
		CREATOR_TYPE = cREATORTYPE;
	}

	public String getAUDITER_ID() {
		return AUDITER_ID;
	}

	public void setAUDITER_ID(String aUDITERID) {
		AUDITER_ID = aUDITERID;
	}

	public String getAUDITER_TYPE() {
		return AUDITER_TYPE;
	}

	public void setAUDITER_TYPE(String aUDITERTYPE) {
		AUDITER_TYPE = aUDITERTYPE;
	}

	public Date getAUDIT_TIME() {
		return AUDIT_TIME;
	}

	public void setAUDIT_TIME(Date aUDITTIME) {
		AUDIT_TIME = aUDITTIME;
	}

	public Integer getORDER_STATUS() {
		return ORDER_STATUS;
	}

	public void setORDER_STATUS(Integer oRDERSTATUS) {
		ORDER_STATUS = oRDERSTATUS;
	}

	public String getSYNC_FLAG() {
		return SYNC_FLAG;
	}

	public void setSYNC_FLAG(String sYNCFLAG) {
		SYNC_FLAG = sYNCFLAG;
	}

	public int getMONEY() {
		return MONEY;
	}

	public void setMONEY(int mONEY) {
		MONEY = mONEY;
	}

	public int getPattern() {
		return pattern;
	}

	public void setPattern(int pattern) {
		this.pattern = pattern;
	}

	public int getReality() {
		return reality;
	}

	public void setReality(int reality) {
		this.reality = reality;
	}

	public int getPRODUCT_CATEGORY() {
		return PRODUCT_CATEGORY;
	}

	public void setPRODUCT_CATEGORY(int pRODUCT_CATEGORY) {
		PRODUCT_CATEGORY = pRODUCT_CATEGORY;
	}

	/**
	 * 将返回的结果转换成OrderInfoBean集合
	 * 
	 * @param list
	 * @return
	 */
	public static List<OrderInfoBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<OrderInfoBean> l = new ArrayList<OrderInfoBean>();
		for (HashMap<String, Object> order : list) {
			OrderInfoBean orderInfoBean = new OrderInfoBean();
			orderInfoBean.setORDER_ID(order.get("ORDER_ID") != null ? order
					.get("ORDER_ID").toString() : null);
			orderInfoBean
					.setCUSTOMER_ID(order.get("CUSTOMER_ID") != null ? order
							.get("CUSTOMER_ID").toString() : null);
			orderInfoBean.setORDER_STATUS(Integer.parseInt(order
					.get("ORDER_STATUS") != null ? order.get("ORDER_STATUS")
					.toString() : null));
			orderInfoBean.setPRODUCT_CATEGORY(Integer.parseInt(order
					.get("PRODUCT_CATEGORY") != null ? order.get(
					"PRODUCT_CATEGORY").toString() : null));

			orderInfoBean.setAMOUNT((Integer) order.get("AMOUNT"));
			orderInfoBean
					.setBILLING_TIME((Timestamp) order.get("BILLING_TIME"));
			orderInfoBean.setACCOUNT_CODE((String) order.get("ACCOUNT_CODE"));
			orderInfoBean.setCONTAINER_ID((String) order.get("CONTAINER_ID"));
			l.add(orderInfoBean);
		}
		return l;
	}

	public String getCONTAINER_ID() {
		return CONTAINER_ID;
	}

	public void setCONTAINER_ID(String cONTAINER_ID) {
		CONTAINER_ID = cONTAINER_ID;
	}

	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public String getBOOK_ID() {
		return BOOK_ID;
	}

	public void setBOOK_ID(String bOOK_ID) {
		BOOK_ID = bOOK_ID;
	}

	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}

	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
	}

	public String getAREA_CODE() {
		return AREA_CODE;
	}

	public void setAREA_CODE(String aREA_CODE) {
		AREA_CODE = aREA_CODE;
	}

	public String getACCOUNT_ID() {
		return ACCOUNT_ID;
	}

	public void setACCOUNT_ID(String aCCOUNT_ID) {
		ACCOUNT_ID = aCCOUNT_ID;
	}

	public long getPRICE() {
		return PRICE;
	}

	public void setPRICE(long pRICE) {
		PRICE = pRICE;
	}

	public long getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(long bALANCE) {
		BALANCE = bALANCE;
	}

}

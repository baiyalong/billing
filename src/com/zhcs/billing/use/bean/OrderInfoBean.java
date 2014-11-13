package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ������
 * 
 * @author hefa
 * 
 */
public class OrderInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public OrderInfoBean() {

	}

	public OrderInfoBean(String oRDERID, String cUSTOMERID, String sERIALNO,
			Integer aMOUNT, Date cREATETIME, String cREATORID,
			Integer cREATORTYPE, String aUDITERID, String aUDITERTYPE,
			Date aUDITTIME, Integer oRDERSTATUS, String sYNCFLAG, int pRODUCT_CATEGORY) {
		super();
		ORDER_ID = oRDERID;
		CUSTOMER_ID = cUSTOMERID;
		SERIAL_NO = sERIALNO;
		AMOUNT = aMOUNT;
		cREATE_TIME = cREATETIME;
		CREATOR_ID = cREATORID;
		CREATOR_TYPE = cREATORTYPE;
		AUDITER_ID = aUDITERID;
		AUDITER_TYPE = aUDITERTYPE;
		AUDIT_TIME = aUDITTIME;
		ORDER_STATUS = oRDERSTATUS;
		SYNC_FLAG = sYNCFLAG;
		PRODUCT_CATEGORY = pRODUCT_CATEGORY;
	}
	private static final Integer dict =1;//�ڵ�����
	
	public static Integer getDict() {
		return dict;
	}
	
	private String ORDER_ID;// �������
	private String CUSTOMER_ID;// �ͻ����
	private String SERIAL_NO;// ������ˮ��
	private Integer AMOUNT;// �����ܽ��
	private Date cREATE_TIME;// ����ʱ��
	private String CREATOR_ID;// ����������
	private Integer CREATOR_TYPE;// ����������
	private String AUDITER_ID;// ������
	private String AUDITER_TYPE;// ����������
	private Date AUDIT_TIME;// ����ʱ��
	private Integer ORDER_STATUS;// ����״̬
	private String SYNC_FLAG;// ͬ����ʶ
	private int PRODUCT_CATEGORY;//��Ʒ����
	
	private int MONEY;//��׼
	private int pattern;//�ʷ�ģ��
	private int reality;//ʵ��
	
	private List<OrderDetailBean> orderDetailBeans = new ArrayList<OrderDetailBean>();//������ϸ��
	
	

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
		return cREATE_TIME;
	}

	public void setcREATE_TIME(Date cREATETIME) {
		cREATE_TIME = cREATETIME;
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
	 * �����صĽ��ת����OrderInfoBean����
	 * @param list
	 * @return
	 */
	public List<OrderInfoBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<OrderInfoBean> l = new ArrayList<OrderInfoBean>();
		for (HashMap<String, Object> order : list) {
			OrderInfoBean orderInfoBean = new OrderInfoBean();
			orderInfoBean.setORDER_ID(order.get("ORDER_ID")!=null?order.get("ORDER_ID").toString():null);
			orderInfoBean.setCUSTOMER_ID(order.get("CUSTOMER_ID")!=null?order.get("CUSTOMER_ID").toString():null);
			orderInfoBean.setORDER_STATUS(Integer.parseInt(order.get("ORDER_STATUS")!=null?order.get("ORDER_STATUS").toString():null));
			orderInfoBean.setPRODUCT_CATEGORY(Integer.parseInt(order.get("PRODUCT_CATEGORY")!=null?order.get("PRODUCT_CATEGORY").toString():null));
			l.add(orderInfoBean);
		}
		return l;
	}
}
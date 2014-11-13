package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 订单明细表
 * 
 * @author hefa
 * 
 */
public class OrderDetailBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1657728789157714458L;

	public OrderDetailBean() {
		// TODO Auto-generated constructor stub
	}

	public OrderDetailBean(String dETAILID, String oRDERID,
			String sUBSCRIBERID, String pRODUCTID, Integer sUMARY) {
		super();
		DETAIL_ID = dETAILID;
		ORDER_ID = oRDERID;
		SUBSCRIBER_ID = sUBSCRIBERID;
		PRODUCT_ID = pRODUCTID;
		SUMARY = sUMARY;
	}

	private ProductInfoBean productInfoBean;// 产品信息

	public ProductInfoBean getProductInfoBean() {
		return productInfoBean;
	}

	public void setProductInfoBean(ProductInfoBean productInfoBean) {
		this.productInfoBean = productInfoBean;
	}

	private String DETAIL_ID;// 明细编号
	private String ORDER_ID;// 订单编号
	private String SUBSCRIBER_ID;// 申购编号
	private String PRODUCT_ID;// 产品编号
	private Integer SUMARY;// 产品订购金额

	private double MONEY;// 一次批价
	private double pattern;// 二次批价


	public double getMONEY() {
		return MONEY;
	}

	public void setMONEY(double mONEY) {
		MONEY = mONEY;
	}

	public double getPattern() {
		return pattern;
	}

	public void setPattern(double pattern) {
		this.pattern = pattern;
	}


	public String getDETAIL_ID() {
		return DETAIL_ID;
	}

	public void setDETAIL_ID(String dETAILID) {
		DETAIL_ID = dETAILID;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
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

	public Integer getSUMARY() {
		return SUMARY;
	}

	public void setSUMARY(Integer sUMARY) {
		SUMARY = sUMARY;
	}

	public List<OrderDetailBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<OrderDetailBean> beans = new ArrayList<OrderDetailBean>();
		for (HashMap<String, Object> hashMap : list) {
			OrderDetailBean bean = new OrderDetailBean();
			bean.setDETAIL_ID(hashMap.get("DETAIL_ID") != null ? hashMap.get("DETAIL_ID").toString() : null);
			bean.setORDER_ID(hashMap.get("ORDER_ID") != null ? hashMap.get("ORDER_ID").toString() : null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get("PRODUCT_ID").toString() : null);
			bean.setSUBSCRIBER_ID(hashMap.get("SUBSCRIBER_ID") != null ? hashMap.get("SUBSCRIBER_ID").toString(): null);
			bean.setSUMARY(hashMap.get("SUMARY") != null ? Integer.parseInt(hashMap.get("SUMARY").toString()) : null);
			beans.add(bean);
		}
		return beans;

	}
}

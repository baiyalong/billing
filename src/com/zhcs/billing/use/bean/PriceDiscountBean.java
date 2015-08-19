package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 资费优惠策略表
 * 
 * @author hefa
 *
 */
public class PriceDiscountBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5075966950858885520L;

	public PriceDiscountBean() {
		// TODO Auto-generated constructor stub
	}

	public PriceDiscountBean(String dISCOUNT_ID, String rULE_ID,
			String pRODUCT_ID, String rULE_CODE, int mIN_VALUE, int mAX_VALUE,
			int dISCOUNT_VALUE, int rATE_VALUE, int dISCOUNT_STATUS) {
		super();
		DISCOUNT_ID = dISCOUNT_ID;
		RULE_ID = rULE_ID;
		PRODUCT_ID = pRODUCT_ID;
		RULE_CODE = rULE_CODE;
		MIN_VALUE = mIN_VALUE;
		MAX_VALUE = mAX_VALUE;
		DISCOUNT_VALUE = dISCOUNT_VALUE;
		RATE_VALUE = rATE_VALUE;
		DISCOUNT_STATUS = dISCOUNT_STATUS;
	}

	private String DISCOUNT_ID;// 优惠编号
	private String RULE_ID; // 规则编号
	private String PRODUCT_ID; // 产品编号
	private String RULE_CODE; // 规则编码
	private int MIN_VALUE; // 起始金额
	private int MAX_VALUE; // 截止金额
	private int DISCOUNT_VALUE;// 优惠金额
	private int RATE_VALUE; // 折扣率
	private int DISCOUNT_STATUS;// 状态

	public String getDISCOUNT_ID() {
		return DISCOUNT_ID;
	}

	public void setDISCOUNT_ID(String dISCOUNT_ID) {
		DISCOUNT_ID = dISCOUNT_ID;
	}

	public String getRULE_ID() {
		return RULE_ID;
	}

	public void setRULE_ID(String rULE_ID) {
		RULE_ID = rULE_ID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getRULE_CODE() {
		return RULE_CODE;
	}

	public void setRULE_CODE(String rULE_CODE) {
		RULE_CODE = rULE_CODE;
	}

	public int getMIN_VALUE() {
		return MIN_VALUE;
	}

	public void setMIN_VALUE(int mIN_VALUE) {
		MIN_VALUE = mIN_VALUE;
	}

	public int getMAX_VALUE() {
		return MAX_VALUE;
	}

	public void setMAX_VALUE(int mAX_VALUE) {
		MAX_VALUE = mAX_VALUE;
	}

	public int getDISCOUNT_VALUE() {
		return DISCOUNT_VALUE;
	}

	public void setDISCOUNT_VALUE(int dISCOUNT_VALUE) {
		DISCOUNT_VALUE = dISCOUNT_VALUE;
	}

	public int getRATE_VALUE() {
		return RATE_VALUE;
	}

	public void setRATE_VALUE(int rATE_VALUE) {
		RATE_VALUE = rATE_VALUE;
	}

	public int getDISCOUNT_STATUS() {
		return DISCOUNT_STATUS;
	}

	public void setDISCOUNT_STATUS(int dISCOUNT_STATUS) {
		DISCOUNT_STATUS = dISCOUNT_STATUS;
	}

	public List<PriceDiscountBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<PriceDiscountBean> beans = new ArrayList<PriceDiscountBean>();
		for (HashMap<String, Object> hashMap : list) {
			PriceDiscountBean bean = new PriceDiscountBean();
			bean.setDISCOUNT_ID(hashMap.get("DISCOUNT_ID") != null ? hashMap
					.get("DISCOUNT_ID").toString() : null);
			bean.setRULE_ID(hashMap.get("RULE_ID") != null ? hashMap.get(
					"RULE_ID").toString() : null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get(
					"PRODUCT_ID").toString() : null);
			bean.setRULE_CODE(hashMap.get("RULE_CODE") != null ? hashMap.get(
					"RULE_CODE").toString() : null);
			bean.setMIN_VALUE(hashMap.get("MIN_VALUE") != null ? Integer
					.parseInt(hashMap.get("MIN_VALUE").toString()) : null);
			bean.setMAX_VALUE(hashMap.get("MAX_VALUE") != null ? Integer
					.parseInt(hashMap.get("MAX_VALUE").toString()) : null);
			bean.setDISCOUNT_VALUE(hashMap.get("DISCOUNT_VALUE") != null ? Integer
					.parseInt(hashMap.get("DISCOUNT_VALUE").toString()) : null);
			bean.setRATE_VALUE(hashMap.get("RATE_VALUE") != null ? Integer
					.parseInt(hashMap.get("RATE_VALUE").toString()) : null);
			bean.setDISCOUNT_STATUS(hashMap.get("DISCOUNT_STATUS") != null ? Integer
					.parseInt(hashMap.get("DISCOUNT_STATUS").toString()) : null);
			beans.add(bean);
		}
		return beans;
	}

}

package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 产品套餐表
 * @author hefa
 *
 */
public class ProductPackgeBean implements java.io.Serializable {
	public ProductPackgeBean() {
		// TODO Auto-generated constructor stub
	}

	public ProductPackgeBean(String pACKAGE_ID, String pRODUCT_ID,
			String pACKAGE_NAME, Integer pACKAGE_PRICE, String vALID_FROM,
			String vALID_TILL, Integer pACKAGE_PERIOD, String eNABLE_SALE,
			String pACKAGE_STATUS) {
		super();
		PACKAGE_ID = pACKAGE_ID;
		PRODUCT_ID = pRODUCT_ID;
		PACKAGE_NAME = pACKAGE_NAME;
		PACKAGE_PRICE = pACKAGE_PRICE;
		VALID_FROM = vALID_FROM;
		VALID_TILL = vALID_TILL;
		PACKAGE_PERIOD = pACKAGE_PERIOD;
		ENABLE_SALE = eNABLE_SALE;
		PACKAGE_STATUS = pACKAGE_STATUS;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6660078646256803319L;
	
	private String PACKAGE_ID;//套餐编号
	private String PRODUCT_ID;//产品编号
	private String PACKAGE_NAME;//套餐名称
	private Integer PACKAGE_PRICE;//套餐资费
	private String VALID_FROM;//起始日期	
	private String VALID_TILL;//截止日期
	private Integer PACKAGE_PERIOD;//套餐周期
	private String ENABLE_SALE;//是否允许订购
	private String PACKAGE_STATUS;//状态
	
	public String getPACKAGE_ID() {
		return PACKAGE_ID;
	}
	public void setPACKAGE_ID(String pACKAGE_ID) {
		PACKAGE_ID = pACKAGE_ID;
	}
	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}
	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}
	public String getPACKAGE_NAME() {
		return PACKAGE_NAME;
	}
	public void setPACKAGE_NAME(String pACKAGE_NAME) {
		PACKAGE_NAME = pACKAGE_NAME;
	}
	public Integer getPACKAGE_PRICE() {
		return PACKAGE_PRICE;
	}
	public void setPACKAGE_PRICE(Integer pACKAGE_PRICE) {
		PACKAGE_PRICE = pACKAGE_PRICE;
	}
	public String getVALID_FROM() {
		return VALID_FROM;
	}
	public void setVALID_FROM(String vALID_FROM) {
		VALID_FROM = vALID_FROM;
	}
	public String getVALID_TILL() {
		return VALID_TILL;
	}
	public void setVALID_TILL(String vALID_TILL) {
		VALID_TILL = vALID_TILL;
	}
	public Integer getPACKAGE_PERIOD() {
		return PACKAGE_PERIOD;
	}
	public void setPACKAGE_PERIOD(Integer pACKAGE_PERIOD) {
		PACKAGE_PERIOD = pACKAGE_PERIOD;
	}
	public String getENABLE_SALE() {
		return ENABLE_SALE;
	}
	public void setENABLE_SALE(String eNABLE_SALE) {
		ENABLE_SALE = eNABLE_SALE;
	}
	public String getPACKAGE_STATUS() {
		return PACKAGE_STATUS;
	}
	public void setPACKAGE_STATUS(String pACKAGE_STATUS) {
		PACKAGE_STATUS = pACKAGE_STATUS;
	}
	
	public List<ProductPackgeBean> changeToObject(List<HashMap<String, Object>> list) {
		List<ProductPackgeBean> beans = new ArrayList<ProductPackgeBean>();
		for (HashMap<String, Object> hashMap : list) {
			ProductPackgeBean bean = new ProductPackgeBean();
			bean.setPACKAGE_ID(hashMap.get("PACKAGE_ID") != null ? hashMap.get("PACKAGE_ID").toString() : null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get("PRODUCT_ID").toString() : null);
			bean.setPACKAGE_NAME(hashMap.get("PACKAGE_NAME") != null ? hashMap.get("PACKAGE_NAME").toString() : null);
			bean.setPACKAGE_PERIOD(hashMap.get("PACKAGE_PERIOD") != null ? Integer.parseInt(hashMap.get("PACKAGE_PERIOD").toString()) : null);
			bean.setVALID_FROM(hashMap.get("VALID_FROM") != null ? hashMap.get("VALID_FROM").toString() : null);
			bean.setVALID_TILL(hashMap.get("VALID_TILL") != null ? hashMap.get("VALID_TILL").toString() : null);
			bean.setPACKAGE_PERIOD(hashMap.get("PACKAGE_PERIOD") != null ? Integer.parseInt(hashMap.get("PACKAGE_PERIOD").toString()) : null);
			bean.setENABLE_SALE(hashMap.get("ENABLE_SALE") != null ? hashMap.get("ENABLE_SALE").toString() : null);
			bean.setPACKAGE_STATUS(hashMap.get("PACKAGE_STATUS") != null ? hashMap.get("PACKAGE_STATUS").toString() : null);
			beans.add(bean);
		}
		return beans;
	}
	
}

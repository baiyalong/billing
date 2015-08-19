package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 套餐明细表
 * 
 * @author hefa
 *
 */
public class PackageDetailBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6918033493646738391L;

	private String PD_ID;// 套餐明细编号
	private String PACKAGE_ID;// 套餐编号
	private String ITEM_ID;// 维度编号
	private String RESOURCE_ID;// 资源编号
	private Integer ITEM_AMOUNT;// 包内数量
	private String MEASURE_UNIT;// 计量单位
	private Integer PRICE;// 超出单价
	private Integer DETAIL_STATUS;// 状态

	public PackageDetailBean() {
		// TODO Auto-generated constructor stub
	}

	public PackageDetailBean(String pD_ID, String pACKAGE_ID, String iTEM_ID,
			String rESOURCE_ID, Integer iTEM_AMOUNT, String mEASURE_UNIT,
			Integer pRICE, Integer dETAIL_STATUS) {
		super();
		PD_ID = pD_ID;
		PACKAGE_ID = pACKAGE_ID;
		ITEM_ID = iTEM_ID;
		RESOURCE_ID = rESOURCE_ID;
		ITEM_AMOUNT = iTEM_AMOUNT;
		MEASURE_UNIT = mEASURE_UNIT;
		PRICE = pRICE;
		DETAIL_STATUS = dETAIL_STATUS;
	}

	public String getPD_ID() {
		return PD_ID;
	}

	public void setPD_ID(String pD_ID) {
		PD_ID = pD_ID;
	}

	public String getPACKAGE_ID() {
		return PACKAGE_ID;
	}

	public void setPACKAGE_ID(String pACKAGE_ID) {
		PACKAGE_ID = pACKAGE_ID;
	}

	public String getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(String iTEM_ID) {
		ITEM_ID = iTEM_ID;
	}

	public String getRESOURCE_ID() {
		return RESOURCE_ID;
	}

	public void setRESOURCE_ID(String rESOURCE_ID) {
		RESOURCE_ID = rESOURCE_ID;
	}

	public Integer getITEM_AMOUNT() {
		return ITEM_AMOUNT;
	}

	public void setITEM_AMOUNT(Integer iTEM_AMOUNT) {
		ITEM_AMOUNT = iTEM_AMOUNT;
	}

	public String getMEASURE_UNIT() {
		return MEASURE_UNIT;
	}

	public void setMEASURE_UNIT(String mEASURE_UNIT) {
		MEASURE_UNIT = mEASURE_UNIT;
	}

	public Integer getPRICE() {
		return PRICE;
	}

	public void setPRICE(Integer pRICE) {
		PRICE = pRICE;
	}

	public Integer getDETAIL_STATUS() {
		return DETAIL_STATUS;
	}

	public void setDETAIL_STATUS(Integer dETAIL_STATUS) {
		DETAIL_STATUS = dETAIL_STATUS;
	}

	public List<PackageDetailBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<PackageDetailBean> beans = new ArrayList<PackageDetailBean>();
		for (HashMap<String, Object> hashMap : list) {
			PackageDetailBean bean = new PackageDetailBean();
			bean.setPD_ID(hashMap.get("PD_ID") != null ? hashMap.get("PD_ID")
					.toString() : null);
			bean.setPACKAGE_ID(hashMap.get("PACKAGE_ID") != null ? hashMap.get(
					"PACKAGE_ID").toString() : null);
			bean.setITEM_ID(hashMap.get("ITEM_ID") != null ? hashMap.get(
					"ITEM_ID").toString() : null);
			bean.setRESOURCE_ID(hashMap.get("RESOURCE_ID") != null ? hashMap
					.get("RESOURCE_ID").toString() : null);
			bean.setITEM_AMOUNT(hashMap.get("ITEM_AMOUNT") != null ? Integer
					.parseInt(hashMap.get("ITEM_AMOUNT").toString()) : null);
			bean.setMEASURE_UNIT(hashMap.get("MEASURE_UNIT") != null ? hashMap
					.get("MEASURE_UNIT").toString() : null);
			bean.setPRICE(hashMap.get("PRICE") != null ? Integer
					.parseInt(hashMap.get("PRICE").toString()) : null);
			bean.setDETAIL_STATUS(hashMap.get("DETAIL_STATUS") != null ? Integer
					.parseInt(hashMap.get("DETAIL_STATUS").toString()) : null);

			beans.add(bean);
		}
		return beans;
	}
}

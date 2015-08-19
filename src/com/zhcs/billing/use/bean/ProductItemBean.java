package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 产品维度表
 * 
 * @author hefa
 * 
 */
public class ProductItemBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4511355693510151558L;

	public ProductItemBean() {

	}

	public ProductItemBean(String iTEM_ID, String pRODUCT_ID,
			String rESOURCE_ID, String iTEM_CODE, String iTEM_NAME,
			String aP_ID, String iS_AUTO_SIZE, String dEFAULT_VALUE,
			String mAX_VALUE, Integer pRICE, String mEASUE_UNIT,
			Integer sEQUENCE_NO, Integer dETAIL_STATUS, String dESCRIPTION,
			String rEFERENCE_KEY) {
		super();
		ITEM_ID = iTEM_ID;
		PRODUCT_ID = pRODUCT_ID;
		RESOURCE_ID = rESOURCE_ID;
		ITEM_CODE = iTEM_CODE;
		ITEM_NAME = iTEM_NAME;
		AP_ID = aP_ID;
		IS_AUTO_SIZE = iS_AUTO_SIZE;
		DEFAULT_VALUE = dEFAULT_VALUE;
		MAX_VALUE = mAX_VALUE;
		PRICE = pRICE;
		MEASUE_UNIT = mEASUE_UNIT;
		SEQUENCE_NO = sEQUENCE_NO;
		DETAIL_STATUS = dETAIL_STATUS;
		DESCRIPTION = dESCRIPTION;
		REFERENCE_KEY = rEFERENCE_KEY;
	}

	private static final Integer dict = 16;// 节点类型

	public static Integer getDict() {
		return dict;
	}

	private String ITEM_ID;// 维度编号
	private String PRODUCT_ID;// 产品编号
	private String RESOURCE_ID;// 资源编号
	private String ITEM_CODE;// 维度编码
	private String ITEM_NAME;// 维度名称
	private String AP_ID;// AP编号
	private String IS_AUTO_SIZE;// 是否自行调整
	private String DEFAULT_VALUE;// 默认值
	private String MAX_VALUE;// 最大值
	private Integer PRICE;// 单价
	private String MEASUE_UNIT;// 计量单位
	private Integer SEQUENCE_NO;// 排序号
	private Integer DETAIL_STATUS;// 状态
	private String DESCRIPTION;// 描述
	private String REFERENCE_KEY;// 备用字段

	private int USAGE_AMOUNT;// 使用量
	private int CURRENT_ADD_TOTAL;// 单次扫描累积量
	private double MONEY;// 标准
	private double twoMONEY;// 二次批价

	private String PD_ID; // 套餐详细编号
	private Integer ITEM_AMOUNT;// 包内剩余数量
	private String SI_ID;// 申购明细编号

	public String getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(String iTEMID) {
		ITEM_ID = iTEMID;
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

	public String getITEM_CODE() {
		return ITEM_CODE;
	}

	public void setITEM_CODE(String iTEMCODE) {
		ITEM_CODE = iTEMCODE;
	}

	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	public void setITEM_NAME(String iTEMNAME) {
		ITEM_NAME = iTEMNAME;
	}

	public String getAP_ID() {
		return AP_ID;
	}

	public void setAP_ID(String aPID) {
		AP_ID = aPID;
	}

	public String getDEFAULT_VALUE() {
		return DEFAULT_VALUE;
	}

	public void setDEFAULT_VALUE(String dEFAULTVALUE) {
		DEFAULT_VALUE = dEFAULTVALUE;
	}

	public Integer getPRICE() {
		return PRICE;
	}

	public void setPRICE(Integer pRICE) {
		PRICE = pRICE;
	}

	public String getMEASUE_UNIT() {
		return MEASUE_UNIT;
	}

	public void setMEASUE_UNIT(String mEASUEUNIT) {
		MEASUE_UNIT = mEASUEUNIT;
	}

	public Integer getSEQUENCE_NO() {
		return SEQUENCE_NO;
	}

	public void setSEQUENCE_NO(Integer sEQUENCENO) {
		SEQUENCE_NO = sEQUENCENO;
	}

	public Integer getDETAIL_STATUS() {
		return DETAIL_STATUS;
	}

	public void setDETAIL_STATUS(Integer dETAILSTATUS) {
		DETAIL_STATUS = dETAILSTATUS;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getIS_AUTO_SIZE() {
		return IS_AUTO_SIZE;
	}

	public void setIS_AUTO_SIZE(String iS_AUTO_SIZE) {
		IS_AUTO_SIZE = iS_AUTO_SIZE;
	}

	public String getMAX_VALUE() {
		return MAX_VALUE;
	}

	public void setMAX_VALUE(String mAX_VALUE) {
		MAX_VALUE = mAX_VALUE;
	}

	public String getREFERENCE_KEY() {
		return REFERENCE_KEY;
	}

	public void setREFERENCE_KEY(String rEFERENCEKEY) {
		REFERENCE_KEY = rEFERENCEKEY;
	}

	public int getUSAGE_AMOUNT() {
		return USAGE_AMOUNT;
	}

	public void setUSAGE_AMOUNT(int uSAGE_AMOUNT) {
		USAGE_AMOUNT = uSAGE_AMOUNT;
	}

	public int getCURRENT_ADD_TOTAL() {
		return CURRENT_ADD_TOTAL;
	}

	public void setCURRENT_ADD_TOTAL(int cURRENT_ADD_TOTAL) {
		CURRENT_ADD_TOTAL = cURRENT_ADD_TOTAL;
	}

	public double getMONEY() {
		return MONEY;
	}

	public void setMONEY(double mONEY) {
		MONEY = mONEY;
	}

	public double getTwoMONEY() {
		return twoMONEY;
	}

	public void setTwoMONEY(double twoMONEY) {
		this.twoMONEY = twoMONEY;
	}

	public String getPD_ID() {
		return PD_ID;
	}

	public void setPD_ID(String pD_ID) {
		PD_ID = pD_ID;
	}

	public Integer getITEM_AMOUNT() {
		return ITEM_AMOUNT;
	}

	public void setITEM_AMOUNT(Integer iTEM_AMOUNT) {
		ITEM_AMOUNT = iTEM_AMOUNT;
	}

	public String getSI_ID() {
		return SI_ID;
	}

	public void setSI_ID(String sI_ID) {
		SI_ID = sI_ID;
	}

	public List<ProductItemBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<ProductItemBean> beans = new ArrayList<ProductItemBean>();
		for (HashMap<String, Object> hashMap : list) {
			ProductItemBean bean = new ProductItemBean();
			bean.setITEM_ID(hashMap.get("ITEM_ID") != null ? hashMap.get(
					"ITEM_ID").toString() : null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get(
					"PRODUCT_ID").toString() : null);
			bean.setRESOURCE_ID(hashMap.get("RESOURCE_ID") != null ? hashMap
					.get("RESOURCE_ID").toString() : null);
			bean.setITEM_CODE(hashMap.get("ITEM_CODE") != null ? hashMap.get(
					"ITEM_CODE").toString() : null);
			bean.setITEM_NAME(hashMap.get("ITEM_NAME") != null ? hashMap.get(
					"ITEM_NAME").toString() : null);
			bean.setAP_ID(hashMap.get("AP_ID") != null ? hashMap.get("AP_ID")
					.toString() : null);
			bean.setIS_AUTO_SIZE(hashMap.get("IS_AUTO_SIZE") != null ? hashMap
					.get("IS_AUTO_SIZE").toString() : null);
			bean.setDEFAULT_VALUE(hashMap.get("DEFAULT_VALUE") != null ? hashMap
					.get("DEFAULT_VALUE").toString() : null);
			bean.setMAX_VALUE(hashMap.get("MAX_VALUE") != null ? hashMap.get(
					"MAX_VALUE").toString() : null);
			bean.setPRICE(hashMap.get("PRICE") != null ? Integer
					.parseInt(hashMap.get("PRICE").toString()) : null);
			bean.setMEASUE_UNIT(hashMap.get("MEASUE_UNIT") != null ? hashMap
					.get("MEASUE_UNIT").toString() : null);
			bean.setSEQUENCE_NO(hashMap.get("SEQUENCE_NO") != null ? Integer
					.parseInt(hashMap.get("SEQUENCE_NO").toString()) : null);
			bean.setDETAIL_STATUS(hashMap.get("DETAIL_STATUS") != null ? Integer
					.parseInt(hashMap.get("DETAIL_STATUS").toString()) : null);
			bean.setDESCRIPTION(hashMap.get("DESCRIPTION") != null ? hashMap
					.get("DESCRIPTION").toString() : null);
			bean.setREFERENCE_KEY(hashMap.get("REFERENCE_KEY") != null ? hashMap
					.get("REFERENCE_KEY").toString() : null);
			beans.add(bean);
		}
		return beans;
	}

}

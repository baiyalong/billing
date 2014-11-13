package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 资费模型表
 * 
 * @author hefa
 * 
 */
public class PriceModelBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PriceModelBean() {
		// TODO Auto-generated constructor stub
	}
	
	public PriceModelBean(String mODEL_ID, String rULE_ID, String pRODUCT_ID,
			String rESOURCE_ID, String rESOURCE_CODE, String iTEM_ID,
			String iTEM_CODE, Integer mIN_X, Integer mAX_X,
			String iNCLUDE_MIN_X, String iNCLUDE_MAX_X, Integer bILLING_WAY,
			Integer sTEP_SIZE, Integer iNIT_VALUE, Integer iNCREMENT_VALUE,
			Integer fIXED_VALUE, String uNIT_NAME, String uNIT_CODE,
			Integer mODEL_STATUS) {
		super();
		MODEL_ID = mODEL_ID;
		RULE_ID = rULE_ID;
		PRODUCT_ID = pRODUCT_ID;
		RESOURCE_ID = rESOURCE_ID;
		RESOURCE_CODE = rESOURCE_CODE;
		ITEM_ID = iTEM_ID;
		ITEM_CODE = iTEM_CODE;
		MIN_X = mIN_X;
		MAX_X = mAX_X;
		INCLUDE_MIN_X = iNCLUDE_MIN_X;
		INCLUDE_MAX_X = iNCLUDE_MAX_X;
		BILLING_WAY = bILLING_WAY;
		STEP_SIZE = sTEP_SIZE;
		INIT_VALUE = iNIT_VALUE;
		INCREMENT_VALUE = iNCREMENT_VALUE;
		FIXED_VALUE = fIXED_VALUE;
		UNIT_NAME = uNIT_NAME;
		UNIT_CODE = uNIT_CODE;
		MODEL_STATUS = mODEL_STATUS;
	}



	private String MODEL_ID;// 资费模型编号
	private String RULE_ID;// 规则编号
	private String PRODUCT_ID;//产品编号
	private String RESOURCE_ID;//资源编号
	private String RESOURCE_CODE;// 资源编码
	private String ITEM_ID;// 维度编号
	private String ITEM_CODE;// 纬度编码
	private Integer MIN_X;// 区间最小值
	private Integer MAX_X;// 区间最大值
	private String INCLUDE_MIN_X;// 是否含最小值
	private String INCLUDE_MAX_X;// 是否含最大值
	private Integer BILLING_WAY;// 计费方式
	private Integer STEP_SIZE;// 步长
	private Integer INIT_VALUE;// 初始值
	private Integer INCREMENT_VALUE;// 增量值
	private Integer FIXED_VALUE;// 固定值
	private String UNIT_NAME;// 计价单位
	private String UNIT_CODE;// 计价单位编码
	private Integer MODEL_STATUS;// 状态

	public String getMODEL_ID() {
		return MODEL_ID;
	}

	public void setMODEL_ID(String mODELID) {
		MODEL_ID = mODELID;
	}

	public String getRULE_ID() {
		return RULE_ID;
	}

	public void setRULE_ID(String rULEID) {
		RULE_ID = rULEID;
	}
	
	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getRESOURCE_ID() {
		return RESOURCE_ID;
	}


	public void setRESOURCE_ID(String rESOURCE_ID) {
		RESOURCE_ID = rESOURCE_ID;
	}


	public String getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(String iTEMID) {
		ITEM_ID = iTEMID;
	}

	public Integer getMIN_X() {
		return MIN_X;
	}

	public void setMIN_X(Integer mINX) {
		MIN_X = mINX;
	}

	public Integer getMAX_X() {
		return MAX_X;
	}

	public void setMAX_X(Integer mAXX) {
		MAX_X = mAXX;
	}

	public String getINCLUDE_MIN_X() {
		return INCLUDE_MIN_X;
	}

	public void setINCLUDE_MIN_X(String iNCLUDEMINX) {
		INCLUDE_MIN_X = iNCLUDEMINX;
	}

	public String getINCLUDE_MAX_X() {
		return INCLUDE_MAX_X;
	}

	public void setINCLUDE_MAX_X(String iNCLUDEMAXX) {
		INCLUDE_MAX_X = iNCLUDEMAXX;
	}

	public Integer getBILLING_WAY() {
		return BILLING_WAY;
	}

	public void setBILLING_WAY(Integer bILLINGWAY) {
		BILLING_WAY = bILLINGWAY;
	}

	public Integer getSTEP_SIZE() {
		return STEP_SIZE;
	}

	public void setSTEP_SIZE(Integer sTEPSIZE) {
		STEP_SIZE = sTEPSIZE;
	}

	public Integer getINIT_VALUE() {
		return INIT_VALUE;
	}

	public void setINIT_VALUE(Integer iNITVALUE) {
		INIT_VALUE = iNITVALUE;
	}

	public Integer getINCREMENT_VALUE() {
		return INCREMENT_VALUE;
	}

	public void setINCREMENT_VALUE(Integer iNCREMENTVALUE) {
		INCREMENT_VALUE = iNCREMENTVALUE;
	}

	public Integer getFIXED_VALUE() {
		return FIXED_VALUE;
	}

	public void setFIXED_VALUE(Integer fIXEDVALUE) {
		FIXED_VALUE = fIXEDVALUE;
	}

	public String getUNIT_NAME() {
		return UNIT_NAME;
	}

	public void setUNIT_NAME(String uNITNAME) {
		UNIT_NAME = uNITNAME;
	}

	public String getUNIT_CODE() {
		return UNIT_CODE;
	}

	public void setUNIT_CODE(String uNITCODE) {
		UNIT_CODE = uNITCODE;
	}

	public Integer getMODEL_STATUS() {
		return MODEL_STATUS;
	}

	public void setMODEL_STATUS(Integer mODELSTATUS) {
		MODEL_STATUS = mODELSTATUS;
	}

	public String getRESOURCE_CODE() {
		return RESOURCE_CODE;
	}

	public void setRESOURCE_CODE(String rESOURCE_CODE) {
		RESOURCE_CODE = rESOURCE_CODE;
	}

	public String getITEM_CODE() {
		return ITEM_CODE;
	}

	public void setITEM_CODE(String iTEM_CODE) {
		ITEM_CODE = iTEM_CODE;
	}

	public List<PriceModelBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<PriceModelBean> beans = new ArrayList<PriceModelBean>();
		for (HashMap<String, Object> hashMap : list) {
			PriceModelBean bean = new PriceModelBean();
			bean.setBILLING_WAY(hashMap.get("BILLING_WAY") != null ? Integer.parseInt(hashMap.get("BILLING_WAY").toString()): null);
			bean.setFIXED_VALUE(hashMap.get("FIXED_VALUE") != null ? Integer.parseInt(hashMap.get("FIXED_VALUE").toString()): null);
			bean.setINCLUDE_MAX_X(hashMap.get("INCLUDE_MAX_X") != null ? hashMap.get("INCLUDE_MAX_X").toString(): null);
			bean.setINCLUDE_MIN_X(hashMap.get("INCLUDE_MIN_X") != null ? hashMap.get("INCLUDE_MIN_X").toString(): null);
			bean.setINCREMENT_VALUE(hashMap.get("INCREMENT_VALUE") != null ? Integer.parseInt(hashMap.get("INCREMENT_VALUE").toString()): null);
			bean.setINIT_VALUE(hashMap.get("INIT_VALUE") != null ? Integer.parseInt(hashMap.get("INIT_VALUE").toString()): null);
			bean.setITEM_ID(hashMap.get("ITEM_ID") != null ? hashMap.get("ITEM_ID").toString(): null);
			bean.setITEM_CODE(hashMap.get("ITEM_CODE") != null ? hashMap.get("ITEM_CODE").toString(): null);
			bean.setMAX_X(hashMap.get("MAX_X") != null ? Integer.parseInt(hashMap.get("MAX_X").toString()): null);
			bean.setMIN_X(hashMap.get("MIN_X") != null ? Integer.parseInt(hashMap.get("MIN_X").toString()): null);
			bean.setMODEL_ID(hashMap.get("MODEL_ID") != null ? hashMap.get("MODEL_ID").toString(): null);
			bean.setMODEL_STATUS(hashMap.get("MODEL_STATUS") != null ? Integer.parseInt(hashMap.get("MODEL_STATUS").toString()): null);
			bean.setPRODUCT_ID(hashMap.get("PRODUCT_ID") != null ? hashMap.get("PRODUCT_ID").toString(): null);
			bean.setRESOURCE_ID(hashMap.get("RESOURCE_ID") != null ? hashMap.get("RESOURCE_ID").toString(): null);
			bean.setRESOURCE_CODE(hashMap.get("RESOURCE_CODE") != null ? hashMap.get("RESOURCE_CODE").toString(): null);
			bean.setRULE_ID(hashMap.get("RULE_ID") != null ? hashMap.get("RULE_ID").toString(): null);
			bean.setSTEP_SIZE(hashMap.get("STEP_SIZE") != null ? Integer.parseInt(hashMap.get("STEP_SIZE").toString()): null);
			bean.setUNIT_CODE(hashMap.get("UNIT_CODE") != null ? hashMap.get("UNIT_CODE").toString(): null);
			bean.setUNIT_NAME(hashMap.get("UNIT_NAME") != null ? hashMap.get("UNIT_NAME").toString(): null);
			
			beans.add(bean);
		}
		return beans;
	}
	
}

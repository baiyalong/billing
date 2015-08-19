package com.zhcs.billing.use.bean;

/**
 * 资费规则定义表
 * 
 * @author hefa
 * 
 */
public class PriceRuleBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8540717177303912817L;

	public PriceRuleBean() {
		// TODO Auto-generated constructor stub
	}

	public PriceRuleBean(String rULE_ID, String rULE_NAME, String rULE_CODE,
			String nODE_NAME, String nODE_CODE, int nODE_TYPE, int bILLING_WAY,
			int vERSION, Integer rULE_STATUS, String dESCRIPTION) {
		super();
		RULE_ID = rULE_ID;
		RULE_NAME = rULE_NAME;
		RULE_CODE = rULE_CODE;
		NODE_NAME = nODE_NAME;
		NODE_CODE = nODE_CODE;
		NODE_TYPE = nODE_TYPE;
		BILLING_WAY = bILLING_WAY;
		VERSION = vERSION;
		RULE_STATUS = rULE_STATUS;
		DESCRIPTION = dESCRIPTION;
	}

	private String RULE_ID;// 规则编号
	private String RULE_NAME;// 规则名称
	private String RULE_CODE;// 规则编码
	private String NODE_NAME;// 节点名称
	private String NODE_CODE;// 节点编码
	private int NODE_TYPE;// 节点类型
	private int BILLING_WAY;// 计费方式
	private int VERSION;// 版本
	private Integer RULE_STATUS;// 状态
	private String DESCRIPTION;// 描述

	public String getRULE_ID() {
		return RULE_ID;
	}

	public void setRULE_ID(String rULEID) {
		RULE_ID = rULEID;
	}

	public String getRULE_NAME() {
		return RULE_NAME;
	}

	public void setRULE_NAME(String rULENAME) {
		RULE_NAME = rULENAME;
	}

	public String getRULE_CODE() {
		return RULE_CODE;
	}

	public void setRULE_CODE(String rULECODE) {
		RULE_CODE = rULECODE;
	}

	public Integer getRULE_STATUS() {
		return RULE_STATUS;
	}

	public void setRULE_STATUS(Integer rULESTATUS) {
		RULE_STATUS = rULESTATUS;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getNODE_NAME() {
		return NODE_NAME;
	}

	public void setNODE_NAME(String nODE_NAME) {
		NODE_NAME = nODE_NAME;
	}

	public String getNODE_CODE() {
		return NODE_CODE;
	}

	public void setNODE_CODE(String nODE_CODE) {
		NODE_CODE = nODE_CODE;
	}

	public int getNODE_TYPE() {
		return NODE_TYPE;
	}

	public void setNODE_TYPE(int nODE_TYPE) {
		NODE_TYPE = nODE_TYPE;
	}

	public int getBILLING_WAY() {
		return BILLING_WAY;
	}

	public void setBILLING_WAY(int bILLING_WAY) {
		BILLING_WAY = bILLING_WAY;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}

}

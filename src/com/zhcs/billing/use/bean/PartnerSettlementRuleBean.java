package com.zhcs.billing.use.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartnerSettlementRuleBean {

	private java.math.BigInteger ID;
	private String PARTNER_ID;
	private int ABILITY_TYPE;
	private String APP_ID;
	private int SETTLEMENT_RULE;
	private int SETTLEMENT_RULE_PARAM;
	private java.sql.Timestamp EFFECTIVE_DATE;
	private int STATUS;// 0Õý³£-1Ê§Ð§

	public int getSETTLEMENT_RULE_PARAM() {
		return SETTLEMENT_RULE_PARAM;
	}

	public void setSETTLEMENT_RULE_PARAM(int sETTLEMENT_RULE_PARAM) {
		SETTLEMENT_RULE_PARAM = sETTLEMENT_RULE_PARAM;
	}

	public int getABILITY_TYPE() {
		return ABILITY_TYPE;
	}

	public void setABILITY_TYPE(int aBILITY_TYPE) {
		ABILITY_TYPE = aBILITY_TYPE;
	}

	public String getPARTNER_ID() {
		return PARTNER_ID;
	}

	public void setPARTNER_ID(String pARTNER_ID) {
		PARTNER_ID = pARTNER_ID;
	}

	public String getAPP_ID() {
		return APP_ID;
	}

	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}

	public int getSETTLEMENT_RULE() {
		return SETTLEMENT_RULE;
	}

	public void setSETTLEMENT_RULE(int sETTLEMENT_RULE) {
		SETTLEMENT_RULE = sETTLEMENT_RULE;
	}

	public java.sql.Timestamp getEFFECTIVE_DATE() {
		return EFFECTIVE_DATE;
	}

	public void setEFFECTIVE_DATE(java.sql.Timestamp eFFECTIVE_DATE) {
		EFFECTIVE_DATE = eFFECTIVE_DATE;
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}

	public static List<PartnerSettlementRuleBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<PartnerSettlementRuleBean> beans = new ArrayList<PartnerSettlementRuleBean>();
		for (HashMap<String, Object> hashMap : list) {
			PartnerSettlementRuleBean bean = new PartnerSettlementRuleBean();
			bean.setID((BigInteger) (hashMap.get("ID") != null ? new java.math.BigInteger(
					hashMap.get("ID").toString()) : 0));

			bean.setABILITY_TYPE((Integer) (hashMap.get("ABILITY_TYPE") != null ? hashMap
					.get("ABILITY_TYPE") : 0));

			bean.setAPP_ID(hashMap.get("APP_ID") != null ? hashMap
					.get("APP_ID").toString() : null);

			bean.setEFFECTIVE_DATE(hashMap.get("EFFECTIVE_DATE") != null ? java.sql.Timestamp
					.valueOf(hashMap.get("EFFECTIVE_DATE").toString()) : null);
			bean.setPARTNER_ID(hashMap.get("PARTNER_ID") != null ? hashMap.get(
					"PARTNER_ID").toString() : null);
			bean.setSETTLEMENT_RULE(hashMap.get("SETTLEMENT_RULE") != null ? Integer
					.parseInt(hashMap.get("SETTLEMENT_RULE").toString()) : 0);
			bean.setSETTLEMENT_RULE_PARAM(hashMap.get("SETTLEMENT_RULE_PARAM") != null ? Integer
					.parseInt(hashMap.get("SETTLEMENT_RULE_PARAM").toString())
					: 0);
			bean.setSTATUS(hashMap.get("STATUS") != null ? Integer
					.parseInt(hashMap.get("STATUS").toString()) : 0);
			beans.add(bean);
		}
		return beans;
	}

	public java.math.BigInteger getID() {
		return ID;
	}

	public void setID(java.math.BigInteger iD) {
		ID = iD;
	}
}

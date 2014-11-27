package com.zhcs.billing.use.bean;

public class PartnerSettlementRecordsBean {

	private String PARTNER_ID;
	private String SETTLEMENT_RULE_ID;
	private int SETTLEMENT_AMOUNT;
	private java.sql.Timestamp SETTLEMENT_DATE;

	public String getPARTNER_ID() {
		return PARTNER_ID;
	}

	public void setPARTNER_ID(String pARTNER_ID) {
		PARTNER_ID = pARTNER_ID;
	}

	public String getSETTLEMENT_RULE_ID() {
		return SETTLEMENT_RULE_ID;
	}

	public void setSETTLEMENT_RULE_ID(String sETTLEMENT_RULE_ID) {
		SETTLEMENT_RULE_ID = sETTLEMENT_RULE_ID;
	}

	public int getSETTLEMENT_AMOUNT() {
		return SETTLEMENT_AMOUNT;
	}

	public void setSETTLEMENT_AMOUNT(int sETTLEMENT_AMOUNT) {
		SETTLEMENT_AMOUNT = sETTLEMENT_AMOUNT;
	}

	public java.sql.Timestamp getSETTLEMENT_DATE() {
		return SETTLEMENT_DATE;
	}

	public void setSETTLEMENT_DATE(java.sql.Timestamp sETTLEMENT_DATE) {
		SETTLEMENT_DATE = sETTLEMENT_DATE;
	}
}

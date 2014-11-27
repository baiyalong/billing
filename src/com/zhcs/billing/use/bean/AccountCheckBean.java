package com.zhcs.billing.use.bean;

public class AccountCheckBean {

	private String CUSTOMER_ID;
	private String ACCOUNT_ID;
	private int INCOME;
	private int OUTCOME;
	private int BALANCE;
	private java.sql.Timestamp CHECK_DATE;
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public String getACCOUNT_ID() {
		return ACCOUNT_ID;
	}
	public void setACCOUNT_ID(String aCCOUNT_ID) {
		ACCOUNT_ID = aCCOUNT_ID;
	}
	public int getINCOME() {
		return INCOME;
	}
	public void setINCOME(int iNCOME) {
		INCOME = iNCOME;
	}
	public int getOUTCOME() {
		return OUTCOME;
	}
	public void setOUTCOME(int oUTCOME) {
		OUTCOME = oUTCOME;
	}
	public int getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(int bALANCE) {
		BALANCE = bALANCE;
	}
	public java.sql.Timestamp getCHECK_DATE() {
		return CHECK_DATE;
	}
	public void setCHECK_DATE(java.sql.Timestamp cHECK_DATE) {
		CHECK_DATE = cHECK_DATE;
	}
	
}

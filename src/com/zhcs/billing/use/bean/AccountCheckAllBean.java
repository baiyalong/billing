package com.zhcs.billing.use.bean;

public class AccountCheckAllBean {

	private int INCOME;
	private int OUTCOME;
	private int BALANCE;
	private java.sql.Timestamp CHECK_DATE;

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

	public java.sql.Timestamp getCHECK_DATE() {
		return CHECK_DATE;
	}

	public void setCHECK_DATE(java.sql.Timestamp cHECK_DATE) {
		CHECK_DATE = cHECK_DATE;
	}

	public int getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(int bALANCE) {
		BALANCE = bALANCE;
	}

}

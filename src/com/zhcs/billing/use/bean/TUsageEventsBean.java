package com.zhcs.billing.use.bean;

/**
 * �굥��
 * 
 * @author yuqingchao
 *
 */
public class TUsageEventsBean {

	private String BILLCYCLE_ID;// ����ID
	private String REQ_TIME; // ʱ��
	private String ORDER_ID; // ����
	private String CUSTOMER_ID; // �ͻ����
	private String BILLING_ID; // �ӿ�֮��ı�ʶ��
	private String PRUDUCT_ID; // ��Ʒ���
	private int REALOCKSTAT; // ʵ�۽��:1ʵ���� 2ʵ����� 3ʵ��ʧ��
	private int ACCOUNT; // ʵ�۽��
	private boolean FEE_TYPE; // ��������(�Ƿ��շ�)
	private String SCHEDULEDSCANNINGSTATE;// ��ʱɨ��״̬

	public String getBILLCYCLE_ID() {
		return BILLCYCLE_ID;
	}

	public void setBILLCYCLE_ID(String bILLCYCLEID) {
		BILLCYCLE_ID = bILLCYCLEID;
	}

	public String getBILLING_ID() {
		return BILLING_ID;
	}

	public void setBILLING_ID(String bILLINGID) {
		BILLING_ID = bILLINGID;
	}

	public String getSCHEDULEDSCANNINGSTATE() {
		return SCHEDULEDSCANNINGSTATE;
	}

	public void setSCHEDULEDSCANNINGSTATE(String sCHEDULEDSCANNINGSTATE) {
		SCHEDULEDSCANNINGSTATE = sCHEDULEDSCANNINGSTATE;
	}

	public String getREQ_TIME() {
		return REQ_TIME;
	}

	public void setREQ_TIME(String rEQTIME) {
		REQ_TIME = rEQTIME;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMERID) {
		CUSTOMER_ID = cUSTOMERID;
	}

	public String getPRUDUCT_ID() {
		return PRUDUCT_ID;
	}

	public void setPRUDUCT_ID(String pRUDUCTID) {
		PRUDUCT_ID = pRUDUCTID;
	}

	public int getREALOCKSTAT() {
		return REALOCKSTAT;
	}

	public void setREALOCKSTAT(int rEALOCKSTAT) {
		REALOCKSTAT = rEALOCKSTAT;
	}

	public int getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(int aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	public boolean isFEE_TYPE() {
		return FEE_TYPE;
	}

	public void setFEE_TYPE(boolean fEETYPE) {
		FEE_TYPE = fEETYPE;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
	}
}

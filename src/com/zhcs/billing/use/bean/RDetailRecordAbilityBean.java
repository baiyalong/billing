package com.zhcs.billing.use.bean;

public class RDetailRecordAbilityBean {

	public RDetailRecordAbilityBean() {

	}

	public int getMSG_TYPE() {
		return MSG_TYPE;
	}

	public void setMSG_TYPE(int mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}

	public String getACCOUNT_ID() {
		return ACCOUNT_ID;
	}

	public void setACCOUNT_ID(String aCCOUNT_ID) {
		ACCOUNT_ID = aCCOUNT_ID;
	}

	public String getCOUNTAINER_ID() {
		return COUNTAINER_ID;
	}

	public void setCOUNTAINER_ID(String cOUNTAINER_ID) {
		COUNTAINER_ID = cOUNTAINER_ID;
	}

	public int getDEDUCTION_AMOUNT() {
		return DEDUCTION_AMOUNT;
	}

	public void setDEDUCTION_AMOUNT(int dEDUCTION_AMOUNT) {
		DEDUCTION_AMOUNT = dEDUCTION_AMOUNT;
	}

	public int getOPERATION_RESULT() {
		return OPERATION_RESULT;
	}

	public void setOPERATION_RESULT(int oPERATION_RESULT) {
		OPERATION_RESULT = oPERATION_RESULT;
	}

	public int getPAYMENT_TYPE() {
		return PAYMENT_TYPE;
	}

	public void setPAYMENT_TYPE(int pAYMENT_TYPE) {
		PAYMENT_TYPE = pAYMENT_TYPE;
	}

	public java.sql.Timestamp getORIGINAL_RECORD_TIME() {
		return ORIGINAL_RECORD_TIME;
	}

	public void setORIGINAL_RECORD_TIME(java.sql.Timestamp oRIGINAL_RECORD_TIME) {
		ORIGINAL_RECORD_TIME = oRIGINAL_RECORD_TIME;
	}

	public java.sql.Timestamp getDETAIL_RECORD_TIME() {
		return DETAIL_RECORD_TIME;
	}

	public void setDETAIL_RECORD_TIME(java.sql.Timestamp dETAIL_RECORD_TIME) {
		DETAIL_RECORD_TIME = dETAIL_RECORD_TIME;
	}

	public String getORIGINAL_RECORD() {
		return ORIGINAL_RECORD;
	}

	public void setORIGINAL_RECORD(String oRIGINAL_RECORD) {
		ORIGINAL_RECORD = oRIGINAL_RECORD;
	}

	public int getDEDUCTION_TYPE() {
		return DEDUCTION_TYPE;
	}

	public void setDEDUCTION_TYPE(int dEDUCTION_TYPE) {
		DEDUCTION_TYPE = dEDUCTION_TYPE;
	}

	// public String getSUBSCRIPTION_ITEM_ID() {
	// return SUBSCRIPTION_ITEM_ID;
	// }
	//
	// public void setSUBSCRIPTION_ITEM_ID(String sUBSCRIPTION_ITEM_ID) {
	// SUBSCRIPTION_ITEM_ID = sUBSCRIPTION_ITEM_ID;
	// }

	public int getPRICE() {
		return PRICE;
	}

	public void setPRICE(int pRICE) {
		PRICE = pRICE;
	}

	public String getBOOK_ID() {
		return BOOK_ID;
	}

	public void setBOOK_ID(String bOOK_ID) {
		BOOK_ID = bOOK_ID;
	}

	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}

	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
	}

	public String getAREA_CODE() {
		return AREA_CODE;
	}

	public void setAREA_CODE(String aREA_CODE) {
		AREA_CODE = aREA_CODE;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCT_ID) {
		PRODUCT_ID = pRODUCT_ID;
	}

	public String getSUBSCRIBER_ID() {
		return SUBSCRIBER_ID;
	}

	public void setSUBSCRIBER_ID(String sUBSCRIBER_ID) {
		SUBSCRIBER_ID = sUBSCRIBER_ID;
	}

	public String getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(String iTEM_ID) {
		ITEM_ID = iTEM_ID;
	}

	public String getPD_ID() {
		return PD_ID;
	}

	public void setPD_ID(String pD_ID) {
		PD_ID = pD_ID;
	}

	public String getSI_ID() {
		return SI_ID;
	}

	public void setSI_ID(String sI_ID) {
		SI_ID = sI_ID;
	}

	public int getREMAINING_AMOUNT() {
		return REMAINING_AMOUNT;
	}

	public void setREMAINING_AMOUNT(int rEMAINING_AMOUNT) {
		REMAINING_AMOUNT = rEMAINING_AMOUNT;
	}

	public String getITEM_CODE() {
		return ITEM_CODE;
	}

	public void setITEM_CODE(String iTEM_CODE) {
		ITEM_CODE = iTEM_CODE;
	}

	private int MSG_TYPE;// �������� 10 11 12 13
	private String CUSTOMER_ID;// �û�ID -������ >����

	private String COUNTAINER_ID;// ����ID ��ԭʼ������ȡ ������ �굥��¼��
	private int DEDUCTION_TYPE;// �۷�����
	// 0�� û���ײͣ�û���Żݣ�����
	// 1�� ���ײͣ��ײ������꣬����
	// 2�� ���ײͣ��ײ�δ���꣬���ײ�
	private int DEDUCTION_AMOUNT;// �۷ѽ��
	private int OPERATION_RESULT;// �������
	private int PAYMENT_TYPE;// �������� 0 Ԥ����/1�󸶷� -�˻���Ϣ�� >����
	private java.sql.Timestamp ORIGINAL_RECORD_TIME;// ԭʼ��������ʱ�� ��ԭʼ������ȡ
	private java.sql.Timestamp DETAIL_RECORD_TIME;// �굥����ʱ�� �굥��¼��
	private String ORIGINAL_RECORD;// ԭʼ���� �굥��¼��

	private int PRICE;// ��׼�ʷ�/�����ײ��ʷ� -��Ʒά�ȱ� -�ײ���ϸ�� >��Ʒ
	private String SI_ID;// �깺��ϸID -�깺��ϸ�� >����
	private String BOOK_ID;// �˱�ID -�˱���Ϣ�� >����
	private String PROVINCE_CODE;// ʡ�ݱ��� -�˱���Ϣ�� >����
	private String AREA_CODE;// �������� -�˱���Ϣ�� >����
	private String ACCOUNT_ID;// �˻�ID -�˻���Ϣ�� >����
	private String ORDER_ID;// ����ID

	//
	private String PRODUCT_ID;// ��ƷID
	private String SUBSCRIBER_ID;// �깺���
	private String ITEM_ID;// ά�ȱ��
	private String ITEM_CODE;// γ�ȱ��� �̲ʵ�
	private String PD_ID;// �ײ���ϸ���
	private int REMAINING_AMOUNT;// ����ʣ����
}

package com.zhcs.billing.use.bean;

public class AccountPayBean {

	private String TIME_STAMP;	//ʱ���
	private String BILLING_ID;	//�ӿ�֮��ı�ʶ��
	private String CUSTOMER_ID;	//�ͻ����
	private String ORDER_ID;	//�������
	private int AMOUNT;			//�۷ѽ��
	private int CDR_TYPE;		//��������  1���������Ʒ�����շ� ��ÿ�¿۹̶��ķ��ã���������Ϣ�޹أ�
	 							//2���������Ʒ��ʹ�����շ� ����CPU���ڴ桢Ӳ�̵Ȱ�ʵ�ʾ�̬������Ϣ�շѣ�
	 							//3���������Ʒ��ʹ�����շ� ������š����š������Ȱ�ʹ�����շѣ�
	private boolean RESULT;		//�۷ѽ��(true:�ɹ� false:ʧ��)
	private String DESCRIPTION;	//�Դ���/�쳣����ϸ������Ϣ
	public String getTIME_STAMP() {
		return TIME_STAMP;
	}
	public void setTIME_STAMP(String tIMESTAMP) {
		TIME_STAMP = tIMESTAMP;
	}
	public String getBILLING_ID() {
		return BILLING_ID;
	}
	public void setBILLING_ID(String bILLINGID) {
		BILLING_ID = bILLINGID;
	}
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(String cUSTOMERID) {
		CUSTOMER_ID = cUSTOMERID;
	}
	public String getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
	}
	public int getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(int aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public int getCDR_TYPE() {
		return CDR_TYPE;
	}
	public void setCDR_TYPE(int cDRTYPE) {
		CDR_TYPE = cDRTYPE;
	}
	public boolean isRESULT() {
		return RESULT;
	}
	public void setRESULT(boolean rESULT) {
		RESULT = rESULT;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	
	
}

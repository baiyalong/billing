package com.zhcs.billing.use.bean;

import java.sql.Timestamp;

public class RErrorRecordBean {

	public RErrorRecordBean(int MSG_TYPE, String MSG, String DESCRIPTION) {
		this.setMSG_TYPE(MSG_TYPE);
		this.setMSG(MSG);
		this.setDESCRIPTION(DESCRIPTION);
	}

	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public Timestamp getTIMESTAMP() {
		return TIMESTAMP;
	}

	public void setTIMESTAMP(Timestamp tIMESTAMP) {
		TIMESTAMP = tIMESTAMP;
	}

	public Timestamp getRESOLVE_TIMESTAMP() {
		return RESOLVE_TIMESTAMP;
	}

	public void setRESOLVE_TIMESTAMP(Timestamp rESOLVE_TIMESTAMP) {
		RESOLVE_TIMESTAMP = rESOLVE_TIMESTAMP;
	}

	public int getRESOLVE() {
		return RESOLVE;
	}

	public void setRESOLVE(int rESOLVE) {
		RESOLVE = rESOLVE;
	}

	public int getMSG_TYPE() {
		return MSG_TYPE;
	}

	public void setMSG_TYPE(int mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}

	private int MSG_TYPE;// ��Ϣ���� 0������ 1��Ӧ��
	private String MSG;// ԭʼ����
	private String DESCRIPTION;// ����
	private Timestamp TIMESTAMP;// ����ʱ���
	private int RESOLVE;// �Ƿ��� 0��δ��� 1���ѽ��
	private Timestamp RESOLVE_TIMESTAMP;// ���ʱ���
}

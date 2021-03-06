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

	private int MSG_TYPE;// 消息类型 0：能力 1：应用
	private String MSG;// 原始话单
	private String DESCRIPTION;// 描述
	private Timestamp TIMESTAMP;// 出错时间戳
	private int RESOLVE;// 是否解决 0：未解决 1：已解决
	private Timestamp RESOLVE_TIMESTAMP;// 解决时间戳
}

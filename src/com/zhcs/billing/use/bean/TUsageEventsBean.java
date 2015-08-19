package com.zhcs.billing.use.bean;

/**
 * 详单表
 * 
 * @author yuqingchao
 *
 */
public class TUsageEventsBean {

	private String BILLCYCLE_ID;// 账期ID
	private String REQ_TIME; // 时间
	private String ORDER_ID; // 订单
	private String CUSTOMER_ID; // 客户编号
	private String BILLING_ID; // 接口之间的标识符
	private String PRUDUCT_ID; // 产品编号
	private int REALOCKSTAT; // 实扣结果:1实扣中 2实扣完成 3实扣失败
	private int ACCOUNT; // 实扣金额
	private boolean FEE_TYPE; // 费用类型(是否收费)
	private String SCHEDULEDSCANNINGSTATE;// 定时扫描状态

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

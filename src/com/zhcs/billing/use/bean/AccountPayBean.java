package com.zhcs.billing.use.bean;

public class AccountPayBean {

	private String TIME_STAMP;	//时间戳
	private String BILLING_ID;	//接口之间的标识符
	private String CUSTOMER_ID;	//客户编号
	private String ORDER_ID;	//订单编号
	private int AMOUNT;			//扣费金额
	private int CDR_TYPE;		//话单类型  1：容器类产品包月收费 （每月扣固定的费用，与配置信息无关）
	 							//2：容器类产品按使用量收费 （如CPU、内存、硬盘等按实际静态配置信息收费）
	 							//3：容器类产品按使用量收费 （如短信、彩信、流量等按使用量收费）
	private boolean RESULT;		//扣费结果(true:成功 false:失败)
	private String DESCRIPTION;	//对错误/异常的详细描述信息
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

package com.zhcs.billing.use.bean;

/**
 * T_USAGE_MONTHLY 资费月租表Bean
 * 
 * @author yuqingchao
 *
 */
public class UsageMonthlyBean {

	private String UNIT_RATE; // 月扣金额
	private int FIXED_OR_MONTHLY; // 0：按天扣，1：月初扣
	private String COMPLETE_STATE; // 扫描状态
	private String BILLSEQ_ID; // 账期
	private String CUSTOMER_ID; // 客户编号
	private String SCANNING_WAY; // 扫描方式
	private String ORDER_ID; // 订单编号
	private String PRODUCT_ID; // 产品编号
	private String PRODUCT_TYPE; // 产品类型
	private int PRODUCT_CATEGORY; // 产品大类（1：容器类 2：应用类）
	private String STATE; // 状态
	private int SERVERS_NO_STATE;// 服务器编号状态

	public String getUNIT_RATE() {
		return UNIT_RATE;
	}

	public void setUNIT_RATE(String uNITRATE) {
		UNIT_RATE = uNITRATE;
	}

	public int getFIXED_OR_MONTHLY() {
		return FIXED_OR_MONTHLY;
	}

	public void setFIXED_OR_MONTHLY(int fIXEDORMONTHLY) {
		FIXED_OR_MONTHLY = fIXEDORMONTHLY;
	}

	public String getCOMPLETE_STATE() {
		return COMPLETE_STATE;
	}

	public void setCOMPLETE_STATE(String cOMPLETESTATE) {
		COMPLETE_STATE = cOMPLETESTATE;
	}

	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}

	public void setCUSTOMER_ID(String cUSTOMERID) {
		CUSTOMER_ID = cUSTOMERID;
	}

	public String getSCANNING_WAY() {
		return SCANNING_WAY;
	}

	public void setSCANNING_WAY(String sCANNINGWAY) {
		SCANNING_WAY = sCANNINGWAY;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDERID) {
		ORDER_ID = oRDERID;
	}

	public String getPRODUCT_ID() {
		return PRODUCT_ID;
	}

	public void setPRODUCT_ID(String pRODUCTID) {
		PRODUCT_ID = pRODUCTID;
	}

	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}

	public void setPRODUCT_TYPE(String pRODUCTTYPE) {
		PRODUCT_TYPE = pRODUCTTYPE;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public int getSERVERS_NO_STATE() {
		return SERVERS_NO_STATE;
	}

	public void setSERVERS_NO_STATE(int sERVERSNOSTATE) {
		SERVERS_NO_STATE = sERVERSNOSTATE;
	}

	public void setPRODUCT_CATEGORY(int pRODUCT_CATEGORY) {
		PRODUCT_CATEGORY = pRODUCT_CATEGORY;
	}

	public int getPRODUCT_CATEGORY() {
		return PRODUCT_CATEGORY;
	}

	public void setBILLSEQ_ID(String bILLSEQ_ID) {
		BILLSEQ_ID = bILLSEQ_ID;
	}

	public String getBILLSEQ_ID() {
		return BILLSEQ_ID;
	}

}

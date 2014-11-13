package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.util.Common;

/**
 * 容器类产品三次批价记录表
 * @author hefa
 *
 */
public class TCulOrderDetailBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -26341783112815427L;

	public TCulOrderDetailBean() {
		
	}
	
	public TCulOrderDetailBean(String iD, String oRDER_ID, String cODE,
			String cTYPE, String bEFORE_AMOUNT, String aFTER_AMOUNT,
			String cOUNT_COST, String dEDUCT_COST, String rEALITY,
			String sCANNING_TIME, String sCANNING_WAY, Date cREATE_TIME,
			Date uPDATE_TIME) {
		super();
		ID = iD;
		ORDER_ID = oRDER_ID;
		CODE = cODE;
		CTYPE = cTYPE;
		BEFORE_AMOUNT = bEFORE_AMOUNT;
		AFTER_AMOUNT = aFTER_AMOUNT;
		COUNT_COST = cOUNT_COST;
		DEDUCT_COST = dEDUCT_COST;
		REALITY = rEALITY;
		SCANNING_TIME = sCANNING_TIME;
		SCANNING_WAY = sCANNING_WAY;
		CREATE_TIME = cREATE_TIME;
		UPDATE_TIME = uPDATE_TIME;
	}



	private String ID;// 主键id
	private String ORDER_ID;// 订单编号
	private String CODE;// 编号
	private String BEFORE_AMOUNT;// 优惠前金额
	private String AFTER_AMOUNT;// 优惠后金额
	private String COUNT_COST;// 累计计算费用
	private String DEDUCT_COST;//已经扣除费用
	private String REALITY;//本次扣费
	private String CTYPE;// 类型（产品，资源）
	private String SCANNING_TIME;// 扫描时间
	private String SCANNING_WAY;// 扫描方式(1.一天扫描一次 5.五分钟扫描一次)
	private Date CREATE_TIME;// 创建时间
	private Date UPDATE_TIME;// 更新时间
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getCTYPE() {
		return CTYPE;
	}

	public void setCTYPE(String cTYPE) {
		CTYPE = cTYPE;
	}

	public String getBEFORE_AMOUNT() {
		return BEFORE_AMOUNT;
	}

	public void setBEFORE_AMOUNT(String bEFORE_AMOUNT) {
		BEFORE_AMOUNT = bEFORE_AMOUNT;
	}

	public String getAFTER_AMOUNT() {
		return AFTER_AMOUNT;
	}

	public void setAFTER_AMOUNT(String aFTER_AMOUNT) {
		AFTER_AMOUNT = aFTER_AMOUNT;
	}

	public String getCOUNT_COST() {
		return COUNT_COST;
	}

	public void setCOUNT_COST(String cOUNT_COST) {
		COUNT_COST = cOUNT_COST;
	}

	public String getDEDUCT_COST() {
		return DEDUCT_COST;
	}

	public void setDEDUCT_COST(String dEDUCT_COST) {
		DEDUCT_COST = dEDUCT_COST;
	}

	public String getREALITY() {
		return REALITY;
	}

	public void setREALITY(String rEALITY) {
		REALITY = rEALITY;
	}

	public String getSCANNING_TIME() {
		return SCANNING_TIME;
	}

	public void setSCANNING_TIME(String sCANNING_TIME) {
		SCANNING_TIME = sCANNING_TIME;
	}

	public String getSCANNING_WAY() {
		return SCANNING_WAY;
	}

	public void setSCANNING_WAY(String sCANNING_WAY) {
		SCANNING_WAY = sCANNING_WAY;
	}

	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	
	public List<TCulOrderDetailBean> changeToObject(List<HashMap<String, Object>> list) {
		List<TCulOrderDetailBean> beans = new ArrayList<TCulOrderDetailBean>();
		for (HashMap<String, Object> hashMap : list) {
			TCulOrderDetailBean bean = new TCulOrderDetailBean();
			bean.setID(hashMap.get("ID") != null ? hashMap.get("ID").toString() : null);
			bean.setORDER_ID(hashMap.get("ORDER_ID") != null ? hashMap.get("ORDER_ID").toString() : null);
			bean.setCODE(hashMap.get("CODE") != null ? hashMap.get("CODE").toString(): null);
			bean.setCTYPE(hashMap.get("CTYPE") != null ? hashMap.get("CTYPE").toString(): null);
			bean.setBEFORE_AMOUNT(hashMap.get("BEFORE_AMOUNT") != null ? hashMap.get("BEFORE_AMOUNT").toString() : null);
			bean.setAFTER_AMOUNT(hashMap.get("AFTER_AMOUNT") != null ? hashMap.get("AFTER_AMOUNT").toString(): null);
			bean.setCOUNT_COST(hashMap.get("COUNT_COST") != null ? hashMap.get("COUNT_COST").toString(): null);
			bean.setDEDUCT_COST(hashMap.get("DEDUCT_COST") != null ? hashMap.get("DEDUCT_COST").toString(): null);
			bean.setREALITY(hashMap.get("REALITY") != null ? hashMap.get("REALITY").toString(): null);
			bean.setSCANNING_TIME(hashMap.get("SCANNING_TIME") != null ? hashMap.get("SCANNING_TIME").toString() : null);
			bean.setSCANNING_WAY(hashMap.get("SCANNING_WAY") != null ? hashMap.get("SCANNING_WAY").toString() : null);
			bean.setCREATE_TIME(hashMap.get("CREATE_TIME") != null ? Common.StrToDate(hashMap.get("CREATE_TIME").toString()) : null);
			bean.setUPDATE_TIME(hashMap.get("UPDATE_TIME") != null ? Common.StrToDate(hashMap.get("UPDATE_TIME").toString()): null);
			beans.add(bean);
		}
		return beans;
	}
}

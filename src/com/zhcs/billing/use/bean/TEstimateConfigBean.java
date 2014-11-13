package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TEstimateConfigBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4578392392087799801L;

	public TEstimateConfigBean() {
	}
	
	private String CONFIG_ID;// 主键id
	private String SEL_TABLE_NAME;// 查询表名称
	private String SEL_TABLE_ORDERNO_BUS;// 查询表订单编号(业务)
	private String SEL_TABLE_ORDERNO_EST;// 查询表订单编号(计量)
	private String SEL_PRODUCT_NO_BUS;// 查询产品编号(业务)
	private String SEL_PRODUCT_NO_EST;// 查询产品编号(计量)
	private String SEL_BUSWD_NO;// 业务维度编号
	private String SEL_ESTWD_VALUE;// 计量维度使用量字段
	private String SEL_TABLE_RENO_BUS;// 查询表资源编号(业务)
	private String SEL_TABLE_RENO_EST;// 查询表资源编号(计量)
	private String SEL_ESTWD_NO;// 计量维度编号
	private String SEL_WDENG_NAME;// 维度字段名称(计量)
	private String SEL_WHERE;// 查询条件
	private String SEL_START_TIME;// 开始时间
	private String SEL_END_TIME;// 结束时间
	private String REMARK;// 备注
	private Date SEL_TIME;//传入的时间点

	public TEstimateConfigBean(String cONFIGID, String sELTABLENAME,
			String sELTABLEORDERNOBUS, String sELTABLEORDERNOEST,
			String sELPRODUCTNOBUS, String sELPRODUCTNOEST, String sELBUSWDNO,
			String sELESTWDVALUE, String sELTABLERENOBUS,
			String sELTABLERENOEST, String sELESTWDNO, String sELWDENGNAME,
			String sELWHERE, String sELSTARTTIME, String sELENDTIME,
			String rEMARK, Date sELTIME) {
		super();
		CONFIG_ID = cONFIGID;
		SEL_TABLE_NAME = sELTABLENAME;
		SEL_TABLE_ORDERNO_BUS = sELTABLEORDERNOBUS;
		SEL_TABLE_ORDERNO_EST = sELTABLEORDERNOEST;
		SEL_PRODUCT_NO_BUS = sELPRODUCTNOBUS;
		SEL_PRODUCT_NO_EST = sELPRODUCTNOEST;
		SEL_BUSWD_NO = sELBUSWDNO;
		SEL_ESTWD_VALUE = sELESTWDVALUE;
		SEL_TABLE_RENO_BUS = sELTABLERENOBUS;
		SEL_TABLE_RENO_EST = sELTABLERENOEST;
		SEL_ESTWD_NO = sELESTWDNO;
		SEL_WDENG_NAME = sELWDENGNAME;
		SEL_WHERE = sELWHERE;
		SEL_START_TIME = sELSTARTTIME;
		SEL_END_TIME = sELENDTIME;
		REMARK = rEMARK;
		SEL_TIME = sELTIME;
	}

	public String getCONFIG_ID() {
		return CONFIG_ID;
	}

	public void setCONFIG_ID(String cONFIG_ID) {
		CONFIG_ID = cONFIG_ID;
	}

	public String getSEL_TABLE_NAME() {
		return SEL_TABLE_NAME;
	}

	public void setSEL_TABLE_NAME(String sEL_TABLE_NAME) {
		SEL_TABLE_NAME = sEL_TABLE_NAME;
	}

	public String getSEL_TABLE_ORDERNO_BUS() {
		return SEL_TABLE_ORDERNO_BUS;
	}

	public void setSEL_TABLE_ORDERNO_BUS(String sEL_TABLE_ORDERNO_BUS) {
		SEL_TABLE_ORDERNO_BUS = sEL_TABLE_ORDERNO_BUS;
	}

	public String getSEL_TABLE_ORDERNO_EST() {
		return SEL_TABLE_ORDERNO_EST;
	}

	public void setSEL_TABLE_ORDERNO_EST(String sEL_TABLE_ORDERNO_EST) {
		SEL_TABLE_ORDERNO_EST = sEL_TABLE_ORDERNO_EST;
	}

	public String getSEL_PRODUCT_NO_BUS() {
		return SEL_PRODUCT_NO_BUS;
	}

	public void setSEL_PRODUCT_NO_BUS(String sEL_PRODUCT_NO_BUS) {
		SEL_PRODUCT_NO_BUS = sEL_PRODUCT_NO_BUS;
	}

	public String getSEL_PRODUCT_NO_EST() {
		return SEL_PRODUCT_NO_EST;
	}

	public void setSEL_PRODUCT_NO_EST(String sEL_PRODUCT_NO_EST) {
		SEL_PRODUCT_NO_EST = sEL_PRODUCT_NO_EST;
	}

	public String getSEL_BUSWD_NO() {
		return SEL_BUSWD_NO;
	}

	public void setSEL_BUSWD_NO(String sEL_BUSWD_NO) {
		SEL_BUSWD_NO = sEL_BUSWD_NO;
	}

	public String getSEL_ESTWD_VALUE() {
		return SEL_ESTWD_VALUE;
	}

	public void setSEL_ESTWD_VALUE(String sEL_ESTWD_VALUE) {
		SEL_ESTWD_VALUE = sEL_ESTWD_VALUE;
	}

	public String getSEL_TABLE_RENO_BUS() {
		return SEL_TABLE_RENO_BUS;
	}

	public void setSEL_TABLE_RENO_BUS(String sEL_TABLE_RENO_BUS) {
		SEL_TABLE_RENO_BUS = sEL_TABLE_RENO_BUS;
	}

	public String getSEL_TABLE_RENO_EST() {
		return SEL_TABLE_RENO_EST;
	}

	public void setSEL_TABLE_RENO_EST(String sEL_TABLE_RENO_EST) {
		SEL_TABLE_RENO_EST = sEL_TABLE_RENO_EST;
	}

	public String getSEL_ESTWD_NO() {
		return SEL_ESTWD_NO;
	}

	public void setSEL_ESTWD_NO(String sEL_ESTWD_NO) {
		SEL_ESTWD_NO = sEL_ESTWD_NO;
	}

	public String getSEL_WHERE() {
		return SEL_WHERE;
	}

	public void setSEL_WHERE(String sEL_WHERE) {
		SEL_WHERE = sEL_WHERE;
	}

	public void setSEL_START_TIME(String sEL_START_TIME) {
		SEL_START_TIME = sEL_START_TIME;
	}

	public void setSEL_END_TIME(String sEL_END_TIME) {
		SEL_END_TIME = sEL_END_TIME;
	}

	public String getSEL_START_TIME() {
		return SEL_START_TIME;
	}

	public String getSEL_END_TIME() {
		return SEL_END_TIME;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getSEL_WDENG_NAME() {
		return SEL_WDENG_NAME;
	}

	public void setSEL_WDENG_NAME(String sELWDENGNAME) {
		SEL_WDENG_NAME = sELWDENGNAME;
	}

	public Date getSEL_TIME() {
		return SEL_TIME;
	}

	public void setSEL_TIME(Date sELTIME) {
		SEL_TIME = sELTIME;
	}

	public List<TEstimateConfigBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<TEstimateConfigBean> beans = new ArrayList<TEstimateConfigBean>();
		for (HashMap<String, Object> hashMap : list) {
			TEstimateConfigBean bean = new TEstimateConfigBean();
			bean.setCONFIG_ID(hashMap.get("CONFIG_ID")!=null?hashMap.get("CONFIG_ID").toString():null);
			bean.setSEL_TABLE_NAME(hashMap.get("SEL_TABLE_NAME")!=null?hashMap.get("SEL_TABLE_NAME").toString():null);
			bean.setSEL_TABLE_ORDERNO_BUS(hashMap.get("SEL_TABLE_ORDERNO_BUS")!=null?hashMap.get("SEL_TABLE_ORDERNO_BUS").toString():null);
			bean.setSEL_TABLE_ORDERNO_EST(hashMap.get("SEL_TABLE_ORDERNO_EST")!=null?hashMap.get("SEL_TABLE_ORDERNO_EST").toString():null);
			bean.setSEL_PRODUCT_NO_BUS(hashMap.get("SEL_PRODUCT_NO_BUS")!=null?hashMap.get("SEL_PRODUCT_NO_BUS").toString():null);
			bean.setSEL_PRODUCT_NO_EST(hashMap.get("SEL_PRODUCT_NO_EST")!=null?hashMap.get("SEL_PRODUCT_NO_EST").toString():null);
			bean.setSEL_BUSWD_NO(hashMap.get("SEL_BUSWD_NO")!=null?hashMap.get("SEL_BUSWD_NO").toString():null);
			bean.setSEL_ESTWD_VALUE(hashMap.get("SEL_ESTWD_VALUE")!=null?hashMap.get("SEL_ESTWD_VALUE").toString():null);
			bean.setSEL_TABLE_RENO_BUS(hashMap.get("SEL_TABLE_RENO_BUS")!=null?hashMap.get("SEL_TABLE_RENO_BUS").toString():null);
			bean.setSEL_TABLE_RENO_EST(hashMap.get("SEL_TABLE_RENO_EST")!=null?hashMap.get("SEL_TABLE_RENO_EST").toString():null);
			bean.setSEL_ESTWD_NO(hashMap.get("SEL_ESTWD_NO")!=null?hashMap.get("SEL_ESTWD_NO").toString():null);
			bean.setSEL_WDENG_NAME(hashMap.get("SEL_WDENG_NAME") != null ? hashMap.get("SEL_WDENG_NAME").toString(): null);
			bean.setSEL_WHERE(hashMap.get("SEL_WHERE")!=null?hashMap.get("SEL_WHERE").toString():null);
			bean.setSEL_START_TIME(hashMap.get("SEL_START_TIME")!=null?hashMap.get("SEL_START_TIME").toString():null);
			bean.setSEL_END_TIME(hashMap.get("SEL_END_TIME")!=null?hashMap.get("SEL_END_TIME").toString():null);
			bean.setREMARK(hashMap.get("REMARK")!=null?hashMap.get("REMARK").toString():null);
			beans.add(bean);
		}
		return beans;

	}
}

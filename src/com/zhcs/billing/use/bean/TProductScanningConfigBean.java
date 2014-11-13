package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 容器产品扫描方式配置表
 * @author hefa
 *
 */
public class TProductScanningConfigBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7866939088216040168L;
	public TProductScanningConfigBean() {

	}
	public TProductScanningConfigBean(String iD, String wD_CODE,
			String wD_NAME, String sCANNING_WAY, String rEMARK) {
		super();
		ID = iD;
		WD_CODE = wD_CODE;
		WD_NAME = wD_NAME;
		SCANNING_WAY = sCANNING_WAY;
		REMARK = rEMARK;
	}


	private String ID;//主键id
	private String WD_CODE;//维度编码
	private String WD_NAME;//维度名称
	private String SCANNING_WAY;//扫描方式
	private String REMARK;//备注
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getWD_CODE() {
		return WD_CODE;
	}
	public void setWD_CODE(String wD_CODE) {
		WD_CODE = wD_CODE;
	}
	public String getWD_NAME() {
		return WD_NAME;
	}
	public void setWD_NAME(String wD_NAME) {
		WD_NAME = wD_NAME;
	}
	public String getSCANNING_WAY() {
		return SCANNING_WAY;
	}
	public void setSCANNING_WAY(String sCANNING_WAY) {
		SCANNING_WAY = sCANNING_WAY;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	
	public List<TProductScanningConfigBean> changeToObject(List<HashMap<String, Object>> list) {
		List<TProductScanningConfigBean> beans = new ArrayList<TProductScanningConfigBean>();
		for (HashMap<String, Object> hashMap : list) {
			TProductScanningConfigBean bean = new TProductScanningConfigBean();
			bean.setID(hashMap.get("ID") != null ? hashMap.get("ID").toString() : null);
			bean.setREMARK(hashMap.get("REMARK") != null ? hashMap.get("REMARK").toString() : null);
			bean.setSCANNING_WAY(hashMap.get("SCANNING_WAY") != null ? hashMap.get("SCANNING_WAY").toString() : null);
			bean.setWD_CODE(hashMap.get("WD_CODE") != null ? hashMap.get("WD_CODE").toString() : null);
			bean.setWD_NAME(hashMap.get("WD_NAME") != null ? hashMap.get("WD_NAME").toString() : null);
			beans.add(bean);
		}
		return beans;
	}

	
}

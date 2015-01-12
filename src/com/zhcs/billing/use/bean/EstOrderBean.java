package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 订单表
 * 
 * @author yuqingchao
 * 
 */
public class EstOrderBean {

	private String orderid; // 订单编号
	// private String starttime; // 开始时间
	// private String endtime; // 结束时间
	private Integer speriod; // 采集周期
	private Integer value; // 使用量
	private String timeTip; // 扫描时间点
	private double reality; // 实际金额

	private String objectid;
	private String oname;
	private String packid; // 容器ID
	private String colltime; // 采集日期
	private String vartype; // max,min,avg

	// -----------------数据质量不为100%的取补偿值
	private String dataquality; // 数据质量
	private String compensate; // 补偿值

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	// public String getStarttime() {
	// return starttime;
	// }
	//
	// public void setStarttime(String starttime) {
	// this.starttime = starttime;
	// }
	//
	// public String getEndtime() {
	// return endtime;
	// }
	//
	// public void setEndtime(String endtime) {
	// this.endtime = endtime;
	// }

	public double getReality() {
		return reality;
	}

	public void setReality(double reality) {
		this.reality = reality;
	}

	public void setTimeTip(String timeTip) {
		this.timeTip = timeTip;
	}

	public String getTimeTip() {
		return timeTip;
	}

	/**
	 * 将返回的结果转换成OrderInfoBean集合
	 * 
	 * @param list
	 * @return
	 */
	public static List<EstOrderBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<EstOrderBean> l = new ArrayList<EstOrderBean>();
		for (HashMap<String, Object> estOrder : list) {
			EstOrderBean estOrdersBean = new EstOrderBean();
			estOrdersBean.setPackid(estOrder.get("packid") != null ? estOrder
					.get("packid").toString() : null);
			estOrdersBean.setOrderid(estOrder.get("ORDERID") != null ? estOrder
					.get("ORDERID").toString() : null);
			estOrdersBean.setSperiod(estOrder.get("SPERIOD") != null ? Integer
					.parseInt(estOrder.get("SPERIOD").toString()) : null);
			estOrdersBean
					.setColltime(estOrder.get("colltime") != null ? estOrder
							.get("colltime").toString() : null);
			// estOrdersBean.setStarttime(estOrder.get("STARTTIME")!=null?estOrder.get("STARTTIME").toString():null);
			// estOrdersBean.setEndtime(estOrder.get("ENDTIME")!=null?estOrder.get("ENDTIME").toString():null);
			Integer estValue = estOrder.get("VALUE") != null ? ((int) Double
					.parseDouble(estOrder.get("VALUE").toString())) : null;
			estOrdersBean.setValue(estValue);
			l.add(estOrdersBean);
		}
		return l;
	}

	public void setSperiod(Integer speriod) {
		this.speriod = speriod;
	}

	public Integer getSperiod() {
		return speriod;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getColltime() {
		return colltime;
	}

	public void setColltime(String colltime) {
		this.colltime = colltime;
	}

	public String getVartype() {
		return vartype;
	}

	public void setVartype(String vartype) {
		this.vartype = vartype;
	}

	public String getDataquality() {
		return dataquality;
	}

	public void setDataquality(String dataquality) {
		this.dataquality = dataquality;
	}

	public String getCompensate() {
		return compensate;
	}

	public void setCompensate(String compensate) {
		this.compensate = compensate;
	}

}

package com.zhcs.billing.use.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ������
 * 
 * @author yuqingchao
 * 
 */
public class EstOrderBean{

	private String orderid;		// �������
	private String starttime;	// ��ʼʱ��
	private String endtime;		// ����ʱ��
	private Integer speriod;		// �ɼ�����
	private Integer value;			// ʹ����
	private String timeTip;		// ɨ��ʱ���
	private double reality;		// ʵ�ʽ��

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

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
	 * �����صĽ��ת����OrderInfoBean����
	 * @param list
	 * @return
	 */
	public static List<EstOrderBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<EstOrderBean> l = new ArrayList<EstOrderBean>();
		for (HashMap<String, Object> estOrder : list) {
			EstOrderBean estOrdersBean = new EstOrderBean();
			estOrdersBean.setOrderid(estOrder.get("ORDERID")!=null?estOrder.get("ORDERID").toString():null);
			estOrdersBean.setSperiod(estOrder.get("SPERIOD")!=null?Integer.parseInt(estOrder.get("SPERIOD").toString()):null);
			estOrdersBean.setStarttime(estOrder.get("STARTTIME")!=null?estOrder.get("STARTTIME").toString():null);
			estOrdersBean.setEndtime(estOrder.get("ENDTIME")!=null?estOrder.get("ENDTIME").toString():null);
			Integer estValue=estOrder.get("VALUE")!=null?((int)Double.parseDouble(estOrder.get("VALUE").toString())):null;
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


}

package com.zhcs.billing.use.bean;

/**
 * ������¼
 * 
 * @author yangke
 *
 */
public class OrderInfoRecords {
	private String orderId; // �������
	private String productNo; // ��Ʒ���
	private double monthCost; // ����

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public double getMonthCost() {
		return monthCost;
	}

	public void setMonthCost(double monthCost) {
		this.monthCost = monthCost;
	}

}

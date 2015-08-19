package com.zhcs.billing.use.bean;

/**
 * 订单记录
 * 
 * @author yangke
 *
 */
public class OrderInfoRecords {
	private String orderId; // 订单编号
	private String productNo; // 产品编号
	private double monthCost; // 月租

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

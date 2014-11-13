package com.zhcs.billing.quartz.dao;

import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.OrderInfoBean;

public interface IQueryDao {

	/**
	 * 查询计量数据库有多少数据需要处理(每天查询一次)
	 * @param estOrderBean
	 * @return
	 */
	List<HashMap<String, Object>> getEstOrderNumOne(EstOrderBean estOrderBean);
	/**
	 * 查询计量数据库有多少数据需要处理（每5分钟查询一次）
	 * @param estOrderBean
	 * @return
	 */
	List<HashMap<String, Object>> getEstOrderNumFive(EstOrderBean estOrderBean);
	/**
	 * 查询业务管理平台订单信息表里有多少数据需要处理
	 * @param orderInfoBean
	 * @return
	 */
	List<HashMap<String, Object>> getMonthsOrderNum(OrderInfoBean orderInfoBean);
	
}

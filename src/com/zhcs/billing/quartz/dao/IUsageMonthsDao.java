package com.zhcs.billing.quartz.dao;

import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageMonthsBean;

/**
 * 资费月租中间表Dao
 * 
 * @author yuqingchao
 * 
 */
public interface IUsageMonthsDao {

	/**
	 * 修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
	 * 
	 * @param usageAccountBean
	 * @param eachMachine
	 * @param theMachineStart
	 */
	public void SetData(UsageMonthsBean usageMonthsBean, int eachMachine,
			int theMachineStart, List<OrderInfoBean> orders);

	/**
	 * 查找符合扫描方式的数据
	 * 
	 * @param usageAccountBean
	 * @return
	 */
	List<UsageMonthsBean> listUsageMonths(UsageMonthsBean usageMonthsBean);

	/**
	 * 修改STATE 为 threadName ，表示此线程正在扫描当中
	 * 
	 * @param usageAccountBean
	 * @param threadOneNum
	 * @return
	 */
	public int scanning(UsageMonthsBean usageMonthsBean, int threadOneNum);

	/**
	 * 修改STATE 为 完成，表示此线程已经扫描当完成
	 * 
	 * @param usageAccountBean
	 * @param ClassName
	 */
	void updateUsageMonthsState(UsageMonthsBean usageMonthsBean,
			String ClassName);

	/**
	 * 判断数据是否都已经处理完成，清空T_USAGE_ACCOUNT表为月租的数据
	 * 
	 * @param usageAccountBean
	 * @param treatmentNum
	 */
	public void deleteDate(UsageMonthsBean usageMonthsBean, int treatmentNum);

	/**
	 * 将查询出的订单数据写入表T_USAGE_MONTHS里
	 * 
	 * @param usageMonthsBean
	 */
	// void insertUsageMonths(UsageMonthsBean usageMonthsBean);

}

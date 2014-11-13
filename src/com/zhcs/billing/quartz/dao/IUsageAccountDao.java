package com.zhcs.billing.quartz.dao;

import java.util.List;

import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageAccountBean;
/**
 * 资费使用量表Dao
 * @author yuqingchao
 *
 */
public interface IUsageAccountDao {
	
	/**
	 * 修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
	 * @param usageAccountBean
	 * @param eachMachine
	 * @param theMachineStart
	 */
//	public void SetData(UsageAccountBean usageAccountBean,int eachMachine,int theMachineStart);
	
	/**
	 * 查找符合扫描方式的数据
	 * @param usageAccountBean
	 * @return
	 */
	public List<UsageAccountBean> listUsageAccount(UsageAccountBean usageAccountBean);
	/**
	 * 入库资费使用量表
	 * @param usageAccountBean
	 * @return
	 */
//	public void insertUsageAccount(UsageAccountBean usageAccountBean);
	
	/**
	 * 修改STATE 为 threadName ，表示此线程正在扫描当中
	 * @param usageAccountBean
	 * @param threadOneNum
	 * @return
	 */
	public int scanning(UsageAccountBean usageAccountBean,int threadOneNum);
	
	/**
	 * 修改STATE 为 完成，表示此线程已经扫描当完成
	 * @param usageAccountBean
	 * @param ClassName
	 */
	public void updateUsageAccountState(UsageAccountBean usageAccountBean,String ClassName);
	
	/**
	 * 判断数据是否都已经处理完成，清空T_USAGE_ACCOUNT表为月租的数据
	 * @param usageAccountBean
	 * @param treatmentNum
	 */
	public void deleteDate(UsageAccountBean usageAccountBean,int treatmentNum);
	/**
	 * 按扫描方式查询资费使用量表有多少数据需要处理
	 * @param usageAccountBean
	 * @return
	 */
//	public int GetAccountFeeNum(UsageAccountBean usageAccountBean);

	/**
	 * 将服务器需要处理的数据放入T_USAGE_ACCOUNT表
	 * @param usageAccountBean
	 * @param eachMachine
	 * @param theMachineStart
	 * @param orders
	 */
	void SetData(UsageAccountBean usageAccountBean, int eachMachine,
			int theMachineStart, String timeTip, List<EstOrderBean> orders);

	
}

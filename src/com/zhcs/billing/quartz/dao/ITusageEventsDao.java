package com.zhcs.billing.quartz.dao;

import java.util.List;

import com.zhcs.billing.use.bean.TUsageEventsBean;
import com.zhcs.billing.use.bean.UsageMonthlyBean;

/**
 * 周期费用扣除详单管理类
 * 
 * @author yuqingchao
 *
 */
public interface ITusageEventsDao {

	/**
	 * 查询需要处理的月租数据
	 * 
	 * @param usageMonthlyBean
	 *            资费月租表Bean
	 * @return
	 */
	public List<UsageMonthlyBean> listRate(UsageMonthlyBean usageMonthlyBean);

	/**
	 * 将数据放入详单表
	 * 
	 * @param tUsageEventsBean
	 *            详单表Bean
	 * @param dateStr
	 *            日期
	 * @param billingID
	 *            接口之间的标识符
	 * @return
	 */
	public int insertTUsageEvents(TUsageEventsBean tUsageEventsBean,
			String dateStr, String billingID);

}

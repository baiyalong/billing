package com.zhcs.billing.quartz.dao;

import java.util.List;

import com.zhcs.billing.use.bean.TUsageEventsBean;
import com.zhcs.billing.use.bean.UsageMonthlyBean;

/**
 * ���ڷ��ÿ۳��굥������
 * 
 * @author yuqingchao
 *
 */
public interface ITusageEventsDao {

	/**
	 * ��ѯ��Ҫ�������������
	 * 
	 * @param usageMonthlyBean
	 *            �ʷ������Bean
	 * @return
	 */
	public List<UsageMonthlyBean> listRate(UsageMonthlyBean usageMonthlyBean);

	/**
	 * �����ݷ����굥��
	 * 
	 * @param tUsageEventsBean
	 *            �굥��Bean
	 * @param dateStr
	 *            ����
	 * @param billingID
	 *            �ӿ�֮��ı�ʶ��
	 * @return
	 */
	public int insertTUsageEvents(TUsageEventsBean tUsageEventsBean,
			String dateStr, String billingID);

}

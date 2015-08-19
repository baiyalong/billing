package com.zhcs.billing.quartz.dao;

import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageMonthsBean;

/**
 * �ʷ������м��Dao
 * 
 * @author yuqingchao
 * 
 */
public interface IUsageMonthsDao {

	/**
	 * �޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
	 * 
	 * @param usageAccountBean
	 * @param eachMachine
	 * @param theMachineStart
	 */
	public void SetData(UsageMonthsBean usageMonthsBean, int eachMachine,
			int theMachineStart, List<OrderInfoBean> orders);

	/**
	 * ���ҷ���ɨ�跽ʽ������
	 * 
	 * @param usageAccountBean
	 * @return
	 */
	List<UsageMonthsBean> listUsageMonths(UsageMonthsBean usageMonthsBean);

	/**
	 * �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱��
	 * 
	 * @param usageAccountBean
	 * @param threadOneNum
	 * @return
	 */
	public int scanning(UsageMonthsBean usageMonthsBean, int threadOneNum);

	/**
	 * �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱���
	 * 
	 * @param usageAccountBean
	 * @param ClassName
	 */
	void updateUsageMonthsState(UsageMonthsBean usageMonthsBean,
			String ClassName);

	/**
	 * �ж������Ƿ��Ѿ�������ɣ����T_USAGE_ACCOUNT��Ϊ���������
	 * 
	 * @param usageAccountBean
	 * @param treatmentNum
	 */
	public void deleteDate(UsageMonthsBean usageMonthsBean, int treatmentNum);

	/**
	 * ����ѯ���Ķ�������д���T_USAGE_MONTHS��
	 * 
	 * @param usageMonthsBean
	 */
	// void insertUsageMonths(UsageMonthsBean usageMonthsBean);

}

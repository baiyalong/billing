package com.zhcs.billing.quartz.dao;

import java.util.List;

import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageAccountBean;
/**
 * �ʷ�ʹ������Dao
 * @author yuqingchao
 *
 */
public interface IUsageAccountDao {
	
	/**
	 * �޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
	 * @param usageAccountBean
	 * @param eachMachine
	 * @param theMachineStart
	 */
//	public void SetData(UsageAccountBean usageAccountBean,int eachMachine,int theMachineStart);
	
	/**
	 * ���ҷ���ɨ�跽ʽ������
	 * @param usageAccountBean
	 * @return
	 */
	public List<UsageAccountBean> listUsageAccount(UsageAccountBean usageAccountBean);
	/**
	 * ����ʷ�ʹ������
	 * @param usageAccountBean
	 * @return
	 */
//	public void insertUsageAccount(UsageAccountBean usageAccountBean);
	
	/**
	 * �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱��
	 * @param usageAccountBean
	 * @param threadOneNum
	 * @return
	 */
	public int scanning(UsageAccountBean usageAccountBean,int threadOneNum);
	
	/**
	 * �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱���
	 * @param usageAccountBean
	 * @param ClassName
	 */
	public void updateUsageAccountState(UsageAccountBean usageAccountBean,String ClassName);
	
	/**
	 * �ж������Ƿ��Ѿ�������ɣ����T_USAGE_ACCOUNT��Ϊ���������
	 * @param usageAccountBean
	 * @param treatmentNum
	 */
	public void deleteDate(UsageAccountBean usageAccountBean,int treatmentNum);
	/**
	 * ��ɨ�跽ʽ��ѯ�ʷ�ʹ�������ж���������Ҫ����
	 * @param usageAccountBean
	 * @return
	 */
//	public int GetAccountFeeNum(UsageAccountBean usageAccountBean);

	/**
	 * ����������Ҫ��������ݷ���T_USAGE_ACCOUNT��
	 * @param usageAccountBean
	 * @param eachMachine
	 * @param theMachineStart
	 * @param orders
	 */
	void SetData(UsageAccountBean usageAccountBean, int eachMachine,
			int theMachineStart, String timeTip, List<EstOrderBean> orders);

	
}

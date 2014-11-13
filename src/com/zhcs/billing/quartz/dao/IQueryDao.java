package com.zhcs.billing.quartz.dao;

import java.util.HashMap;
import java.util.List;

import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.OrderInfoBean;

public interface IQueryDao {

	/**
	 * ��ѯ�������ݿ��ж���������Ҫ����(ÿ���ѯһ��)
	 * @param estOrderBean
	 * @return
	 */
	List<HashMap<String, Object>> getEstOrderNumOne(EstOrderBean estOrderBean);
	/**
	 * ��ѯ�������ݿ��ж���������Ҫ����ÿ5���Ӳ�ѯһ�Σ�
	 * @param estOrderBean
	 * @return
	 */
	List<HashMap<String, Object>> getEstOrderNumFive(EstOrderBean estOrderBean);
	/**
	 * ��ѯҵ�����ƽ̨������Ϣ�����ж���������Ҫ����
	 * @param orderInfoBean
	 * @return
	 */
	List<HashMap<String, Object>> getMonthsOrderNum(OrderInfoBean orderInfoBean);
	
}

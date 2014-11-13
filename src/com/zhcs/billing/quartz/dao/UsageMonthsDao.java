package com.zhcs.billing.quartz.dao;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageMonthsBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;

public class UsageMonthsDao implements IUsageMonthsDao {

	private static Logger log = LoggerFactory.getLogger(UsageMonthsDao.class);
	private static LoggerUtil loggerUtil = LoggerUtil.getLogger(UsageMonthsDao.class);
	BillingBaseDao billingBaseDao;
	BaseDao baseDao;
	
	public BillingBaseDao GetBillingBaseDao(){
		if(billingBaseDao==null)
		{
			billingBaseDao=new BillingBaseDao();
		}
		return billingBaseDao;
	}
	public BaseDao GetBaseDao(){
		if(baseDao==null)
		{
			baseDao=new BaseDao();
		}
		return baseDao;
	}
	@Override
	public void SetData(UsageMonthsBean usageMonthsBean, int eachMachine,
			int theMachineStart,List<OrderInfoBean> orders) {
		int n = 0;
		String sql = VariableConfigManager.Months_SetDataSql;
		for(int i=theMachineStart;i<(eachMachine+theMachineStart);i++){
			List params=new ArrayList();
			params.add(orders.get(i).getCUSTOMER_ID());
			params.add(orders.get(i).getORDER_ID());
			params.add(usageMonthsBean.getFIXED_OR_MONTHLY());
			params.add(usageMonthsBean.getSCANNING_WAY());
			params.add(usageMonthsBean.getSERVERS_NO_STATE());
			params.add(orders.get(i).getPRODUCT_CATEGORY());
			int num = 0;
			try {
				num = GetBillingBaseDao().doSaveOrUpdate(sql, params);
				if(num != 0){
					log.info("UsageMonthsDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageMonthsBean.getSERVERS_NO_STATE()+"�ɹ�����");
					loggerUtil.info("UsageMonthsDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageMonthsBean.getSERVERS_NO_STATE()+"�ɹ�����");
					n++;
				}
			} catch (Exception e) {
				log.error("UsageMonthsDao Class SetData Method",e);
				loggerUtil.error("UsageMonthsDao Class SetData Method",e);
			}
			params=null;
			if(num==0){
				log.info("UsageMonthsDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageMonthsBean.getSERVERS_NO_STATE()+"ʧ�ܣ���");
				loggerUtil.info("UsageMonthsDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageMonthsBean.getSERVERS_NO_STATE()+"ʧ�ܣ���");
				return;
			}
		}
		log.info("���������Ϊ��"+usageMonthsBean.getSERVERS_NO_STATE()+"�����ݹ��У�"+n);
		loggerUtil.info("���������Ϊ��"+usageMonthsBean.getSERVERS_NO_STATE()+"�����ݹ��У�"+n);
	}

	@Override
	public int scanning(UsageMonthsBean usageMonthsBean, int threadOneNum) {
		String sql = VariableConfigManager.Months_scanningSql;
		List params=new ArrayList();
		params.add(usageMonthsBean.getSTATE());
		params.add(usageMonthsBean.getCOMPLETE_STATE());
		params.add(usageMonthsBean.getSCANNING_WAY());
		params.add(usageMonthsBean.getFIXED_OR_MONTHLY());
		params.add(usageMonthsBean.getSERVERS_NO_STATE());
		params.add(threadOneNum);
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if(stateNum!=0){
				log.info("UsageMonthsDao Class Scanning Method:�޸�T_USAGE_MONTHS ��STATE Ϊ "+usageMonthsBean.getSTATE()+"�ɹ�����");
				loggerUtil.info("UsageMonthsDao Class Scanning Method:�޸�T_USAGE_MONTHS ��STATE Ϊ "+usageMonthsBean.getSTATE()+"�ɹ�����");
			}
		} catch (Exception e) {
			log.error("UsageMonthsDao Class Scanning Method ", e);
			loggerUtil.error("UsageMonthsDao Class Scanning Method ", e);
			e.printStackTrace();
		}
		params=null;
		if(stateNum==0){
			log.info("UsageMonthsDao Class Scanning Method:�޸�T_USAGE_MONTHS ��STATE Ϊ "+usageMonthsBean.getSTATE()+"ʧ�ܣ���");
			loggerUtil.info("UsageMonthsDao Class Scanning Method:�޸�T_USAGE_MONTHS ��STATE Ϊ "+usageMonthsBean.getSTATE()+"ʧ�ܣ���");
		}
		return stateNum;
	}

	@Override
	public void updateUsageMonthsState(UsageMonthsBean usageMonthsBean,
			String ClassName) {
		String sql = VariableConfigManager.Months_updateUsageMonthsState;
		List params = new ArrayList();
		params.add(usageMonthsBean.getCOMPLETE_STATE());
		params.add(usageMonthsBean.getSCANNING_WAY());
		params.add(usageMonthsBean.getFIXED_OR_MONTHLY());
		params.add(usageMonthsBean.getSTATE());
		params.add(usageMonthsBean.getSERVERS_NO_STATE());
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			log.info(" UsageMonthsDao Class run updateUsageMonthsState Method �޸�  T_USAGE_MONTHS �� STATE = ���   �ɹ�.���޸������ݣ�"+stateNum);
			loggerUtil.info(" UsageMonthsDao Class run updateUsageMonthsState Method �޸�  T_USAGE_MONTHS �� STATE = ���   �ɹ�.���޸������ݣ�"+stateNum);
		} catch (Exception e) {
			loggerUtil.error(""+ClassName+" Class run updateUsageMonthsState Method ", e);
		}
		params=null;
		if (stateNum == 0) {
			log.error(" UsageMonthsDao Class run updateUsageMonthsState Method �޸�  T_USAGE_MONTHS �� STATE = ���   ʧ�ܣ���");
			loggerUtil.error(" UsageMonthsDao Class run updateUsageMonthsState Method �޸�  T_USAGE_MONTHS �� STATE = ���   ʧ�ܣ���");
			return;  // �޸�ʧ��
		}
	}
	
	@Override
	public void deleteDate(UsageMonthsBean usageMonthsBean, int tTNum) {
		try{
//			String sql = VariableConfigManager.Months_deleteDate_select;
//			List params=new ArrayList();
//			params.add(usageMonthsBean.getSCANNING_WAY());
//			params.add(usageMonthsBean.getFIXED_OR_MONTHLY());
//			params.add(usageMonthsBean.getSTATE());
//			params.add(usageMonthsBean.getCOMPLETE_STATE());
//			List<HashMap<String, Object>> list = GetBillingBaseDao().doSelect(sql, params);
//			int num = Integer.parseInt(list.get(0).get("NUM").toString()); // �����̹߳��д����˶�������
//			log.info("��ѯT_USAGE_MONTHS���߳�:"+usageMonthsBean.getSTATE() +" ���д�����:"+num+" ��״̬Ϊ:"+usageMonthsBean.getCOMPLETE_STATE()+"���ݣ�Ӧ�ô������Ϊ��"+tTNum);
//			loggerUtil.info("��ѯT_USAGE_MONTHS���߳�:"+usageMonthsBean.getSTATE() +" ���д�����:"+num+" ��״̬Ϊ:"+usageMonthsBean.getCOMPLETE_STATE()+"���ݣ�Ӧ�ô������Ϊ��"+tTNum);
//			if(num==tTNum){
				String sqlD = VariableConfigManager.Months_deleteDate_delete;
				List paramsD = new ArrayList();
				paramsD.add(usageMonthsBean.getSCANNING_WAY());
				paramsD.add(usageMonthsBean.getFIXED_OR_MONTHLY());
				paramsD.add(usageMonthsBean.getSTATE());
				paramsD.add(usageMonthsBean.getCOMPLETE_STATE());
				GetBillingBaseDao().doDelete(sqlD,paramsD);
				log.info("�ɹ�ɾ���߳�:"+usageMonthsBean.getSTATE() +" �Ѿ�������ɵ����� T_USAGE_MONTHS��,ɾ������Ϊ:"+tTNum+"״̬Ϊ:"+usageMonthsBean.getCOMPLETE_STATE());
				loggerUtil.info("�ɹ�ɾ���߳�:"+usageMonthsBean.getSTATE() +" �Ѿ�������ɵ����� T_USAGE_MONTHS��,ɾ������Ϊ:"+tTNum+"״̬Ϊ:"+usageMonthsBean.getCOMPLETE_STATE());
//			}
				paramsD=null;
		}catch (Exception e) {
			log.error("UsageMonthsDao Class deleteT Method ! ", e);
			loggerUtil.error("UsageMonthsDao Class deleteT Method ! ", e);
		}
		
	}


	@Override
	public List<UsageMonthsBean> listUsageMonths(
			UsageMonthsBean usageMonthsBean) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<UsageMonthsBean> l = new ArrayList<UsageMonthsBean>();
		String sql = VariableConfigManager.Months_listUsageMonthsSql;
		List params=new ArrayList();
		params.add(usageMonthsBean.getSCANNING_WAY());
		params.add(usageMonthsBean.getFIXED_OR_MONTHLY());
		params.add(usageMonthsBean.getSERVERS_NO_STATE());
		params.add(usageMonthsBean.getSTATE());
		list = GetBillingBaseDao().doSelect(sql, params);
		l = usageMonthsBean.changeToObject(list);
		params=null;
		list=null;
		return l;
	}

}

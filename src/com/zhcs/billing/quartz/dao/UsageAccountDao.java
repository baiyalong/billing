package com.zhcs.billing.quartz.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.UsageAccountBean;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;

public class UsageAccountDao implements IUsageAccountDao {

	private static Logger log = LoggerFactory.getLogger(UsageAccountDao.class);
	private static LoggerUtil loggerUtil = LoggerUtil.getLogger(UsageAccountDao.class);
	BillingBaseDao billingBaseDao;
	
	public BillingBaseDao GetBillingBaseDao(){
		if(billingBaseDao==null)
		{
			billingBaseDao=new BillingBaseDao();
		}
		return billingBaseDao;
	}
	
	@Override
	public void SetData(UsageAccountBean usageAccountBean, int eachMachine,
			int theMachineStart, String collDate, List<EstOrderBean> estOrders) {
		int n = 0;
		String sql = VariableConfigManager.Account_SetDataSql;
		for(int i=theMachineStart;i<(eachMachine+theMachineStart);i++){
			List params=new ArrayList();
			params.add(Common.createBillingID());
			params.add(estOrders.get(i).getOrderid());
			params.add(collDate);
			params.add(usageAccountBean.getSCANNING_WAY());
			params.add(usageAccountBean.getSERVERS_NO_STATE());
			int num = 0;
			try {
				num = GetBillingBaseDao().doSaveOrUpdate(sql, params);
				if(num != 0){
					log.info("UsageAccountDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageAccountBean.getSERVERS_NO_STATE()+"�ɹ�����");
					loggerUtil.info("UsageAccountDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageAccountBean.getSERVERS_NO_STATE()+"�ɹ�����");
					n++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("UsageAccountDao Class SetData Method",e);
				loggerUtil.error("UsageAccountDao Class SetData Method",e);
			}
			if(num==0){
				log.info("UsageAccountDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageAccountBean.getSERVERS_NO_STATE()+"ʧ�ܣ���");
				loggerUtil.info("UsageAccountDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"+ usageAccountBean.getSERVERS_NO_STATE()+"ʧ�ܣ���");
				return;
			}
			params=null;
		}
		
		log.info("���������Ϊ��"+usageAccountBean.getSERVERS_NO_STATE()+"�����ݹ��У�"+n);
		loggerUtil.info("���������Ϊ��"+usageAccountBean.getSERVERS_NO_STATE()+"�����ݹ��У�"+n);
	}


	@Override
	public int scanning(UsageAccountBean usageAccountBean, int threadOneNum) {
//		String sql = "UPDATE T_USAGE_ACCOUNT SET STATE = ?,COMPLETE_STATE = ? WHERE ID IN (SELECT T.ID FROM(SELECT ID FROM T_USAGE_ACCOUNT WHERE SCANNING_WAY = ? AND SERVERS_NO_STATE = ? LIMIT 0,?)AS T)";
		String sql = VariableConfigManager.Account_scanningSql;
		List params=new ArrayList();
		params.add(usageAccountBean.getSTATE());
		params.add(usageAccountBean.getCOMPLETE_STATE());
		params.add(usageAccountBean.getSCANNING_WAY());
		params.add(usageAccountBean.getSERVERS_NO_STATE());
		params.add(threadOneNum);
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if(stateNum!=0){
				log.info("UsageAccountDao Class Scanning Method:�޸�T_USAGE_ACCOUNT ��STATE Ϊ "+usageAccountBean.getSTATE()+"�ɹ�����");
				loggerUtil.info("UsageAccountDao Class Scanning Method:�޸�T_USAGE_ACCOUNT ��STATE Ϊ "+usageAccountBean.getSTATE()+"�ɹ�����");
			}
		} catch (Exception e) {
			log.error("UsageAccountDao Class Scanning Method ", e);
			loggerUtil.error("UsageAccountDao Class Scanning Method ", e);
			e.printStackTrace();
		}
		if(stateNum==0){
			log.info("UsageAccountDao Class Scanning Method:�޸�T_USAGE_ACCOUNT ��STATE Ϊ "+usageAccountBean.getSTATE()+"ʧ�ܣ���");
			loggerUtil.info("UsageAccountDao Class Scanning Method:�޸�T_USAGE_ACCOUNT ��STATE Ϊ "+usageAccountBean.getSTATE()+"ʧ�ܣ���");
		}
		params=null;
		return stateNum;
	}

	@Override
	public void updateUsageAccountState(UsageAccountBean usageAccountBean,
			String ClassName) {
//		String sql = "UPDATE T_USAGE_ACCOUNT SET COMPLETE_STATE = ? WHERE SCANNING_WAY = ? AND STATE = ? AND SERVERS_NO_STATE = ?";
		String sql = VariableConfigManager.Account_updateUsageAccountState;
		List params = new ArrayList();
		params.add(usageAccountBean.getCOMPLETE_STATE());
		params.add(usageAccountBean.getSCANNING_WAY());
		params.add(usageAccountBean.getSTATE());
		params.add(usageAccountBean.getSERVERS_NO_STATE());
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			log.info(" UsageAccountDao Class run updateUsageAccountState Method �޸�  T_USAGE_ACCOUNT �� STATE = ���   �ɹ�.���޸������ݣ�"+stateNum);
			loggerUtil.info(" UsageAccountDao Class run updateUsageAccountState Method �޸�  T_USAGE_ACCOUNT �� STATE = ���   �ɹ�.���޸������ݣ�"+stateNum);
		} catch (Exception e) {
			log.error(""+ClassName+" Class run updateUsageAccountState Method ", e);
			loggerUtil.error(""+ClassName+" Class run updateUsageAccountState Method ", e);
		}
		params=null;
		if (stateNum == 0) {
			log.error(" UsageAccountDao Class run updateUsageAccountState Method �޸�  T_USAGE_ACCOUNT �� STATE = ���   ʧ�ܣ���");
			loggerUtil.error(" UsageAccountDao Class run updateUsageAccountState Method �޸�  T_USAGE_ACCOUNT �� STATE = ���   ʧ�ܣ���");
			return;  // �޸�ʧ��
		}
	}
	
	@Override
	public void deleteDate(UsageAccountBean usageAccountBean, int tTNum) {
		try{
//			String sql = "SELECT COUNT(*) NUM FROM T_USAGE_ACCOUNT WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
//			String sql = VariableConfigManager.Account_deleteDate_select;
//			List params=new ArrayList();
//			params.add(usageAccountBean.getSCANNING_WAY());
//			params.add(usageAccountBean.getSTATE());
//			params.add(usageAccountBean.getCOMPLETE_STATE());
//			List<HashMap<String, Object>> list = GetBillingBaseDao().doSelect(sql, params);
//			int num = Integer.parseInt(list.get(0).get("NUM").toString()); // �����̹߳��д����˶�������
//			log.info("��ѯT_USAGE_ACCOUNT���߳�:"+usageAccountBean.getSTATE() +" ���д�����:"+num+" ��״̬Ϊ:"+usageAccountBean.getCOMPLETE_STATE()+"���ݣ�Ӧ�ô������Ϊ��"+tTNum);
//			loggerUtil.info("��ѯT_USAGE_ACCOUNT���߳�:"+usageAccountBean.getSTATE() +" ���д�����:"+num+" ��״̬Ϊ:"+usageAccountBean.getCOMPLETE_STATE()+"���ݣ�Ӧ�ô������Ϊ��"+tTNum);
//			if(num==tTNum){
//				String sqlD = "DELETE FROM T_USAGE_ACCOUNT WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
				String sqlD = VariableConfigManager.Account_deleteDate_delete;
				List paramsD = new ArrayList();
				paramsD.add(usageAccountBean.getSCANNING_WAY());
				paramsD.add(usageAccountBean.getSTATE());
				paramsD.add(usageAccountBean.getCOMPLETE_STATE());
				GetBillingBaseDao().doDelete(sqlD,paramsD);
				log.info("�ɹ�ɾ���߳�:"+usageAccountBean.getSTATE() +" �Ѿ�������ɵ����� T_USAGE_ACCOUNT��,ɾ������Ϊ:"+tTNum+"״̬Ϊ:"+usageAccountBean.getCOMPLETE_STATE());
				loggerUtil.info("�ɹ�ɾ���߳�:"+usageAccountBean.getSTATE() +" �Ѿ�������ɵ����� T_USAGE_ACCOUNT��,ɾ������Ϊ:"+tTNum+"״̬Ϊ:"+usageAccountBean.getCOMPLETE_STATE());
//			}
				paramsD=null;
		}catch (Exception e) {
			log.error("UsageAccountDao Class deleteT Method ! ", e);
			loggerUtil.error("UsageAccountDao Class deleteT Method ! ", e);
		}
		
	}


	@Override
	public List<UsageAccountBean> listUsageAccount(
			UsageAccountBean usageAccountBean) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<UsageAccountBean> l = new ArrayList<UsageAccountBean>();
//		String sql = "SELECT * FROM T_USAGE_ACCOUNT T WHERE T.SCANNING_WAY = ? AND T.SERVERS_NO_STATE = ? AND T.STATE = ?";
		String sql = VariableConfigManager.Account_listUsageAccountSql;
		List params=new ArrayList();
		params.add(usageAccountBean.getSCANNING_WAY());
		params.add(usageAccountBean.getSERVERS_NO_STATE());
		params.add(usageAccountBean.getSTATE());
		list = GetBillingBaseDao().doSelect(sql, params);
		l = usageAccountBean.changeToObject(list);
		params=null;
		list=null;
		return l;
	}

}

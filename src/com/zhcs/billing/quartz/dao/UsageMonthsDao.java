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
					log.info("UsageMonthsDao Class SetData Method：修改服务器所需要处理的数据状态为服务器编号:"+ usageMonthsBean.getSERVERS_NO_STATE()+"成功！！");
					loggerUtil.info("UsageMonthsDao Class SetData Method：修改服务器所需要处理的数据状态为服务器编号:"+ usageMonthsBean.getSERVERS_NO_STATE()+"成功！！");
					n++;
				}
			} catch (Exception e) {
				log.error("UsageMonthsDao Class SetData Method",e);
				loggerUtil.error("UsageMonthsDao Class SetData Method",e);
			}
			params=null;
			if(num==0){
				log.info("UsageMonthsDao Class SetData Method：修改服务器所需要处理的数据状态为服务器编号:"+ usageMonthsBean.getSERVERS_NO_STATE()+"失败！！");
				loggerUtil.info("UsageMonthsDao Class SetData Method：修改服务器所需要处理的数据状态为服务器编号:"+ usageMonthsBean.getSERVERS_NO_STATE()+"失败！！");
				return;
			}
		}
		log.info("服务器编号为："+usageMonthsBean.getSERVERS_NO_STATE()+"的数据共有："+n);
		loggerUtil.info("服务器编号为："+usageMonthsBean.getSERVERS_NO_STATE()+"的数据共有："+n);
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
				log.info("UsageMonthsDao Class Scanning Method:修改T_USAGE_MONTHS 表STATE 为 "+usageMonthsBean.getSTATE()+"成功！！");
				loggerUtil.info("UsageMonthsDao Class Scanning Method:修改T_USAGE_MONTHS 表STATE 为 "+usageMonthsBean.getSTATE()+"成功！！");
			}
		} catch (Exception e) {
			log.error("UsageMonthsDao Class Scanning Method ", e);
			loggerUtil.error("UsageMonthsDao Class Scanning Method ", e);
			e.printStackTrace();
		}
		params=null;
		if(stateNum==0){
			log.info("UsageMonthsDao Class Scanning Method:修改T_USAGE_MONTHS 表STATE 为 "+usageMonthsBean.getSTATE()+"失败！！");
			loggerUtil.info("UsageMonthsDao Class Scanning Method:修改T_USAGE_MONTHS 表STATE 为 "+usageMonthsBean.getSTATE()+"失败！！");
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
			log.info(" UsageMonthsDao Class run updateUsageMonthsState Method 修改  T_USAGE_MONTHS 表 STATE = 完成   成功.共修改了数据："+stateNum);
			loggerUtil.info(" UsageMonthsDao Class run updateUsageMonthsState Method 修改  T_USAGE_MONTHS 表 STATE = 完成   成功.共修改了数据："+stateNum);
		} catch (Exception e) {
			loggerUtil.error(""+ClassName+" Class run updateUsageMonthsState Method ", e);
		}
		params=null;
		if (stateNum == 0) {
			log.error(" UsageMonthsDao Class run updateUsageMonthsState Method 修改  T_USAGE_MONTHS 表 STATE = 完成   失败！！");
			loggerUtil.error(" UsageMonthsDao Class run updateUsageMonthsState Method 修改  T_USAGE_MONTHS 表 STATE = 完成   失败！！");
			return;  // 修改失败
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
//			int num = Integer.parseInt(list.get(0).get("NUM").toString()); // 所有线程共有处理了多少数据
//			log.info("查询T_USAGE_MONTHS表，线程:"+usageMonthsBean.getSTATE() +" 共有处理了:"+num+" 条状态为:"+usageMonthsBean.getCOMPLETE_STATE()+"数据，应该处理的数为："+tTNum);
//			loggerUtil.info("查询T_USAGE_MONTHS表，线程:"+usageMonthsBean.getSTATE() +" 共有处理了:"+num+" 条状态为:"+usageMonthsBean.getCOMPLETE_STATE()+"数据，应该处理的数为："+tTNum);
//			if(num==tTNum){
				String sqlD = VariableConfigManager.Months_deleteDate_delete;
				List paramsD = new ArrayList();
				paramsD.add(usageMonthsBean.getSCANNING_WAY());
				paramsD.add(usageMonthsBean.getFIXED_OR_MONTHLY());
				paramsD.add(usageMonthsBean.getSTATE());
				paramsD.add(usageMonthsBean.getCOMPLETE_STATE());
				GetBillingBaseDao().doDelete(sqlD,paramsD);
				log.info("成功删除线程:"+usageMonthsBean.getSTATE() +" 已经处理完成的数据 T_USAGE_MONTHS表,删除个数为:"+tTNum+"状态为:"+usageMonthsBean.getCOMPLETE_STATE());
				loggerUtil.info("成功删除线程:"+usageMonthsBean.getSTATE() +" 已经处理完成的数据 T_USAGE_MONTHS表,删除个数为:"+tTNum+"状态为:"+usageMonthsBean.getCOMPLETE_STATE());
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

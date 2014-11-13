package com.zhcs.billing.quartz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.dao.QueryDao;
import com.zhcs.billing.quartz.dao.UsageAccountDao;
import com.zhcs.billing.use.bean.EstOrderBean;
import com.zhcs.billing.use.bean.UsageAccountBean;
import com.zhcs.billing.util.EnvironmentUtils;
import com.zhcs.billing.util.LoggerUtil;
import com.zhcs.billing.util.VariableConfigManager;

/**
 * 实扣费用-处理（虚机资源按使用量每天扫描一次）
 * @author yuqingchao
 *
 */
public class UsageAmountVMTreatment extends Task implements
		Job {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(UsageAmountVMTreatment.class);
	private static Logger log = LoggerFactory.getLogger(UsageAmountVMTreatment.class);

	@Override
	public void execute(HashMap map) {
		
		Date date = new Date(new Date().getTime()-VariableConfigManager.Delayed_VMTreatment);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeTip = sdf.format(date);
		
		try{
			/***  查看需要处理的订单数据   ***/
			String ip = EnvironmentUtils.getIP();                                      // 当前机器IP 
			if(ip == null){
				log.info("UsageAmountVMTreatment Class execute Method：获取IP失败！！");
				logUtil.info("UsageAmountVMTreatment Class execute Method：获取IP失败！！");
				return;
			}
			if (map.get("ServersNum") == null || map.get(ip) == null ||  map.get("ThreadNum") == null
					|| map.get("ThreadOneNum") == null || map.get("SleepTime") == null) {
				log.info("UsageAmountVMTreatment Class execute Method：T_JOB_TASK 表  PARMS 字段 参数据配置有误！！");
				logUtil.info("UsageAmountVMTreatment Class execute Method：T_JOB_TASK 表  PARMS 字段 参数据配置有误！！");
				return;
			}
			
			EstOrderBean estOrderBean = new EstOrderBean();
			estOrderBean.setTimeTip(timeTip);	
			estOrderBean.setSperiod(1440);			//采集周期为一天
			List<EstOrderBean> estorders = HandleDataTotal(estOrderBean);
			
			int treatmentNum =estorders.size();// 共有多少数据需要处理
			log.info("按使用量每天扫描一次的共有"+treatmentNum+"条数据需要处理！");
			logUtil.info("按使用量每天扫描一次的共有"+treatmentNum+"条数据需要处理！");
			if (treatmentNum > 0) {
				int serversNum = Integer.parseInt((String) map.get("ServersNum"));        // 多少台服务器
				int eachMachine = 0;                                                      // 此台服务器处理多少数据
				int serversNo = Integer.parseInt((String) map.get(ip));					  // 服务器编号
				int theMachineStart = 0;                                                  // 此服务器处理数据的开始点
				int threadNum = Integer.parseInt((String) map.get("ThreadNum"));          // 启动多少线程处理数据
				int tTNum = 0;                                                            // 每条线程处理多少条数据
				int threadOneNum = Integer.parseInt((String) map.get("ThreadOneNum"));    // 每条线程一次处理多少条数据
				int sleepTime = Integer.parseInt((String) map.get("SleepTime"));          // 线程启动间隔时间
				
				/***  每台服务器需要处理多少数据    ***/
				int eachMachineT = 0;
				if (treatmentNum % serversNum == 0) {
					eachMachineT = treatmentNum / serversNum;
				} else {
					eachMachineT = (treatmentNum / serversNum);
				}
				
				/***  根据服务器的编号，得出此服务器需要处理多少数据  和  开始点    ***/
				// 判断是否为最后一台服务器，如果是则处理剩下的数据
				if(serversNo == (serversNum -1 )){
					eachMachine = treatmentNum - (eachMachineT * (serversNum -1 ));
				}else{
					eachMachine = eachMachineT;
				}
				for(int j = 0;j<serversNum;j++){
					if(serversNo == j){
						theMachineStart = (eachMachineT * j);
					}
				}
				
				//修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
				SetData(eachMachine,theMachineStart,serversNo,timeTip,estorders);
				
				/***  本服务器每条线程处理多少数据    ***/
				if (eachMachine % threadNum == 0) {
					tTNum = eachMachine / threadNum ;
				} else {
					tTNum = (eachMachine / threadNum );
				}
				
				log.info("共有 "+treatmentNum+" 条数据需要处理， " + serversNum + " 台服务器，服务器编号为：" + serversNo 
						+ "此服务器处理 "+eachMachine+" 条数据，此服务器处理数据的开始点：" + theMachineStart 
						+ "，启动 "+threadNum+" 条线程处理数据，每条线程一次处理 "+threadOneNum+" 条数据，线程启动间隔时间：" + sleepTime);
				logUtil.info("共有 "+treatmentNum+" 条数据需要处理， " + serversNum + " 台服务器，服务器编号为：" + serversNo 
						+ "此服务器处理 "+eachMachine+" 条数据，此服务器处理数据的开始点：" + theMachineStart 
						+ "，启动 "+threadNum+" 条线程处理数据，每条线程一次处理 "+threadOneNum+" 条数据，线程启动间隔时间：" + sleepTime);
				
				for (int i = 0; i < threadNum; i++) {
					// 判断是否为最后一次，如果是则处理剩下的数据
					if(i == (threadNum-1)){
						tTNum = eachMachine - (tTNum * (threadNum-1));
					}
					
					UsageAmountVMThread ut = new UsageAmountVMThread(treatmentNum,tTNum,threadOneNum,serversNo);
					ut.start();
					Thread.sleep(sleepTime);
				}
			} else {
				log.info("UsageAmountVMTreatment Class execute Method：ORDER_INFO 表 无订单数据需要处理!");
				logUtil.info("UsageAmountVMTreatment Class execute Method：ORDER_INFO 表 无订单数据需要处理!");
				return;
			}
		}catch (Exception e) {
			log.error("UsageAmountVMTreatment Class execute Method !", e);
			logUtil.error("UsageAmountVMTreatment Class execute Method !", e);
			e.printStackTrace();
		}finally{
			System.gc();
		}
	}

	/**
	 * 修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
	 * @param eachMachine
	 * @param theMachineStart
	 * @param serversNo
	 * @param orders
	 */
	private void SetData(int eachMachine, int theMachineStart, int serversNo,String timeTip,List<EstOrderBean> estOrders) {
		UsageAccountDao usageAccountDao = new UsageAccountDao();
		UsageAccountBean usageAccountBean = new UsageAccountBean();
		usageAccountBean.setSCANNING_WAY("1");	//"1":每天扫描一次
		usageAccountBean.setSERVERS_NO_STATE(serversNo);
		usageAccountDao.SetData(usageAccountBean, eachMachine, theMachineStart,timeTip,estOrders);
	}

	/**
	 * 共有多少数据需要处理
	 * @return
	 */
	public List<EstOrderBean> HandleDataTotal(EstOrderBean estOrderBean){
		QueryDao queryDao = new QueryDao();
		List<HashMap<String, Object>> list = queryDao.getEstOrderNumOne(estOrderBean);
		List<EstOrderBean> l = new ArrayList<EstOrderBean>();
		if(list!=null&&list.size()>0){
			l = EstOrderBean.changeToObject(list);
		}
		list=null;
		return l;
	}

}

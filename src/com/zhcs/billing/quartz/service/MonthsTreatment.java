package com.zhcs.billing.quartz.service;

import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.dao.QueryDao;
import com.zhcs.billing.quartz.dao.UsageMonthsDao;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageMonthsBean;
import com.zhcs.billing.util.EnvironmentUtils;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 实扣月租-处理（按天扣）
 * 
 * @author yuqingchao
 *
 */
public class MonthsTreatment extends Task implements Job {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(MonthsTreatment.class);
	private static Logger log = LoggerFactory.getLogger(MonthsTreatment.class);

	@Override
	public void execute(HashMap map) {

		try {
			/*** 查看需要处理的订单数据 ***/
			List<String> ips = EnvironmentUtils.getLocalIPList(); // 当前机器IP
			if (ips.isEmpty()) {
				log.info("MonthsTreatment Class execute Method：获取IP失败！！");
				logUtil.info("MonthsTreatment Class execute Method：获取IP失败！！");
				return;
			}
			if (map.get("ServersNum") == null || map.get("ThreadNum") == null
					|| map.get("ThreadOneNum") == null
					|| map.get("SleepTime") == null || map.get("IP") == null
					|| map.get("No") == null) {
				log.info("UsageAmountVMTreatment Class execute Method：T_JOB_TASK 表  PARMS 字段 参数据配置有误！！");
				logUtil.info("UsageAmountVMTreatment Class execute Method：T_JOB_TASK 表  PARMS 字段 参数据配置有误！！");
				return;
			}
			if (!ips.contains(map.get("IP"))) {
				log.info("UsageAmountVMTreatment Class execute Method：T_JOB_TASK 表  PARMS 字段 IP配置有误！！");
				logUtil.info("UsageAmountVMTreatment Class execute Method：T_JOB_TASK 表  PARMS 字段IP配置有误！！");
				return;
			}
			// 共有多少数据需要处理
			List<OrderInfoBean> orders = HandleDataTotal();
			int treatmentNum = orders.size();
			log.info("包月按天扣费的共有" + treatmentNum + "条数据需要处理.");
			logUtil.info("包月按天扣费的共有" + treatmentNum + "条数据需要处理.");
			if (treatmentNum > 0) {
				int serversNum = Integer.parseInt((String) map
						.get("ServersNum")); // 多少台服务器
				int eachMachine = 0; // 此台服务器处理多少数据
				int serversNo = Integer.parseInt((String) map.get("No")); // 服务器编号
				int theMachineStart = 0; // 此服务器处理数据的开始点
				int threadNum = Integer.parseInt((String) map.get("ThreadNum")); // 启动多少线程处理数据
				int tTNum = 0; // 每条线程处理多少条数据
				int threadOneNum = Integer.parseInt((String) map
						.get("ThreadOneNum")); // 每条线程一次处理多少条数据
				int sleepTime = Integer.parseInt((String) map.get("SleepTime")); // 线程启动间隔时间

				/*** 每台服务器需要处理多少数据 ***/
				int eachMachineT = 0;
				if (treatmentNum % serversNum == 0) {
					eachMachineT = treatmentNum / serversNum;
				} else {
					eachMachineT = (treatmentNum / serversNum);
				}

				/*** 根据服务器的编号，得出此服务器需要处理多少数据 和 开始点 ***/
				// 判断是否为最后一台服务器，如果是则处理剩下的数据
				if (serversNo == (serversNum - 1)) {
					eachMachine = treatmentNum
							- (eachMachineT * (serversNum - 1));
				} else {
					eachMachine = eachMachineT;
				}
				for (int j = 0; j < serversNum; j++) {
					if (serversNo == j) {
						theMachineStart = (eachMachineT * j);
					}
				}

				// 修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
				SetData(eachMachine, theMachineStart, serversNo, orders);

				/*** 本服务器每条线程处理多少数据 ***/
				if (eachMachine % threadNum == 0) {
					tTNum = eachMachine / threadNum;
				} else {
					tTNum = (eachMachine / threadNum);
				}
				orders = null;
				log.info("共有 " + treatmentNum + " 条数据需要处理， " + serversNum
						+ " 台服务器，服务器编号为：" + serversNo + "此服务器处理 " + eachMachine
						+ " 条数据，此服务器处理数据的开始点：" + theMachineStart + "，启动 "
						+ threadNum + " 条线程处理数据，每条线程一次处理 " + threadOneNum
						+ " 条数据，线程启动间隔时间：" + sleepTime);
				logUtil.info("共有 " + treatmentNum + " 条数据需要处理， " + serversNum
						+ " 台服务器，服务器编号为：" + serversNo + "此服务器处理 " + eachMachine
						+ " 条数据，此服务器处理数据的开始点：" + theMachineStart + "，启动 "
						+ threadNum + " 条线程处理数据，每条线程一次处理 " + threadOneNum
						+ " 条数据，线程启动间隔时间：" + sleepTime);

				for (int i = 0; i < threadNum; i++) {
					// 判断是否为最后一次，如果是则处理剩下的数据
					if (i == (threadNum - 1)) {
						tTNum = eachMachine - (tTNum * (threadNum - 1));
					}

					// MonthsThread mt = new
					// MonthsThread(treatmentNum,tTNum,threadOneNum,serversNo);
					// mt.start();
					// Thread.sleep(sleepTime);

					// ThreadPool
					ThreadPool.getInstance().AddTask(
							new MonthsThread(treatmentNum, tTNum, threadOneNum,
									serversNo));

				}
			} else {
				log.info("MonthsTreatment Class execute Method：ORDER_INFO 表 无订单数据需要处理!");
				logUtil.info("MonthsTreatment Class execute Method：ORDER_INFO 表 无订单数据需要处理!");
				return;
			}
		} catch (Exception e) {
			log.error("MonthsTreatment Class execute Method !", e);
			logUtil.error("MonthsTreatment Class execute Method !", e);
			e.printStackTrace();
		} finally {
			System.gc();
		}
	}

	/**
	 * 修改此服务器所需要处理的数据状态为本服务器编号 serversNo,此服务器的线程只处理此服务器需要处理的数据
	 * 
	 * @param eachMachine
	 * @param theMachineStart
	 * @param serversNo
	 */
	private void SetData(int eachMachine, int theMachineStart, int serversNo,
			List<OrderInfoBean> orders) {
		UsageMonthsDao usageMonthsDao = new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setSERVERS_NO_STATE(serversNo);
		usageMonthsBean.setFIXED_OR_MONTHLY(1); // 1：按天扣 0：月初扣
		usageMonthsDao.SetData(usageMonthsBean, eachMachine, theMachineStart,
				orders);
	}

	/**
	 * 共有多少数据需要处理
	 * 
	 * @return
	 */
	private List<OrderInfoBean> HandleDataTotal() {
		QueryDao queryDao = new QueryDao();
		OrderInfoBean orderInfoBean = new OrderInfoBean();
		orderInfoBean.setORDER_STATUS(3); // 1：已结算
		orderInfoBean.setPRODUCT_CATEGORY(1); // 产品大类 1：容器类 2：应用类
		List<HashMap<String, Object>> list = queryDao
				.getMonthsOrderNum(orderInfoBean);
		List<OrderInfoBean> l = orderInfoBean.changeToObject(list);
		return l;
	}

}

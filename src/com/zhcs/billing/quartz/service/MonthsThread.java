package com.zhcs.billing.quartz.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.dao.QueryDao;
import com.zhcs.billing.quartz.dao.UsageMonthsDao;
import com.zhcs.billing.use.bean.AccountPayBean;
import com.zhcs.billing.use.bean.UsageMonthsBean;
import com.zhcs.billing.use.dao.AppCalculateCharage;
import com.zhcs.billing.use.dao.BillingCumulative;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;

/**
 * 实扣月租-执行线程（按天扣）
 * @author yuqingchao
 *
 */
public class MonthsThread extends Thread {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(MonthsThread.class);
	private static Logger log = LoggerFactory.getLogger(MonthsThread.class);
	private int treatmentNum; // 所有线程共处理多少数据
	private int tTNum; // 此线程处理多少数据
	private int threadOneNum; // 此线程一次处理多少条数据
	private int serversNo; // 服务器编号

	public MonthsThread(int treatmentNum, int tTNum, int threadOneNum,
			int serversNo) {
		this.treatmentNum = treatmentNum;
		this.tTNum = tTNum;
		this.threadOneNum = threadOneNum;
		this.serversNo = serversNo;
		log.debug("treatmentNum:"+treatmentNum+"tTNum:"+tTNum+"threadOneNum:"+threadOneNum+"serversNo:"+serversNo);
		logUtil.debug("treatmentNum:"+treatmentNum+"tTNum:"+tTNum+"threadOneNum:"+threadOneNum+"serversNo:"+serversNo);
	}

	@Override
	public synchronized void run() {
		try {
			if (threadOneNum > 0 && tTNum > 0) {
				String threadName = Common.getThreadSSSStr(); // 线程名称：用于做线程处理数据的标示
				log.info("MonthsThread Class run Method:线程名称：" + threadName + " 开始！");
				logUtil.info("MonthsThread Class run Method:线程名称：" + threadName + " 开始！");
				/*** 根据 T_Bill_Cycle_Seq 表 Bill_Cycle_Stat字段的状态，得到账期 ***/
				String dateStr = ""; // 账期
				dateStr = Common.GetBillCycle();
				if (dateStr != null && !"".equals(dateStr)) {
					dateStr.replaceAll("-", "").substring(0, 6);
				} else {
					dateStr = Common.getDate();
				}
				log.info("MonthsThread Class run Method:账期名称：" + dateStr);
				logUtil.info("MonthsThread Class run Method:账期名称：" + dateStr);

				/*** 算出循环次数 ***/
				int forNum = 0;
				if (tTNum % threadOneNum == 0) {
					forNum = tTNum / threadOneNum;
				} else {
					forNum = (tTNum / threadOneNum)+1;
				}
				
				for (int n = 0; n < forNum; n++) {
					/*** 如果循环最后一次则 只处理此线程未处理的数据 ***/
					if (n == (forNum - 1)) {
						threadOneNum = tTNum - (threadOneNum * (forNum - 1));
					}
					log.info("线程名称：" + threadName + " 循环第 " + n + "次处理 "
							+ threadOneNum + "条数据！！");
					logUtil.info("线程名称：" + threadName + " 循环第 " + n + "次处理 "
							+ threadOneNum + "条数据！！");
					/*** 修改STATE 为 threadName ，表示此线程正在扫描当中 ***/
					int stateNum1 = Scanning(threadName);
					if (stateNum1 == 0) {
						log.error("MonthsThread Class run Method 修改  T_USAGE_MONTHS 表 STATE = " + threadName + " 失败 ！");
						logUtil.error("MonthsThread Class run Method 修改  T_USAGE_MONTHS 表 STATE = " + threadName + " 失败 ！");
						return; // 修改失败
					} else {
						/*** 查询需要处理的月租数据 ***/
						UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
						UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
						usageMonthsBean.setSCANNING_WAY("1");
						usageMonthsBean.setFIXED_OR_MONTHLY(1);	// 按天扣:1，按月扣:0
						usageMonthsBean.setSTATE(threadName);
						usageMonthsBean.setSERVERS_NO_STATE(serversNo);
						List<UsageMonthsBean> list = usageMonthsDao.listUsageMonths(usageMonthsBean);
						log.info("MonthsThread Class run Method: 查询需要处理的月租数据为："+ list.size() + "条");
						logUtil.info("MonthsThread Class run Method: 查询需要处理的月租数据为："+ list.size() + "条");
						/*** 处理月租数据 ***/
						if (list.size() > 0 && list != null) {
							for (int i = 0; i < list.size(); i++) {
								String orderid = list.get(i).getORDER_ID();
								AppCalculateCharage acc = new AppCalculateCharage();
								//進行垃圾回收
								System.gc();
								//原始保留
								//String account = acc.CommonFeeN(orderid, "2", Common.getDateStr() ,Common.createBillingID() , dateStr, "1");
								String account = acc.CommonFeeN(orderid,Common.getDateStr());
								//進行垃圾回收
								System.gc();
								//跟据订单编号获得客户编号
								String customerid = getCustomerIdByOrderId(orderid);	
								
								List<String> params = new ArrayList<String>();
								List<String> values = new ArrayList<String>();
								params.add("TimeStamp");	//时间戳 
								params.add("BillingID");	//接口之间的标识符  
								params.add("CustomerID");	//客户编号
								params.add("OrderID");		//订单编号
								params.add("Amount");		//扣费金额
								params.add("CDRType");		//话单类型

								values.add(Common.getDateStr());
								values.add(Common.createBillingID());
								values.add(customerid!=null?customerid:"");
								values.add(orderid!=null?orderid:"");
								values.add(account);
								values.add("1");
								
								String req = Common.changeJSON(params, values);
								String json = req.substring(1, req.length()-1);
								//進行垃圾回收
								System.gc();
								//调用业务管理平台账户扣费接口
								Object[] objects = Common.accountPayInerface(BillingCumulative.readProperties("accountUrl"), BillingCumulative.readProperties("accountMethod"), json); 
								log.info("计费系统发送信息："+json);
								log.info("计费系统接受信息："+objects[0]);
								logUtil.info("计费系统发送信息："+json);
								logUtil.info("计费系统接受信息："+objects[0]);
								
								JSONObject jsonObject = JSONObject.fromObject(objects[0]);
								String result = jsonObject.get("Result")+"";
								
								if("true".equals(result)){
									log.info("订单编号为："+orderid+"调用平台扣费成功！");
									logUtil.info("订单编号为："+orderid+"调用平台扣费成功！");
								}else {
									log.info("订单编号为："+orderid+"调用平台扣费失败！");
									logUtil.info("订单编号为："+orderid+"调用平台扣费失败！");
								}
								/*调用业务账户扣费接口结束，将结果记录到T_ACCOUNTPAY表里*/
								AccountPayBean accountPayBean = new AccountPayBean();
								accountPayBean.setBILLING_ID(jsonObject.get("TimeStamp")+"");
								accountPayBean.setTIME_STAMP(jsonObject.get("BillingID")+"");
								accountPayBean.setCUSTOMER_ID(customerid);
								accountPayBean.setORDER_ID(orderid);
								accountPayBean.setAMOUNT(Integer.parseInt(account));
								accountPayBean.setCDR_TYPE(1);
								accountPayBean.setRESULT((jsonObject.get("Result")+"").equals("true")?true:false);
								accountPayBean.setDESCRIPTION(jsonObject.get("Description")+"");
								insertAccountPay(accountPayBean);
								
								//--------------开始垃圾回收内存占用-------------
								acc            =null;
								params         =null;
								values         =null;
								objects        =null;
								jsonObject     =null;
								accountPayBean =null;
								//---------------------------------------
								//進行垃圾回收
								System.gc();
							}
						} else {
							log.info("MonthsThread Class run Method !  无月租实扣数据需要处理！  时间："
									+ Common.getDateTime(new Timestamp(System.currentTimeMillis())));
							logUtil.info("MonthsThread Class run Method !  无月租实扣数据需要处理！  时间："
									+ Common.getDateTime(new Timestamp(System.currentTimeMillis())));
						}
						
						//--------------开始垃圾回收内存占用-------------
						usageMonthsDao =null;
						list           =null;
						usageMonthsBean=null;
						//---------------------------------------
					}
				}

				/*** 修改STATE 为 完成，表示此线程已经扫描当完成 ***/
				CompleteScan(threadName);
				/*** 判断数据是否都已经处理完成，清空T_Usage_Monthly表为月租的数据 ***/
				deleteT(threadName);
				log.info("线程名称：" + threadName + "     结束！");
				logUtil.info("线程名称：" + threadName + "     结束！");
			}
		} catch (Exception e) {
			log.error("MonthsThread Class run Method ! ", e);
			logUtil.error("MonthsThread Class run Method ! ", e);
			e.printStackTrace();
		}
		//進行垃圾回收
		System.gc();
	}

	/*** 判断当前日期是不是当月最后一天 ***/
	public boolean isLastDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		} else {
			return false;
		}
	}

	/*** 修改STATE 为 threadName ，表示此线程正在扫描当中 ***/
	public int Scanning(String threadName) {
		int stateNum1 = 0;
		UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setCOMPLETE_STATE("未执行");
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setFIXED_OR_MONTHLY(1);	//按天扣:1，按月扣:0
		usageMonthsBean.setSTATE(threadName);
		usageMonthsBean.setSERVERS_NO_STATE(serversNo);
		stateNum1 = usageMonthsDao.scanning(usageMonthsBean, threadOneNum);
		return stateNum1;
	}

	/*** 修改STATE 为 完成，表示此线程已经扫描当完成 ***/
	public void CompleteScan(String threadName) {
		UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setCOMPLETE_STATE("完成");
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setFIXED_OR_MONTHLY(1);//按天扣:1，按月扣:0
		usageMonthsBean.setSTATE(threadName);
		usageMonthsBean.setSERVERS_NO_STATE(serversNo);
		String ClassName = "MonthsThread";
		usageMonthsDao.updateUsageMonthsState(usageMonthsBean, ClassName);
	}

	/*** 判断数据是否都已经处理完成，清空T_Usage_Monthly表为月租的数据 ***/
	public void deleteT(String threadName){
		UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setFIXED_OR_MONTHLY(1);//按天扣:1，按月扣:0
		usageMonthsBean.setSTATE(threadName);
		usageMonthsBean.setCOMPLETE_STATE("完成");
		usageMonthsDao.deleteDate(usageMonthsBean, tTNum);
	}
	/**
	 * 根据订单编号查询客户编号
	 * @param orderid
	 * @return
	 */
	private String getCustomerIdByOrderId(String orderid){
		QueryDao queryDao = new QueryDao();
		return queryDao.getCustomerIdByOrderId(orderid);
	}
	/**
	 * 往T_ACCOUNTPAY表里插入数据
	 * @param accountPayBean
	 */
	private void insertAccountPay(AccountPayBean accountPayBean){
		QueryDao queryDao = new QueryDao();
		queryDao.insertAccountPay(accountPayBean);
	}

	/* Get And Set */
	public int getTreatmentNum() {
		return treatmentNum;
	}

	public void setTreatmentNum(int treatmentNum) {
		this.treatmentNum = treatmentNum;
	}

	public int gettTNum() {
		return tTNum;
	}

	public void settTNum(int tTNum) {
		this.tTNum = tTNum;
	}

	public int getThreadOneNum() {
		return threadOneNum;
	}

	public void setThreadOneNum(int threadOneNum) {
		this.threadOneNum = threadOneNum;
	}

	public int getServersNo() {
		return serversNo;
	}

	public void setServersNo(int serversNo) {
		this.serversNo = serversNo;
	}
	
}

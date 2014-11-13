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
 * ʵ������-ִ���̣߳�����ۣ�
 * @author yuqingchao
 *
 */
public class MonthsThread extends Thread {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(MonthsThread.class);
	private static Logger log = LoggerFactory.getLogger(MonthsThread.class);
	private int treatmentNum; // �����̹߳������������
	private int tTNum; // ���̴߳����������
	private int threadOneNum; // ���߳�һ�δ������������
	private int serversNo; // ���������

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
				String threadName = Common.getThreadSSSStr(); // �߳����ƣ��������̴߳������ݵı�ʾ
				log.info("MonthsThread Class run Method:�߳����ƣ�" + threadName + " ��ʼ��");
				logUtil.info("MonthsThread Class run Method:�߳����ƣ�" + threadName + " ��ʼ��");
				/*** ���� T_Bill_Cycle_Seq �� Bill_Cycle_Stat�ֶε�״̬���õ����� ***/
				String dateStr = ""; // ����
				dateStr = Common.GetBillCycle();
				if (dateStr != null && !"".equals(dateStr)) {
					dateStr.replaceAll("-", "").substring(0, 6);
				} else {
					dateStr = Common.getDate();
				}
				log.info("MonthsThread Class run Method:�������ƣ�" + dateStr);
				logUtil.info("MonthsThread Class run Method:�������ƣ�" + dateStr);

				/*** ���ѭ������ ***/
				int forNum = 0;
				if (tTNum % threadOneNum == 0) {
					forNum = tTNum / threadOneNum;
				} else {
					forNum = (tTNum / threadOneNum)+1;
				}
				
				for (int n = 0; n < forNum; n++) {
					/*** ���ѭ�����һ���� ֻ������߳�δ��������� ***/
					if (n == (forNum - 1)) {
						threadOneNum = tTNum - (threadOneNum * (forNum - 1));
					}
					log.info("�߳����ƣ�" + threadName + " ѭ���� " + n + "�δ��� "
							+ threadOneNum + "�����ݣ���");
					logUtil.info("�߳����ƣ�" + threadName + " ѭ���� " + n + "�δ��� "
							+ threadOneNum + "�����ݣ���");
					/*** �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱�� ***/
					int stateNum1 = Scanning(threadName);
					if (stateNum1 == 0) {
						log.error("MonthsThread Class run Method �޸�  T_USAGE_MONTHS �� STATE = " + threadName + " ʧ�� ��");
						logUtil.error("MonthsThread Class run Method �޸�  T_USAGE_MONTHS �� STATE = " + threadName + " ʧ�� ��");
						return; // �޸�ʧ��
					} else {
						/*** ��ѯ��Ҫ������������� ***/
						UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
						UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
						usageMonthsBean.setSCANNING_WAY("1");
						usageMonthsBean.setFIXED_OR_MONTHLY(1);	// �����:1�����¿�:0
						usageMonthsBean.setSTATE(threadName);
						usageMonthsBean.setSERVERS_NO_STATE(serversNo);
						List<UsageMonthsBean> list = usageMonthsDao.listUsageMonths(usageMonthsBean);
						log.info("MonthsThread Class run Method: ��ѯ��Ҫ�������������Ϊ��"+ list.size() + "��");
						logUtil.info("MonthsThread Class run Method: ��ѯ��Ҫ�������������Ϊ��"+ list.size() + "��");
						/*** ������������ ***/
						if (list.size() > 0 && list != null) {
							for (int i = 0; i < list.size(); i++) {
								String orderid = list.get(i).getORDER_ID();
								AppCalculateCharage acc = new AppCalculateCharage();
								//�M����������
								System.gc();
								//ԭʼ����
								//String account = acc.CommonFeeN(orderid, "2", Common.getDateStr() ,Common.createBillingID() , dateStr, "1");
								String account = acc.CommonFeeN(orderid,Common.getDateStr());
								//�M����������
								System.gc();
								//���ݶ�����Ż�ÿͻ����
								String customerid = getCustomerIdByOrderId(orderid);	
								
								List<String> params = new ArrayList<String>();
								List<String> values = new ArrayList<String>();
								params.add("TimeStamp");	//ʱ��� 
								params.add("BillingID");	//�ӿ�֮��ı�ʶ��  
								params.add("CustomerID");	//�ͻ����
								params.add("OrderID");		//�������
								params.add("Amount");		//�۷ѽ��
								params.add("CDRType");		//��������

								values.add(Common.getDateStr());
								values.add(Common.createBillingID());
								values.add(customerid!=null?customerid:"");
								values.add(orderid!=null?orderid:"");
								values.add(account);
								values.add("1");
								
								String req = Common.changeJSON(params, values);
								String json = req.substring(1, req.length()-1);
								//�M����������
								System.gc();
								//����ҵ�����ƽ̨�˻��۷ѽӿ�
								Object[] objects = Common.accountPayInerface(BillingCumulative.readProperties("accountUrl"), BillingCumulative.readProperties("accountMethod"), json); 
								log.info("�Ʒ�ϵͳ������Ϣ��"+json);
								log.info("�Ʒ�ϵͳ������Ϣ��"+objects[0]);
								logUtil.info("�Ʒ�ϵͳ������Ϣ��"+json);
								logUtil.info("�Ʒ�ϵͳ������Ϣ��"+objects[0]);
								
								JSONObject jsonObject = JSONObject.fromObject(objects[0]);
								String result = jsonObject.get("Result")+"";
								
								if("true".equals(result)){
									log.info("�������Ϊ��"+orderid+"����ƽ̨�۷ѳɹ���");
									logUtil.info("�������Ϊ��"+orderid+"����ƽ̨�۷ѳɹ���");
								}else {
									log.info("�������Ϊ��"+orderid+"����ƽ̨�۷�ʧ�ܣ�");
									logUtil.info("�������Ϊ��"+orderid+"����ƽ̨�۷�ʧ�ܣ�");
								}
								/*����ҵ���˻��۷ѽӿڽ������������¼��T_ACCOUNTPAY����*/
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
								
								//--------------��ʼ���������ڴ�ռ��-------------
								acc            =null;
								params         =null;
								values         =null;
								objects        =null;
								jsonObject     =null;
								accountPayBean =null;
								//---------------------------------------
								//�M����������
								System.gc();
							}
						} else {
							log.info("MonthsThread Class run Method !  ������ʵ��������Ҫ����  ʱ�䣺"
									+ Common.getDateTime(new Timestamp(System.currentTimeMillis())));
							logUtil.info("MonthsThread Class run Method !  ������ʵ��������Ҫ����  ʱ�䣺"
									+ Common.getDateTime(new Timestamp(System.currentTimeMillis())));
						}
						
						//--------------��ʼ���������ڴ�ռ��-------------
						usageMonthsDao =null;
						list           =null;
						usageMonthsBean=null;
						//---------------------------------------
					}
				}

				/*** �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱��� ***/
				CompleteScan(threadName);
				/*** �ж������Ƿ��Ѿ�������ɣ����T_Usage_Monthly��Ϊ��������� ***/
				deleteT(threadName);
				log.info("�߳����ƣ�" + threadName + "     ������");
				logUtil.info("�߳����ƣ�" + threadName + "     ������");
			}
		} catch (Exception e) {
			log.error("MonthsThread Class run Method ! ", e);
			logUtil.error("MonthsThread Class run Method ! ", e);
			e.printStackTrace();
		}
		//�M����������
		System.gc();
	}

	/*** �жϵ�ǰ�����ǲ��ǵ������һ�� ***/
	public boolean isLastDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		} else {
			return false;
		}
	}

	/*** �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱�� ***/
	public int Scanning(String threadName) {
		int stateNum1 = 0;
		UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setCOMPLETE_STATE("δִ��");
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setFIXED_OR_MONTHLY(1);	//�����:1�����¿�:0
		usageMonthsBean.setSTATE(threadName);
		usageMonthsBean.setSERVERS_NO_STATE(serversNo);
		stateNum1 = usageMonthsDao.scanning(usageMonthsBean, threadOneNum);
		return stateNum1;
	}

	/*** �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱��� ***/
	public void CompleteScan(String threadName) {
		UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setCOMPLETE_STATE("���");
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setFIXED_OR_MONTHLY(1);//�����:1�����¿�:0
		usageMonthsBean.setSTATE(threadName);
		usageMonthsBean.setSERVERS_NO_STATE(serversNo);
		String ClassName = "MonthsThread";
		usageMonthsDao.updateUsageMonthsState(usageMonthsBean, ClassName);
	}

	/*** �ж������Ƿ��Ѿ�������ɣ����T_Usage_Monthly��Ϊ��������� ***/
	public void deleteT(String threadName){
		UsageMonthsDao usageMonthsDao=new UsageMonthsDao();
		UsageMonthsBean usageMonthsBean = new UsageMonthsBean();
		usageMonthsBean.setSCANNING_WAY("1");
		usageMonthsBean.setFIXED_OR_MONTHLY(1);//�����:1�����¿�:0
		usageMonthsBean.setSTATE(threadName);
		usageMonthsBean.setCOMPLETE_STATE("���");
		usageMonthsDao.deleteDate(usageMonthsBean, tTNum);
	}
	/**
	 * ���ݶ�����Ų�ѯ�ͻ����
	 * @param orderid
	 * @return
	 */
	private String getCustomerIdByOrderId(String orderid){
		QueryDao queryDao = new QueryDao();
		return queryDao.getCustomerIdByOrderId(orderid);
	}
	/**
	 * ��T_ACCOUNTPAY�����������
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

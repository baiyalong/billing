package com.zhcs.billing.quartz.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.quartz.dao.QueryDao;
import com.zhcs.billing.quartz.dao.UsageAccountDao;
import com.zhcs.billing.use.bean.AccountPayBean;
import com.zhcs.billing.use.bean.OrderInfoBean;
import com.zhcs.billing.use.bean.UsageAccountBean;
import com.zhcs.billing.use.dao.BillingCumulative;
import com.zhcs.billing.use.dao.BillingMethod;
import com.zhcs.billing.util.Common;
import com.zhcs.billing.util.LoggerUtil;

/**
 * ʵ�۷���-��������ʹ����ÿ5����ɨ��һ�Σ�
 * @author yuqingchao
 *
 */
public class UsageAccountSThread extends Thread {

	private static LoggerUtil logUtil = LoggerUtil.getLogger(UsageAccountSThread.class);
	private static Logger log = LoggerFactory.getLogger(UsageAccountSThread.class);
	private int treatmentNum; // �����̹߳�������������
	private int tTNum; // ���̴߳�����������
	private int threadOneNum; // ���߳�һ�δ�������������
	private int serversNo; // ���������
	
	public UsageAccountSThread(int treatmentNum, int tTNum, int threadOneNum,
			int serversNo) {
		super();
		this.treatmentNum = treatmentNum;
		this.tTNum = tTNum;
		this.threadOneNum = threadOneNum;
		this.serversNo = serversNo;
		logUtil.debug("treatmentNum:"+treatmentNum+"tTNum:"+tTNum+"threadOneNum:"+threadOneNum+"serversNo:"+serversNo);
	}
	public synchronized void run(){
		try {
			if (threadOneNum > 0 && tTNum > 0) {
				
				String threadName = Common.getThreadSSSStr(); // �߳����ƣ��������̴߳������ݵı�ʾ
				log.info("UsageAccountSThread Class run Method:�߳����ƣ�" + threadName + " ��ʼ��");
				logUtil.info("UsageAccountSThread Class run Method:�߳����ƣ�" + threadName + " ��ʼ��");
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
					forNum = (tTNum / threadOneNum) + 1;
				}
				
		        
				for (int n = 0; n < forNum; n++) {
					/*** ���ѭ�����һ���� ֻ�������߳�δ���������� ***/
					if (n == (forNum - 1)) {
						threadOneNum = tTNum - (threadOneNum * (forNum - 1));
					}
					log.info("�߳����ƣ�" + threadName + " ѭ���� " + n+1 + "�δ��� "
							+ threadOneNum + "�����ݣ���");
					logUtil.info("�߳����ƣ�" + threadName + " ѭ���� " + n+1 + "�δ��� "
							+ threadOneNum + "�����ݣ���");

					/*** �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱�� ***/
					int stateNum1 = Scanning(threadName);
					if (stateNum1 == 0) {
						log.error("UsageAccountSThread Class run Method �޸�  T_USAGE_ACCOUNT �� STATE = " + threadName + " ʧ�� ��");
						logUtil.error("UsageAccountSThread Class run Method �޸�  T_USAGE_ACCOUNT �� STATE = " + threadName + " ʧ�� ��");
						return; // �޸�ʧ��
					} else {
						/*** ��ѯ��Ҫ���������� ***/
						UsageAccountDao usageAccountDao = new UsageAccountDao();
						UsageAccountBean usageAccountBean = new UsageAccountBean();
						usageAccountBean.setSCANNING_WAY("5");//ɨ�跽ʽ 1:ÿ��ɨ��һ�Σ�5:ÿ5����ɨ��һ��
						usageAccountBean.setSTATE(threadName);
						usageAccountBean.setSERVERS_NO_STATE(serversNo);
						List<UsageAccountBean> list = usageAccountDao.listUsageAccount(usageAccountBean);
						log.info("UsageAccountSThread Class run Method: ��ѯ��Ҫ��������Ϊ��"+ list.size() + "��");
						logUtil.info("UsageAccountSThread Class run Method: ��ѯ��Ҫ��������Ϊ��"+ list.size() + "��");
						/*** �������� ***/
						if (list.size() > 0 && list != null) {
							
							for (int i = 0; i < list.size(); i++) {
								OrderInfoBean orderInfoBean = new OrderInfoBean();
								orderInfoBean.setORDER_ID(list.get(i).getORDER_ID());
								String amount = BillingMethod.compute(orderInfoBean, "5", list.get(i).getSCANNING_TIME_POINT());
								String customerid = getCustomerIdByOrderId(orderInfoBean.getORDER_ID());	//���ݶ�����Ż�ÿͻ����
								
								List<String> params = new ArrayList<String>();
								List values = new ArrayList();
								params.add("TimeStamp");	//ʱ��� 
								params.add("BillingID");	//�ӿ�֮��ı�ʶ��  
								params.add("CustomerID");	//�ͻ����
								params.add("OrderID");		//�������
								params.add("Amount");		//�۷ѽ��
								params.add("CDRType");		//��������
								values.add(Common.getDateStr());
								values.add(Common.createBillingID());
								values.add(customerid!=null?customerid:"");
								values.add(orderInfoBean.getORDER_ID()!=null?orderInfoBean.getORDER_ID():"");
								values.add(amount+"");
								values.add("3");
								
								String req = Common.changeJSON(params, values);
								String json = req.substring(1, req.length()-1);
//								int num = 0;
//								for (int j = 0; j < 10000; j++) {
//									log.info("ʱ�䣺---------------"+new Date());
//									num++;
//								}
//								System.out.println(num);
								//����ҵ�����ƽ̨�˻��۷ѽӿ�
								Object[] objects = Common.accountPayInerface(BillingCumulative.readProperties("accountUrl"), BillingCumulative.readProperties("accountMethod"), json); 
								log.info("�Ʒ�ϵͳ������Ϣ��"+json);
								log.info("�Ʒ�ϵͳ������Ϣ��"+objects[0]);
								logUtil.info("�Ʒ�ϵͳ������Ϣ��"+json);
								logUtil.info("�Ʒ�ϵͳ������Ϣ��"+objects[0]);
								
								JSONObject jsonObject = JSONObject.fromObject(objects[0]);
								String result = jsonObject.get("Result")+"";
								
								if("true".equals(result)){
									log.info("�������Ϊ��"+orderInfoBean.getORDER_ID()+"����ƽ̨�۷ѳɹ���");
									logUtil.info("�������Ϊ��"+orderInfoBean.getORDER_ID()+"����ƽ̨�۷ѳɹ���");
								}else {
									log.info("�������Ϊ��"+orderInfoBean.getORDER_ID()+"����ƽ̨�۷�ʧ�ܣ�");
									logUtil.info("�������Ϊ��"+orderInfoBean.getORDER_ID()+"����ƽ̨�۷�ʧ�ܣ�");
								}
								/*����ҵ���˻��۷ѽӿڽ������������¼��T_ACCOUNTPAY����*/
								AccountPayBean accountPayBean = new AccountPayBean();
								accountPayBean.setBILLING_ID(jsonObject.get("TimeStamp")+"");
								accountPayBean.setTIME_STAMP(jsonObject.get("BillingID")+"");
								accountPayBean.setCUSTOMER_ID(customerid);
								accountPayBean.setORDER_ID(orderInfoBean.getORDER_ID());
								accountPayBean.setAMOUNT(orderInfoBean.getReality());
								accountPayBean.setCDR_TYPE(3);
								accountPayBean.setRESULT((jsonObject.get("Result")+"").equals("true")?true:false);
								accountPayBean.setDESCRIPTION(jsonObject.get("Description")+"");
								insertAccountPay(accountPayBean);
								
								
								//--------------��ʼ���������ڴ�ռ��-------------
								params         =null;
								values         =null;
								objects        =null;
								jsonObject     =null;
								accountPayBean =null;
								//---------------------------------------
							}
						} else {
							log.info("UsageAccountSThread Class run Method !  ��������Ҫ������  ʱ�䣺"
									+ Common.getDateTime(new Timestamp(System.currentTimeMillis())));
							logUtil.info("UsageAccountSThread Class run Method !  ��������Ҫ������  ʱ�䣺"
									+ Common.getDateTime(new Timestamp(System.currentTimeMillis())));
						}
						
						//--------------��ʼ���������ڴ�ռ��-------------
						usageAccountDao =null;
						list           =null;
						usageAccountBean=null;
						//---------------------------------------
					}
					
				}

				/*** �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱��� ***/
				CompleteScan(threadName);
				/*** �ж������Ƿ��Ѿ�������ɣ����T_USAGE_ACCOUNT��Ϊ��������� ***/
				deleteT(threadName);
				log.info("�߳����ƣ�" + threadName + "     ������");
				logUtil.info("�߳����ƣ�" + threadName + "     ������");
			}
		} catch (Exception e) {
			log.error(" Class run Method ! ", e);
			logUtil.error(" Class run Method ! ", e);
			e.printStackTrace();
		}
	}

	

	/**
	 * �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱��
	 * @param threadName
	 * @return
	 */
	private int Scanning(String threadName) {
		UsageAccountDao usageAccountDao = new UsageAccountDao();
		UsageAccountBean usageAccountBean = new UsageAccountBean();
		usageAccountBean.setCOMPLETE_STATE("δִ��");
		usageAccountBean.setSCANNING_WAY("5");
		usageAccountBean.setSTATE(threadName);
		usageAccountBean.setSERVERS_NO_STATE(serversNo);
		return usageAccountDao.scanning(usageAccountBean, threadOneNum);
	}
	/**
	 * �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱���
	 * @param threadName
	 */
	private void CompleteScan(String threadName){
		UsageAccountDao usageAccountDao = new UsageAccountDao();
		UsageAccountBean usageAccountBean = new UsageAccountBean();
		usageAccountBean.setCOMPLETE_STATE("���");
		usageAccountBean.setSCANNING_WAY("5");
		usageAccountBean.setSTATE(threadName);
		usageAccountBean.setSERVERS_NO_STATE(serversNo);
		String ClassName = "UsageAmountVMThread";
		usageAccountDao.updateUsageAccountState(usageAccountBean, ClassName);
	}
	/**
	 * �ж������Ƿ��Ѿ�������ɣ����T_USAGE_ACCOUNT��Ϊ���������
	 * @param threadName
	 */
	private void deleteT(String threadName) {
		UsageAccountDao usageAccountDao = new UsageAccountDao();
		UsageAccountBean usageAccountBean = new UsageAccountBean();
		usageAccountBean.setCOMPLETE_STATE("���");
		usageAccountBean.setSCANNING_WAY("5");
		usageAccountBean.setSTATE(threadName);
		usageAccountDao.deleteDate(usageAccountBean, tTNum);
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
	
	/* Set and Get*/
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
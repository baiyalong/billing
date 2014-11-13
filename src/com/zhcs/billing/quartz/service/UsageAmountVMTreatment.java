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
 * ʵ�۷���-���������Դ��ʹ����ÿ��ɨ��һ�Σ�
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
			/***  �鿴��Ҫ����Ķ�������   ***/
			String ip = EnvironmentUtils.getIP();                                      // ��ǰ����IP 
			if(ip == null){
				log.info("UsageAmountVMTreatment Class execute Method����ȡIPʧ�ܣ���");
				logUtil.info("UsageAmountVMTreatment Class execute Method����ȡIPʧ�ܣ���");
				return;
			}
			if (map.get("ServersNum") == null || map.get(ip) == null ||  map.get("ThreadNum") == null
					|| map.get("ThreadOneNum") == null || map.get("SleepTime") == null) {
				log.info("UsageAmountVMTreatment Class execute Method��T_JOB_TASK ��  PARMS �ֶ� �������������󣡣�");
				logUtil.info("UsageAmountVMTreatment Class execute Method��T_JOB_TASK ��  PARMS �ֶ� �������������󣡣�");
				return;
			}
			
			EstOrderBean estOrderBean = new EstOrderBean();
			estOrderBean.setTimeTip(timeTip);	
			estOrderBean.setSperiod(1440);			//�ɼ�����Ϊһ��
			List<EstOrderBean> estorders = HandleDataTotal(estOrderBean);
			
			int treatmentNum =estorders.size();// ���ж���������Ҫ����
			log.info("��ʹ����ÿ��ɨ��һ�εĹ���"+treatmentNum+"��������Ҫ����");
			logUtil.info("��ʹ����ÿ��ɨ��һ�εĹ���"+treatmentNum+"��������Ҫ����");
			if (treatmentNum > 0) {
				int serversNum = Integer.parseInt((String) map.get("ServersNum"));        // ����̨������
				int eachMachine = 0;                                                      // ��̨�����������������
				int serversNo = Integer.parseInt((String) map.get(ip));					  // ���������
				int theMachineStart = 0;                                                  // �˷������������ݵĿ�ʼ��
				int threadNum = Integer.parseInt((String) map.get("ThreadNum"));          // ���������̴߳�������
				int tTNum = 0;                                                            // ÿ���̴߳������������
				int threadOneNum = Integer.parseInt((String) map.get("ThreadOneNum"));    // ÿ���߳�һ�δ������������
				int sleepTime = Integer.parseInt((String) map.get("SleepTime"));          // �߳��������ʱ��
				
				/***  ÿ̨��������Ҫ�����������    ***/
				int eachMachineT = 0;
				if (treatmentNum % serversNum == 0) {
					eachMachineT = treatmentNum / serversNum;
				} else {
					eachMachineT = (treatmentNum / serversNum);
				}
				
				/***  ���ݷ������ı�ţ��ó��˷�������Ҫ�����������  ��  ��ʼ��    ***/
				// �ж��Ƿ�Ϊ���һ̨�����������������ʣ�µ�����
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
				
				//�޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
				SetData(eachMachine,theMachineStart,serversNo,timeTip,estorders);
				
				/***  ��������ÿ���̴߳����������    ***/
				if (eachMachine % threadNum == 0) {
					tTNum = eachMachine / threadNum ;
				} else {
					tTNum = (eachMachine / threadNum );
				}
				
				log.info("���� "+treatmentNum+" ��������Ҫ���� " + serversNum + " ̨�����������������Ϊ��" + serversNo 
						+ "�˷��������� "+eachMachine+" �����ݣ��˷������������ݵĿ�ʼ�㣺" + theMachineStart 
						+ "������ "+threadNum+" ���̴߳������ݣ�ÿ���߳�һ�δ��� "+threadOneNum+" �����ݣ��߳��������ʱ�䣺" + sleepTime);
				logUtil.info("���� "+treatmentNum+" ��������Ҫ���� " + serversNum + " ̨�����������������Ϊ��" + serversNo 
						+ "�˷��������� "+eachMachine+" �����ݣ��˷������������ݵĿ�ʼ�㣺" + theMachineStart 
						+ "������ "+threadNum+" ���̴߳������ݣ�ÿ���߳�һ�δ��� "+threadOneNum+" �����ݣ��߳��������ʱ�䣺" + sleepTime);
				
				for (int i = 0; i < threadNum; i++) {
					// �ж��Ƿ�Ϊ���һ�Σ����������ʣ�µ�����
					if(i == (threadNum-1)){
						tTNum = eachMachine - (tTNum * (threadNum-1));
					}
					
					UsageAmountVMThread ut = new UsageAmountVMThread(treatmentNum,tTNum,threadOneNum,serversNo);
					ut.start();
					Thread.sleep(sleepTime);
				}
			} else {
				log.info("UsageAmountVMTreatment Class execute Method��ORDER_INFO �� �޶���������Ҫ����!");
				logUtil.info("UsageAmountVMTreatment Class execute Method��ORDER_INFO �� �޶���������Ҫ����!");
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
	 * �޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
	 * @param eachMachine
	 * @param theMachineStart
	 * @param serversNo
	 * @param orders
	 */
	private void SetData(int eachMachine, int theMachineStart, int serversNo,String timeTip,List<EstOrderBean> estOrders) {
		UsageAccountDao usageAccountDao = new UsageAccountDao();
		UsageAccountBean usageAccountBean = new UsageAccountBean();
		usageAccountBean.setSCANNING_WAY("1");	//"1":ÿ��ɨ��һ��
		usageAccountBean.setSERVERS_NO_STATE(serversNo);
		usageAccountDao.SetData(usageAccountBean, eachMachine, theMachineStart,timeTip,estOrders);
	}

	/**
	 * ���ж���������Ҫ����
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

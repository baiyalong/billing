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
 * ʵ������-��������ۣ�
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
			/*** �鿴��Ҫ����Ķ������� ***/
			List<String> ips = EnvironmentUtils.getLocalIPList(); // ��ǰ����IP
			if (ips.isEmpty()) {
				log.info("MonthsTreatment Class execute Method����ȡIPʧ�ܣ���");
				logUtil.info("MonthsTreatment Class execute Method����ȡIPʧ�ܣ���");
				return;
			}
			if (map.get("ServersNum") == null || map.get("ThreadNum") == null
					|| map.get("ThreadOneNum") == null
					|| map.get("SleepTime") == null || map.get("IP") == null
					|| map.get("No") == null) {
				log.info("UsageAmountVMTreatment Class execute Method��T_JOB_TASK ��  PARMS �ֶ� �������������󣡣�");
				logUtil.info("UsageAmountVMTreatment Class execute Method��T_JOB_TASK ��  PARMS �ֶ� �������������󣡣�");
				return;
			}
			if (!ips.contains(map.get("IP"))) {
				log.info("UsageAmountVMTreatment Class execute Method��T_JOB_TASK ��  PARMS �ֶ� IP�������󣡣�");
				logUtil.info("UsageAmountVMTreatment Class execute Method��T_JOB_TASK ��  PARMS �ֶ�IP�������󣡣�");
				return;
			}
			// ���ж���������Ҫ����
			List<OrderInfoBean> orders = HandleDataTotal();
			int treatmentNum = orders.size();
			log.info("���°���۷ѵĹ���" + treatmentNum + "��������Ҫ����.");
			logUtil.info("���°���۷ѵĹ���" + treatmentNum + "��������Ҫ����.");
			if (treatmentNum > 0) {
				int serversNum = Integer.parseInt((String) map
						.get("ServersNum")); // ����̨������
				int eachMachine = 0; // ��̨�����������������
				int serversNo = Integer.parseInt((String) map.get("No")); // ���������
				int theMachineStart = 0; // �˷������������ݵĿ�ʼ��
				int threadNum = Integer.parseInt((String) map.get("ThreadNum")); // ���������̴߳�������
				int tTNum = 0; // ÿ���̴߳������������
				int threadOneNum = Integer.parseInt((String) map
						.get("ThreadOneNum")); // ÿ���߳�һ�δ������������
				int sleepTime = Integer.parseInt((String) map.get("SleepTime")); // �߳��������ʱ��

				/*** ÿ̨��������Ҫ����������� ***/
				int eachMachineT = 0;
				if (treatmentNum % serversNum == 0) {
					eachMachineT = treatmentNum / serversNum;
				} else {
					eachMachineT = (treatmentNum / serversNum);
				}

				/*** ���ݷ������ı�ţ��ó��˷�������Ҫ����������� �� ��ʼ�� ***/
				// �ж��Ƿ�Ϊ���һ̨�����������������ʣ�µ�����
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

				// �޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
				SetData(eachMachine, theMachineStart, serversNo, orders);

				/*** ��������ÿ���̴߳���������� ***/
				if (eachMachine % threadNum == 0) {
					tTNum = eachMachine / threadNum;
				} else {
					tTNum = (eachMachine / threadNum);
				}
				orders = null;
				log.info("���� " + treatmentNum + " ��������Ҫ���� " + serversNum
						+ " ̨�����������������Ϊ��" + serversNo + "�˷��������� " + eachMachine
						+ " �����ݣ��˷������������ݵĿ�ʼ�㣺" + theMachineStart + "������ "
						+ threadNum + " ���̴߳������ݣ�ÿ���߳�һ�δ��� " + threadOneNum
						+ " �����ݣ��߳��������ʱ�䣺" + sleepTime);
				logUtil.info("���� " + treatmentNum + " ��������Ҫ���� " + serversNum
						+ " ̨�����������������Ϊ��" + serversNo + "�˷��������� " + eachMachine
						+ " �����ݣ��˷������������ݵĿ�ʼ�㣺" + theMachineStart + "������ "
						+ threadNum + " ���̴߳������ݣ�ÿ���߳�һ�δ��� " + threadOneNum
						+ " �����ݣ��߳��������ʱ�䣺" + sleepTime);

				for (int i = 0; i < threadNum; i++) {
					// �ж��Ƿ�Ϊ���һ�Σ����������ʣ�µ�����
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
				log.info("MonthsTreatment Class execute Method��ORDER_INFO �� �޶���������Ҫ����!");
				logUtil.info("MonthsTreatment Class execute Method��ORDER_INFO �� �޶���������Ҫ����!");
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
	 * �޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
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
		usageMonthsBean.setFIXED_OR_MONTHLY(1); // 1������� 0���³���
		usageMonthsDao.SetData(usageMonthsBean, eachMachine, theMachineStart,
				orders);
	}

	/**
	 * ���ж���������Ҫ����
	 * 
	 * @return
	 */
	private List<OrderInfoBean> HandleDataTotal() {
		QueryDao queryDao = new QueryDao();
		OrderInfoBean orderInfoBean = new OrderInfoBean();
		orderInfoBean.setORDER_STATUS(3); // 1���ѽ���
		orderInfoBean.setPRODUCT_CATEGORY(1); // ��Ʒ���� 1�������� 2��Ӧ����
		List<HashMap<String, Object>> list = queryDao
				.getMonthsOrderNum(orderInfoBean);
		List<OrderInfoBean> l = orderInfoBean.changeToObject(list);
		return l;
	}

}

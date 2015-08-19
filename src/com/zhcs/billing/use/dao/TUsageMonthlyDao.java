package com.zhcs.billing.use.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.TUsageEventsBean;
import com.zhcs.billing.use.bean.UsageMonthlyBean;
import com.zhcs.billing.util.BaseDao;
import com.zhcs.billing.util.BillingBaseDao;
import com.zhcs.billing.util.Common;

/**
 * ��ѯ�������������Ϣ�ࣨ����ۣ�
 * 
 * @author ���
 *
 */
public class TUsageMonthlyDao {

	private static Logger log = LoggerFactory.getLogger(TUsageMonthlyDao.class);
	private BillingBaseDao billing;
	private BaseDao basedao;

	public BaseDao GetBaseDao() {
		if (basedao == null) {
			basedao = new BaseDao();
		}
		return basedao;
	}

	public BillingBaseDao GetBillingBaseDao() {
		if (billing == null) {
			billing = new BillingBaseDao();
		}
		return billing;
	}

	/**
	 * �� �ʷ������(T_USAGE_MONTHLY) ��Ӽ�¼
	 * �ֶ���(����id,���,�ͻ���š�����ۻ��³��ۡ��¿۽�������š���Ʒ��š���Ʒ���͡�ɨ��״̬,״̬,���������״̬)
	 * 
	 * @param usageMonthlyBean
	 */
	@SuppressWarnings("unchecked")
	public void insertTUsageMonthly(UsageMonthlyBean usageMonthlyBean) {
		String insql = "INSERT INTO T_USAGE_MONTHLY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List param = new ArrayList();
		param.add(Common.createBillingID()); // ����
		param.add(usageMonthlyBean.getBILLSEQ_ID()); // ��� ���� T_Bill_Cycle_Seq
		param.add(usageMonthlyBean.getCUSTOMER_ID()); // �ͻ����
		param.add(usageMonthlyBean.getFIXED_OR_MONTHLY()); // ����ۻ��³��� 0�������
		param.add(usageMonthlyBean.getSCANNING_WAY()); // ɨ�跽ʽ
		param.add(usageMonthlyBean.getUNIT_RATE()); // �¿۽�� (����)
		param.add(usageMonthlyBean.getORDER_ID()); // �������
		param.add(usageMonthlyBean.getPRODUCT_ID()); // ��Ʒ���
		param.add(usageMonthlyBean.getPRODUCT_TYPE()); // ��Ʒ����
		param.add(usageMonthlyBean.getSCANNING_WAY()); // ɨ��״̬
		param.add(usageMonthlyBean.getSTATE()); // ״̬
		param.add(usageMonthlyBean.getSERVERS_NO_STATE()); // ���������״̬
		param.add(usageMonthlyBean.getPRODUCT_CATEGORY()); // ��Ʒ���ࣨ1�������� 2��Ӧ���ࣩ
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(insql, param);
			if (num != 0) {
				log.info("TUsageMonthlyDao Class insertTUsageMonthly Method�� ���T_USAGE_MONTHLY��ɹ�,����������Ҫ���������״̬Ϊ���������:"
						+ usageMonthlyBean.getSERVERS_NO_STATE() + "�ɹ�����");
			}
		} catch (Exception e) {
			log.error("TUsageMonthlyDao Class insertTUsageMonthly Method", e);
			e.printStackTrace();
		}
		if (num == 0) {
			log.info("TUsageMonthlyDao Class insertTUsageMonthly Method�� ���T_USAGE_MONTHLY��ʧ��,����������Ҫ���������״̬Ϊ���������:"
					+ usageMonthlyBean.getSERVERS_NO_STATE() + "ʧ�ܣ���");
		}
		param = null;
	}

	/**
	 * �� �굥����̬�����£���Ӽ�¼
	 * 
	 * @param usageEventsBean
	 */
	@SuppressWarnings("unchecked")
	public int insertTUsageEvents(TUsageEventsBean usageEventsBean) {
		String insql = "insert into T_USAGE_EVENTS_"
				+ Common.getDateStr().substring(0,
						Common.getDateStr().length() - 2)
				+ " values(?,?,?,?,?,?,?,?,?,?)";
		List orderParam = new ArrayList();
		orderParam.add(Common.getStrSSS()); // ����id
		orderParam.add(usageEventsBean.getBILLCYCLE_ID()); // ��� ����
															// T_Bill_Cycle_Seq
		orderParam.add(Common.getDateTimeSSS()); // ʱ��
		orderParam.add(usageEventsBean.getCUSTOMER_ID()); // �ͻ����
		orderParam.add(usageEventsBean.getBILLING_ID()); // �ӿ�֮��ı�ʶ��
		orderParam.add(usageEventsBean.getPRUDUCT_ID()); // ��Ʒ���
		orderParam.add(usageEventsBean.getREALOCKSTAT()); // ʵ�۽��(1ʵ����2ʵ�����3ʵ��ʧ��)
		orderParam.add(usageEventsBean.getACCOUNT()); // ʵ�۽��
		orderParam.add(usageEventsBean.isFEE_TYPE()); // ��������
		orderParam.add(usageEventsBean.getSCHEDULEDSCANNINGSTATE());// ��ʱɨ��״̬
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(insql, orderParam);
			if (num != 0) {
				log.info("����굥��T_USAGE_EVENTS_"
						+ Common.getDateStr().substring(0,
								Common.getDateStr().length() - 2) + "�ɹ�����һ�����ݣ�"
						+ num);
			}
		} catch (Exception e) {
			log.error("����굥��T_USAGE_EVENTS_"
					+ Common.getDateStr().substring(0,
							Common.getDateStr().length() - 2) + "���ʧ��"
					+ e.getMessage());
			e.printStackTrace();
		}
		orderParam = null;
		return num;
	}

	/**
	 * �޸Ĵ˷���������Ҫ���������״̬Ϊ����������� serversNo,�˷��������߳�ֻ����˷�������Ҫ���������
	 * 
	 * @param usageMonthlyBean
	 * @param eachMachine
	 * @param theMachineStart
	 */
	@SuppressWarnings("unchecked")
	public void SetData(UsageMonthlyBean usageMonthlyBean, int eachMachine,
			int theMachineStart) {

		String sql = "UPDATE T_USAGE_MONTHLY SET SERVERS_NO_STATE = ? WHERE ID IN "
				+ "(SELECT T.ID FROM(SELECT ID FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? ORDER BY ID LIMIT "
				+ theMachineStart + "," + eachMachine + ")AS T)";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		int num = 0;
		try {
			num = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if (num != 0) {
				log.info("TUsageMonthlyDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"
						+ usageMonthlyBean.getSERVERS_NO_STATE() + "�ɹ�����");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("TUsageMonthlyDao Class SetData Method", e);
		}
		if (num == 0) {
			log.info("TUsageMonthlyDao Class SetData Method���޸ķ���������Ҫ���������״̬Ϊ���������:"
					+ usageMonthlyBean.getSERVERS_NO_STATE() + "ʧ�ܣ���");
			return;
		}
		params = null;
	}

	/**
	 * �޸�STATE Ϊ threadName ����ʾ���߳�����ɨ�赱��
	 * 
	 * @param usageMonthlyBean
	 * @param threadOneNum
	 * @return
	 */
	public int scanning(UsageMonthlyBean usageMonthlyBean, int threadOneNum) {
		String sql = "UPDATE T_USAGE_MONTHLY SET STATE = ?,COMPLETE_STATE = ? WHERE ID IN (SELECT T.ID FROM(SELECT ID FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? AND SERVERS_NO_STATE = ? LIMIT 0,"
				+ threadOneNum + ")AS T)";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getSTATE());
		params.add(usageMonthlyBean.getCOMPLETE_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			if (stateNum != 0) {
				log.info("TUsageMonthlyDao Class scanning Method:�޸�T_USAGE_MONTHLY ��STATE Ϊ "
						+ usageMonthlyBean.getSTATE() + "�ɹ�����");
			}
		} catch (Exception e) {
			log.error("TUsageMonthlyDao Class scanning Method ", e);
			e.printStackTrace();
		}
		if (stateNum == 0) {
			log.info("TUsageMonthlyDao Class scanning Method:�޸�T_USAGE_MONTHLY ��STATE Ϊ "
					+ usageMonthlyBean.getSTATE() + "ʧ�ܣ���");
		}
		params = null;
		return stateNum;
	}

	/**
	 * �޸�STATE Ϊ ��ɣ���ʾ���߳��Ѿ�ɨ�赱���
	 * 
	 * @param usageMonthlyBean
	 * @param ClassName
	 */
	public void updateUsageMonthlyState(UsageMonthlyBean usageMonthlyBean,
			String ClassName) {
		String sql = "UPDATE T_USAGE_MONTHLY SET COMPLETE_STATE = ? WHERE SCANNING_WAY = ? AND STATE = ? AND SERVERS_NO_STATE = ?";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getCOMPLETE_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		params.add(usageMonthlyBean.getSTATE());
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		int stateNum = 0;
		try {
			stateNum = GetBillingBaseDao().doSaveOrUpdate(sql, params);
			log.info(" TUsageMonthlyDao Class run updateUsageMonthlyState Method �޸�  T_USAGE_MONTHLY �� STATE = ���   �ɹ�.���޸������ݣ�"
					+ stateNum);
		} catch (Exception e) {
			log.error("" + ClassName
					+ " Class run updateUsageMonthlyState Method ", e);
		}
		params = null;
		if (stateNum == 0) {
			log.info(" TUsageMonthlyDao Class run updateUsageMonthlyState Method �޸�  T_USAGE_MONTHLY �� STATE = ���   ʧ�ܣ���");
			return; // �޸�ʧ��
		}

	}

	/**
	 * �ж������Ƿ��Ѿ�������ɣ����T_USAGE_MONTHLY��Ϊ���������
	 * 
	 * @param usageAccountBean
	 * @param tTNum
	 */
	public void deleteDate(UsageMonthlyBean usageMonthlyBean, int tTNum) {
		try {
			String sql = "SELECT COUNT(*) NUM FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
			List params = new ArrayList();
			params.add(usageMonthlyBean.getSCANNING_WAY());
			params.add(usageMonthlyBean.getSTATE());
			params.add(usageMonthlyBean.getCOMPLETE_STATE());
			List<HashMap<String, Object>> list = GetBillingBaseDao().doSelect(
					sql, params);
			int num = Integer.parseInt(list.get(0).get("NUM").toString()); // �����̹߳��д����˶�������
			log.info("��ѯT_USAGE_ACCOUNT���߳�:" + usageMonthlyBean.getSTATE()
					+ " ���д�����:" + num + " ��״̬Ϊ:"
					+ usageMonthlyBean.getCOMPLETE_STATE() + "���ݣ�Ӧ�ô������Ϊ��"
					+ tTNum);
			if (num == tTNum) {
				String sqlD = "DELETE FROM T_USAGE_MONTHLY WHERE SCANNING_WAY = ? AND STATE = ? AND COMPLETE_STATE = ?";
				List paramsD = new ArrayList();
				paramsD.add(usageMonthlyBean.getSCANNING_WAY());
				paramsD.add(usageMonthlyBean.getSTATE());
				paramsD.add(usageMonthlyBean.getCOMPLETE_STATE());
				GetBillingBaseDao().doDelete(sqlD, paramsD);
				params = null;
				log.info("�ɹ�ɾ���߳�:" + usageMonthlyBean.getSTATE()
						+ " �Ѿ�������ɵ����� T_USAGE_MONTHLY��,ɾ������Ϊ:" + num + "״̬Ϊ:"
						+ usageMonthlyBean.getCOMPLETE_STATE());
			}
		} catch (Exception e) {
			log.error("TUsageMonthlyDao Class deleteDate Method ! ", e);
		}

	}

	/**
	 * ��ѯ�ʷ������
	 * 
	 * @param usageMonthlyBean
	 * @return
	 */
	public List<UsageMonthlyBean> listrate(UsageMonthlyBean usageMonthlyBean) {
		String sql = "SELECT * FROM T_USAGE_MONTHLY T WHERE T.SERVERS_NO_STATE = ? AND T.SCANNING_WAY = ? AND T.STATE = ?";
		List params = new ArrayList();
		params.add(usageMonthlyBean.getSERVERS_NO_STATE());
		params.add(usageMonthlyBean.getSCANNING_WAY());
		params.add(usageMonthlyBean.getSTATE());
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<UsageMonthlyBean> l = new ArrayList<UsageMonthlyBean>();
		list = GetBillingBaseDao().doSelect(sql, params);
		l = changeToObject(list);
		params = null;
		list = null;
		return l;
	}

	/**
	 * �����ؽ��ת��UsageMonthlyBean�б����
	 * 
	 * @param list
	 * @return
	 */
	private List<UsageMonthlyBean> changeToObject(
			List<HashMap<String, Object>> list) {
		List<UsageMonthlyBean> l = new ArrayList<UsageMonthlyBean>();
		for (HashMap<String, Object> d : list) {
			UsageMonthlyBean usageMonthlyBean = new UsageMonthlyBean();
			usageMonthlyBean.setBILLSEQ_ID(d.get("BILLSEQ_ID") != null ? d.get(
					"BILLSEQ_ID").toString() : null);
			usageMonthlyBean.setCUSTOMER_ID(d.get("CUSTOMER_ID") != null ? d
					.get("CUSTOMER_ID").toString() : null);
			usageMonthlyBean.setFIXED_OR_MONTHLY(Integer.parseInt(d
					.get("FIXED_OR_MONTHLY") != null ? d
					.get("FIXED_OR_MONTHLY").toString() : null));
			usageMonthlyBean.setSCANNING_WAY(d.get("SCANNING_WAY") != null ? d
					.get("SCANNING_WAY").toString() : null);
			usageMonthlyBean.setUNIT_RATE(d.get("UNIT_RATE") != null ? d.get(
					"UNIT_RATE").toString() : null);
			usageMonthlyBean.setORDER_ID(d.get("ORDER_ID") != null ? d.get(
					"ORDER_ID").toString() : null);
			usageMonthlyBean.setPRODUCT_ID(d.get("PRODUCT_ID") != null ? d.get(
					"PRODUCT_ID").toString() : null);
			usageMonthlyBean.setPRODUCT_TYPE(d.get("PRODUCT_TYPE") != null ? d
					.get("PRODUCT_TYPE").toString() : null);
			usageMonthlyBean
					.setCOMPLETE_STATE(d.get("COMPLETE_STATE") != null ? d.get(
							"COMPLETE_STATE").toString() : null);
			usageMonthlyBean.setSTATE(d.get("STATE") != null ? d.get("STATE")
					.toString() : null);
			usageMonthlyBean.setSERVERS_NO_STATE(Integer.parseInt(d
					.get("SERVERS_NO_STATE") != null ? d
					.get("SERVERS_NO_STATE").toString() : null));
			usageMonthlyBean.setPRODUCT_CATEGORY(Integer.parseInt(d
					.get("PRODUCT_CATEGORY") != null ? d
					.get("PRODUCT_CATEGORY").toString() : null));
			l.add(usageMonthlyBean);
		}
		return l;
	}

}

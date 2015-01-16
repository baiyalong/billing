package com.zhcs.billing.realTime;

import java.lang.Runnable;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhcs.billing.use.bean.RDetailRecordAbilityBean;
import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingInsert;
import com.zhcs.billing.use.dao.BillingQuery;
import com.zhcs.billing.util.LoggerUtil;

public class ThreadAbility implements Runnable {

	private static LoggerUtil logUtil = LoggerUtil
			.getLogger(ThreadAbility.class);
	private static Logger log = LoggerFactory.getLogger(ThreadAbility.class);

	private String msg = null;// ԭʼ����

	public ThreadAbility(String msg) {
		this.msg = msg;
		bean = new RDetailRecordAbilityBean();
		bean.setORIGINAL_RECORD(msg);
	}

	public void run() {

		if (!(this.GetData() && this.Billing() && this.Persistence())) {
			Msg.ErrorRecord(new RErrorRecordBean(Msg.MsgType.Ability.value(),
					this.msg, "ʵʱ�Ʒ�-��������-�쳣"));
		}
	}

	private RDetailRecordAbilityBean bean;

	// ��ȡ�Ʒ������������ݣ����������ݼ���
	private boolean GetData() {
		boolean res = false;
		try {
			// ��ԭʼ��������ȡ����ID�ͻ�������
			bean.setCOUNTAINER_ID(Msg.containerID(msg));// ����ID
			bean.setMSG_TYPE(Msg.msgType(msg)); // ��������
			bean.setORIGINAL_RECORD_TIME(Msg.recordTime(msg)); // ԭʼ��������ʱ��

			// �����ݿ��л�ȡ������ϵ���û���Ϣ����Ʒ�ʷ�
			// �⻧ID �������� ��Ʒ�ʷ�
			res = BillingQuery.rInfoAbility(bean);

		} catch (Exception e) {
			// TODO: handle exception
			res = false;
		}
		return res;
	}

	// �۷�
	private boolean Billing() {
		boolean res = true;
		try {
			// ���ȿ۳��ײ�
			if (!BillingInsert.RPackage(bean)) {
				// ���˻����
				if (!BillingInsert.RBalance(bean)) {
					// Ƿ��֪ͨ
					// TODO-----------------------------------------------------------------
					log.info("���㣬Ƿ�ѣ�֪ͨҵ�����ƽ̨������");
					logUtil.info("���㣬Ƿ�ѣ�֪ͨҵ�����ƽ̨������");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}

		return res;
	}

	// �굥���
	private boolean Persistence() {
		boolean res = true;
		try {
			BillingInsert.RDetailRecordAbility(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}
		return res;
	}

}

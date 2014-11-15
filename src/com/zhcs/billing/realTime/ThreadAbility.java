package com.zhcs.billing.realTime;

import java.lang.Runnable;

import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingInsert;

public class ThreadAbility implements Runnable {

	private String msg = null;

	public ThreadAbility(String msg) {
		this.msg = msg;
	}

	public void run() {
		System.out
				.println(msg + "  ThreadID��" + Thread.currentThread().getId());

		if (!(this.GetData() && this.Billing() && this.Settlement() && this
				.Persistence())) {
			return;
		}

	}

	private String containerID = null;// ����ID
	private int msgType = 0;// �������͡�
	// 10 ����
	// 11 ����
	// 12 ��λ
	// 13 GIS
	private int price = 0;// �ʷ�
	private int amount = 0;// ���۽��

	// ��ȡ�Ʒ������������ݣ����������ݼ���
	private boolean GetData() {
		boolean res = false;
		try {
			// ��ԭʼ��������ȡ����ID�ͻ�������
			containerID = Msg.containerID(msg);// ����ID
			msgType = Msg.msgType(msg);// ��������
			if (!(msgType == 10 || msgType == 11 || msgType == 12 || msgType == 13)) {
				this.ErrorRecord("ԭʼ�����쳣");
				return false;
			}
			// �����ݿ��л�ȡ������ϵ���û���Ϣ����Ʒ�ʷ�

			res = true;
		} catch (Exception e) {
			// TODO: handle exception
			this.ErrorRecord("ԭʼ�����쳣");
			res = false;
		}
		return res;
	}

	// �Ʒ� ��׼����
	private boolean Billing() {
		// ���ڵ�ǰ������ʾһ����¼���������Ϊ��׼�ʷ�x1
		this.amount = this.price;
		return true;
	}

	// �۷�
	private boolean Settlement() {
		// ���ȿ۳��ײ�(����)

		// ���˻����(����)

		// Ƿ��֪ͨ
		boolean res = false;
		return res;
	}

	// �굥���
	private boolean Persistence() {
		boolean res = false;
		return res;
	}

	// ��¼�쳣����
	private void ErrorRecord(String description) {
		RErrorRecordBean bean = new RErrorRecordBean(
				Msg.MsgType.Ability.value(), this.msg, description);
		BillingInsert.RErrorRecord(bean);
	}

}

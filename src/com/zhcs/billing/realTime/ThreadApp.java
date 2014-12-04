package com.zhcs.billing.realTime;

import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingInsert;

public class ThreadApp implements Runnable {

	private String msg = null;

	public ThreadApp(String msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		if (!(this.GetData() && this.Billing() && this.Persistence())) {
			Msg.ErrorRecord(new RErrorRecordBean(Msg.MsgType.Ability.value(),
					this.msg, "ʵʱ�Ʒ�-Ӧ�û���-�쳣"));
		}

	}

	private boolean GetData() {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean Persistence() {
		// TODO Auto-generated method stub
		boolean res = false;
		try {
			BillingInsert.RDetailRecordApp(this.msg);
			res = true;
		} catch (Exception e) {
			res = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	private boolean Billing() {
		// TODO Auto-generated method stub
		return true;
	}

}

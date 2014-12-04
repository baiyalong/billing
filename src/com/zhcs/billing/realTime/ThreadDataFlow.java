package com.zhcs.billing.realTime;

import com.zhcs.billing.use.bean.RDetailRecordDataFlowBean;
import com.zhcs.billing.use.bean.RErrorRecordBean;

public class ThreadDataFlow implements Runnable {

	public ThreadDataFlow(String msg) {
		this.msg = msg;
		bean = new RDetailRecordDataFlowBean();
		bean.setORIGINAL_RECORD(msg);
	}

	private String msg;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!(this.GetData() && this.Billing() && this.Persistence())) {
			Msg.ErrorRecord(new RErrorRecordBean(Msg.MsgType.Ability.value(),
					this.msg, "实时计费-流量话单-异常"));
		}
	}

	private RDetailRecordDataFlowBean bean;

	private boolean Persistence() {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean Billing() {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean GetData() {
		// TODO Auto-generated method stub
		return true;
	}

}

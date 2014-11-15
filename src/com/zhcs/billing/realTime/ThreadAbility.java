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
				.println(msg + "  ThreadID：" + Thread.currentThread().getId());

		if (!(this.GetData() && this.Billing() && this.Settlement() && this
				.Persistence())) {
			return;
		}

	}

	private String containerID = null;// 容器ID
	private int msgType = 0;// 话单类型　
	// 10 短信
	// 11 彩信
	// 12 定位
	// 13 GIS
	private int price = 0;// 资费
	private int amount = 0;// 批价金额

	// 获取计费所需的相关数据，并进行数据检验
	private boolean GetData() {
		boolean res = false;
		try {
			// 从原始话单中提取容器ID和话单类型
			containerID = Msg.containerID(msg);// 容器ID
			msgType = Msg.msgType(msg);// 话单类型
			if (!(msgType == 10 || msgType == 11 || msgType == 12 || msgType == 13)) {
				this.ErrorRecord("原始话单异常");
				return false;
			}
			// 从数据库中获取订购关系、用户信息、产品资费

			res = true;
		} catch (Exception e) {
			// TODO: handle exception
			this.ErrorRecord("原始话单异常");
			res = false;
		}
		return res;
	}

	// 计费 标准批价
	private boolean Billing() {
		// 由于当前话单表示一条记录，因此批价为标准资费x1
		this.amount = this.price;
		return true;
	}

	// 扣费
	private boolean Settlement() {
		// 优先扣除套餐(事务)

		// 扣账户余额(事务)

		// 欠费通知
		boolean res = false;
		return res;
	}

	// 详单入库
	private boolean Persistence() {
		boolean res = false;
		return res;
	}

	// 记录异常话单
	private void ErrorRecord(String description) {
		RErrorRecordBean bean = new RErrorRecordBean(
				Msg.MsgType.Ability.value(), this.msg, description);
		BillingInsert.RErrorRecord(bean);
	}

}

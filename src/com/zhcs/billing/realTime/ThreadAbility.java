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

	private String msg = null;// 原始话单

	public ThreadAbility(String msg) {
		this.msg = msg;
		bean = new RDetailRecordAbilityBean();
		bean.setORIGINAL_RECORD(msg);
	}

	public void run() {

		if (!(this.GetData() && this.Billing() && this.Persistence())) {
			Msg.ErrorRecord(new RErrorRecordBean(Msg.MsgType.Ability.value(),
					this.msg, "实时计费-能力话单-异常"));
		}
	}

	private RDetailRecordAbilityBean bean;

	// 获取计费所需的相关数据，并进行数据检验
	private boolean GetData() {
		boolean res = false;
		try {
			// 从原始话单中提取容器ID和话单类型
			bean.setCOUNTAINER_ID(Msg.containerID(msg));// 容器ID
			bean.setMSG_TYPE(Msg.msgType(msg)); // 能力类型
			bean.setORIGINAL_RECORD_TIME(Msg.recordTime(msg)); // 原始话单生成时间

			// 从数据库中获取订购关系、用户信息、产品资费
			// 租户ID 付费类型 产品资费
			res = BillingQuery.rInfoAbility(bean);

		} catch (Exception e) {
			// TODO: handle exception
			res = false;
		}
		return res;
	}

	// 扣费
	private boolean Billing() {
		boolean res = true;
		try {
			// 优先扣除套餐
			if (!BillingInsert.RPackage(bean)) {
				// 扣账户余额
				if (!BillingInsert.RBalance(bean)) {
					// 欠费通知
					// TODO-----------------------------------------------------------------
					log.info("余额不足，欠费，通知业务管理平台。。。");
					logUtil.info("余额不足，欠费，通知业务管理平台。。。");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
		}

		return res;
	}

	// 详单入库
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

package com.zhcs.billing.realTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.zhcs.billing.use.bean.RErrorRecordBean;
import com.zhcs.billing.use.dao.BillingInsert;

public class Msg {

	private String msg = null;

	public Msg(String msg) {
		this.msg = msg;
	}

	public static int count(String msg) {
		int res = 0;
		try {
			res = Integer.parseInt(msg.split("\n")[0].split("\\|")[7]);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}

	public static String[] liMsg(String msg) {
		String[] li = new String[] {};
		try {
			String[] m = msg.split("\n");
			int count = m.length - 1;
			if (count != Msg.count(msg)) {
				// 异常话单
				// TODO: handle exception

			}
			li = Arrays.copyOfRange(m, 1, count + 1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return li;
	}

	// 获取容器ID
	public static String containerID(String msg) {
		return msg.split("\\|")[3];
	}

	// 获取话单类型
	// 10 短信
	// 11 彩信
	// 12 定位
	// 13 GIS
	public static int msgType(String msg) {
		return Integer.parseInt(msg.split("\\|")[8]);
	}

	public enum MsgType {
		// 利用构造函数传参
		Ability(0), App(1), DataFlow(2);

		// 定义私有变量
		private int nCode;

		// 构造函数，枚举类型只能为私有
		private MsgType(int _nCode) {
			this.nCode = _nCode;
		}

		public int value() {
			return this.nCode;
		}

		@Override
		public String toString() {
			return String.valueOf(this.nCode);
		}
	}

	public static Timestamp recordTime(String msg) {
		DateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
		DateFormat fff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = msg.split("\\|")[1];
		try {
			return Timestamp.valueOf(fff.format(ff.parse(str)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 记录异常话单
	public static void ErrorRecord(RErrorRecordBean bean) {
		try {
			BillingInsert.RErrorRecord(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.zhcs.billing.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

	public static String today() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		String today = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());
		return today;
	}

	public static String yestoday() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -1); // 得到前一天
		String yesteday = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());
		return yesteday;
	}

	public static String thisMonth() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 输出前一月的时候要记得加1

		return year + "-" + month + "-01";
	}

	public static String lastMonth() {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.MONTH, -1); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // 输出前一月的时候要记得加1

		return year + "-" + month + "-01";
	}
}

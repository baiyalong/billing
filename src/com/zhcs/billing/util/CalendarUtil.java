package com.zhcs.billing.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {

	public static String today() {
		Calendar calendar = Calendar.getInstance();// ��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
		String today = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());
		return today;
	}

	public static String yestoday() {
		Calendar calendar = Calendar.getInstance();// ��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
		calendar.add(Calendar.DATE, -1); // �õ�ǰһ��
		String yesteday = new SimpleDateFormat("yyyy-MM-dd").format(calendar
				.getTime());
		return yesteday;
	}

	public static String thisMonth() {
		Calendar calendar = Calendar.getInstance();// ��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // ���ǰһ�µ�ʱ��Ҫ�ǵü�1

		return year + "-" + month + "-01";
	}

	public static String lastMonth() {
		Calendar calendar = Calendar.getInstance();// ��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
		calendar.add(Calendar.MONTH, -1); // �õ�ǰһ����
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // ���ǰһ�µ�ʱ��Ҫ�ǵü�1

		return year + "-" + month + "-01";
	}
}

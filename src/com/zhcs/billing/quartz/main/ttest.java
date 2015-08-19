package com.zhcs.billing.quartz.main;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zhcs.billing.realTime.ThreadAbility;
import com.zhcs.billing.threadPool.ThreadPool;
import com.zhcs.billing.util.CalendarUtil;

public class ttest {

	public static void main(String[] args) throws ParseException {
		/*
		 * // TODO Auto-generated method stub String text =
		 * "201501141057040001|20150114105704||PACK1607||||10658500011|10|||9|10|10|10|00||13256433898|13217904245|13256433898|0|20150114105704|20150114105704||||1|2||1|"
		 * ;
		 * 
		 * ThreadPool.getInstance().AddTask(new ThreadAbility(text));
		 */

		/*
		 * Date today = Calendar.getInstance().getTime(); Calendar dd =
		 * Calendar.getInstance(); dd.add(Calendar.DATE, -1); Date yesteday =
		 * dd.getTime();
		 * 
		 * long tt = (today.getTime() - yesteday.getTime())/1000/(24*3600);
		 */

		// Timestamp tt = Timestamp.valueOf(CalendarUtil.today() + " 00:00:00");

		// System.out.println(tt);
		if (!(a() && b() && c())) {
			err();
		}
	}

	public static boolean a() {
		System.out.println("a");
		return false;
	}

	public static boolean b() {
		System.out.println("b");
		return false;
	}

	public static boolean c() {
		System.out.println("c");
		return true;
	}

	public static boolean err() {
		System.out.println("err");
		return true;
	}

}

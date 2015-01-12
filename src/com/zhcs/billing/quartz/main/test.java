package com.zhcs.billing.quartz.main;

import java.util.List;

import com.zhcs.billing.util.EnvironmentUtils;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<String> ll = EnvironmentUtils.getLocalIPList();
		for(String ss : ll)
		{
			System.out.println(ss);
		}
	}
}

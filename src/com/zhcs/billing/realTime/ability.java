package com.zhcs.billing.realTime;

import java.lang.Runnable;

public class ability implements Runnable {

	private String msg = null;

	public ability(String msg) {
		this.msg = msg;
	}

	public void run() {
		System.out.println(msg + "  " + Thread.currentThread().getId());


		
	}
}

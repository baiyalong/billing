package com.zhcs.billing.realTime;

public class ThreadApp implements Runnable {

	private String msg = null;

	public ThreadApp(String msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out
				.println(msg + "  ThreadID£º" + Thread.currentThread().getId());

	}

}

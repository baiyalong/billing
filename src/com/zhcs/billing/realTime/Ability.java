package com.zhcs.billing.realTime;

import java.lang.Runnable;

public class Ability implements Runnable {

	private String msg = null;

	public Ability(String msg) {
		this.msg = msg;
	}

	public void run() {
		System.out.println(msg + "  " + Thread.currentThread().getId());

		GetData();
		Billing();
		Settlement();
		Persistence();

	}

	private String containerID = null;
	private String msgType = null;

	private void GetData() {
		containerID = Msg.containerID(msg);
		msgType = Msg.msgType(msg);
	}

	private void Billing() {

	}

	private void Settlement() {

	}

	private void Persistence() {

	}

}

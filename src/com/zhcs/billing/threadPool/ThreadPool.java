package com.zhcs.billing.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private static class SingletonHolder {
		private static final ThreadPool INSTANCE = new ThreadPool();
	}

	private ThreadPool() {
		this.init();
	}

	public static final ThreadPool getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private ExecutorService pool = null;

	private void init() {
		this.pool = Executors.newCachedThreadPool();
	}

	public boolean AddTask(Runnable task) {
		boolean res = true;
		try {
			this.pool.submit(task);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			res = false;
		}
		return res;
	}
}

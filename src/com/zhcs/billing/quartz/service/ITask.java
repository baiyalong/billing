package com.zhcs.billing.quartz.service;

import java.util.HashMap;

public interface ITask {

	/**
	   * 这里留了一个execute接口给业务系统使用，业务系统写任务的时候，只需要继承Task类就可以了。
	   * @param map 因为很多任务执行可能需要传入参数，这个参数可以配置在JOB_TASK表的PARMS字段里面
	   * 比如配置 
	   * <item>
	   * <key>k</key>
	   * <value>v</value>
	   * </item>
	   * <item>
	   * <key>kk</key>
	   * <value>vv</value>
	   * </item>
	   * 这里在程序运行中，启动业务系统job的时候，会把上面的记录以MAP的形式传递过去。
	   * 这个在PARMS字段里面，那么在业务系统实现这个方法的时候，可以通过
	   * map.get("k") 获得 v 值
	   */
	public void execute(HashMap map);

}

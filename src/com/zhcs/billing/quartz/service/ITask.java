package com.zhcs.billing.quartz.service;

import java.util.HashMap;

public interface ITask {

	/**
	   * ��������һ��execute�ӿڸ�ҵ��ϵͳʹ�ã�ҵ��ϵͳд�����ʱ��ֻ��Ҫ�̳�Task��Ϳ����ˡ�
	   * @param map ��Ϊ�ܶ�����ִ�п�����Ҫ��������������������������JOB_TASK���PARMS�ֶ�����
	   * �������� 
	   * <item>
	   * <key>k</key>
	   * <value>v</value>
	   * </item>
	   * <item>
	   * <key>kk</key>
	   * <value>vv</value>
	   * </item>
	   * �����ڳ��������У�����ҵ��ϵͳjob��ʱ�򣬻������ļ�¼��MAP����ʽ���ݹ�ȥ��
	   * �����PARMS�ֶ����棬��ô��ҵ��ϵͳʵ�����������ʱ�򣬿���ͨ��
	   * map.get("k") ��� v ֵ
	   */
	public void execute(HashMap map);

}

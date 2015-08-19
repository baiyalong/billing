package com.zhcs.billing.util;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtil {

	private static CacheManager singletonManager = CacheManager.create(System
			.getProperty("BILLING_HOME") + "/config/ehcache.xml");

	/**
	 * ��ȡΪcacheName�Ļ��� ��������ڴ�������
	 * 
	 * @param cacheName
	 * @return
	 */
	public static Cache getCache(String cacheName) {
		Cache cache = null;
		if ((cacheName == null) || (cacheName.equals(""))) {
			return null;
		}
		if (singletonManager == null) {
			singletonManager = CacheManager.create();
		}

		if (singletonManager.cacheExists(cacheName)) {
			cache = singletonManager.getCache(cacheName);
		} else {
			singletonManager.addCache(cacheName);
			cache = singletonManager.getCache(cacheName);
		}
		return cache;
	}

	/**
	 * �ж�chcheName�����Ƿ���� �����ڷ���cache ���򷵻�null
	 * 
	 * @param cacheName
	 * @return
	 */
	public static Cache checkCache(String cacheName) {
		Cache cache = null;
		if ((cacheName == null) || (cacheName.equals(""))) {
			return null;
		}
		if (singletonManager == null) {
			singletonManager = CacheManager.create();
		}

		if (singletonManager.cacheExists(cacheName)) {
			cache = singletonManager.getCache(cacheName);
		}
		return cache;
	}

	/**
	 * ���뻺�� ������Ҫʵ����дtoString����������ֵΪid
	 * 
	 * @param name
	 *            ���������
	 * @param list
	 *            �����б�
	 */
	@SuppressWarnings("unchecked")
	public static void putInCache(String name, List list) {
		Cache cache = getCache(name);
		for (Object bean : list) {
			Element element = new Element(bean.toString(), bean);
			cache.put(element);
		}
	}

	/**
	 * ����������뻺��
	 * 
	 * @param name
	 * @param bean
	 */
	public static void putInCache(String name, Object bean) {
		Cache cache = getCache(name);
		Element element = new Element(bean.toString(), bean);
		cache.put(element);
	}

	/**
	 * ɾ�� cacheName ����
	 * 
	 * @param cacheName
	 */
	public static void closeCache(String cacheName) {
		if (singletonManager == null) {
			singletonManager = CacheManager.create();
		}
		if (singletonManager.cacheExists(cacheName)) {
			singletonManager.removeCache(cacheName);
		}
	}

	/**
	 * ɾ�� ���л���
	 */
	public static void clearAll() {
		if (singletonManager != null) {
			singletonManager.clearAll();
		}
	}
}

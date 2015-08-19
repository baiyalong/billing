package com.zhcs.billing.util;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtil {

	private static CacheManager singletonManager = CacheManager.create(System
			.getProperty("BILLING_HOME") + "/config/ehcache.xml");

	/**
	 * 获取为cacheName的缓存 如果不存在创建缓存
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
	 * 判断chcheName缓存是否存在 若存在返回cache 否则返回null
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
	 * 放入缓存 对象需要实现重写toString方法，返回值为id
	 * 
	 * @param name
	 *            缓存的名字
	 * @param list
	 *            对象列表
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
	 * 单个对象放入缓存
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
	 * 删除 cacheName 缓存
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
	 * 删除 所有缓存
	 */
	public static void clearAll() {
		if (singletonManager != null) {
			singletonManager.clearAll();
		}
	}
}

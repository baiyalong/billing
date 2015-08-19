package com.zhcs.billing.util;

import java.util.ArrayList;
import java.util.List;
import net.sf.ehcache.Cache;

public class CacheDao<T> {
	private static LoggerUtil log = LoggerUtil.getLogger(CacheDao.class);

	/**
	 * 从缓存中获取对象列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getFormChache(String name) {
		if (name == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		Cache cache = null;
		cache = CacheUtil.checkCache(name);
		if (cache != null) {
			List<String> listkey = new ArrayList<String>();
			listkey = cache.getKeys();
			for (String key : listkey) {
				list.add((T) cache.get(key).getValue());
			}
			listkey = null;
		}
		return list;
	}
}

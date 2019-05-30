package com.november.first.common.utils;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtils {

private static CacheManager cacheManager = ((EhCacheCacheManager)SpringContextHolder.getBean("cacheManager")).getCacheManager();
//private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

private static final String SYS_CACHE = "sysCaches";

public static Object get(String key){
return get(SYS_CACHE,key);
}

public static void put(String key, Object value){
put(SYS_CACHE,key,value);
}

public static void remove(String key){
remove(SYS_CACHE,key);
}

private static Object get(String sysCache, String key) {
Element element = getCache(sysCache).get(key);
return element==null?null:element.getObjectValue();
}

private static void put(String sysCache, String key, Object value) {
Element element = new Element(key,value);
getCache(sysCache).put(element);
}

private static void remove(String sysCache, String key) {
getCache(sysCache).remove(key);
}

public static Cache getCache(String cacheName){
Cache cache = cacheManager.getCache(cacheName);
if(cache == null){
cacheManager.addCache(cacheName);
cache = cacheManager.getCache(cacheName);
cache.getCacheConfiguration().setEternal(true);
}
return cache;
}

}

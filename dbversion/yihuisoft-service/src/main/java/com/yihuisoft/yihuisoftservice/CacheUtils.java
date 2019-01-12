package com.yihuisoft.yihuisoftservice;

import java.io.NotSerializableException;
import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
/**
 * 缓存工具类
 * @author tonywu
 * @version v1.0.0
 */
public class CacheUtils implements Serializable {
    /**序列id*/
	private static final long serialVersionUID = 1L;
    /**缓存管理*/
	private static CacheManager cacheManager = CacheManager.create();


    public static String getManager() {
        return cacheManager.toString();
    }

    /**
     * 获取缓存
     * @param cacheName
     * @param key
     * @return
     */
    public static Object get(String cacheName, String key) throws NotSerializableException {
        Element element = getCache(cacheName).get(key);
        return element==null?null:element.getObjectValue();
    }

    /**
     * 写入缓存
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value) throws Exception {
        //先删除
        remove(cacheName,key);

        Element element = null;
        try {
            element = new Element(key, value);
//            if (value instanceof java.util.List) {
//            	//element = new Element(key, (Serializable)value);
//                element = new Element(key, value);
//            }else if(value instanceof java.util.Map){
//            	//element = new Element(key, (Serializable)value);
//                element = new Element(key, value);
//            }else{
//            	element = new Element(key, value);
//            }
            getCache(cacheName).put(element);
		} catch (Exception e) {
			//e.printStackTrace();
            System.out.println(e);
		}
    }

    /**
     * 从缓存中移除
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, String key) {
        getCache(cacheName).remove(key);
    }
    
    /**
     * 获得一个Cache，没有则创建一个。
     * @param cacheName
     * @return
     */
    public static Cache getCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null){
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }
    
    /**
     * 清除所有缓存
     */
    public static void removeAll() {

        String[] cacheNames = cacheManager.getCacheNames();
        for(String cacheName:cacheNames){
            //清除该缓存下的所有值
            getCache(cacheName).removeAll();
        }
	}
}

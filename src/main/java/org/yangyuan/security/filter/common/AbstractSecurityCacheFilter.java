package org.yangyuan.security.filter.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 具有缓存的安全过滤器抽象实现
 * <br>
 * 此类没有实现缓存淘汰算法，也没有任何缓存回收机制
 * <br>
 * 所以此缓存存放的元素必须是可以预期的元素，即数量可控，容量可控
 * @author yangyuan
 * @date 2018年4月19日
 */
public abstract class AbstractSecurityCacheFilter implements SecurityFilter{
    /**
     * 缓存容器
     */
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<String, Object>();
    
    /**
     * 缓存
     * <br>
     * 线程安全
     * @param key 键
     * @param value 值
     */
    protected void caching(String key, Object value){
        cache.putIfAbsent(key, value);
    }
    
    /**
     * 获取指定键对应的缓存值
     * <br>
     * 线程安全
     * @param key 键
     * @return 缓存未命中返回null，否则返回缓存的值
     */
    @SuppressWarnings("unchecked")
    protected <T> T cached(String key){
        return (T) cache.get(key);
    }
    
}

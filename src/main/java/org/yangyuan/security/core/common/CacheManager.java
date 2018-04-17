package org.yangyuan.security.core.common;

/**
 * 缓存管理器定义
 * @author yangyuan
 * @date 2018年3月30日 
 * @param <K> key类型
 * @param <V> value类型
 */
public interface CacheManager<K, V> {
    /**
     * 强制缓存失效
     * @param subject
     */
    void invalid(Subject<K, V> subject);
}

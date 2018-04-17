package org.yangyuan.security.core.common;

/**
 * 缓存管理器定义
 * @author yangyuan
 * @date 2018年3月30日 
 */
public interface CacheManager {
    /**
     * 强制主题缓存失效
     * @param subject 主题
     */
    <K, V> void invalid(Subject<K, V> subject);
}

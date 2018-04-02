package org.yangyuan.security.core.common;

/**
 * 资源工厂定义
 * @author yangyuan
 * @date 2018年3月31日
 */
public interface ResourceFactory<T> {
    
    /**
     * 获取资源
     * @return 资源实例
     */
    T getResource();
    
}

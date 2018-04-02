package org.yangyuan.security.core.common;

/**
 * 安全认证主题定义，非常核心的接口
 * @author yangyuan
 * @date 2017年4月26日 
 * @param <T> 主题承载session key类型
 * @param <E> 主题承载session value类型
 */
public interface Subject<T, E> {
    
    /**
     * 获取当前主题关联的唯一标识
     * <p>每个标识代表一个用户；当然，多个标识可能指向同一个用户</p>
     * @return
     */
    String getPrincipal();
    
    /**
     * 获取当前主题关联的session对象
     * @return
     */
    Session<T, E> getSession();
    
    /**
     * 主题是否有效
     * @return
     */
    boolean isValid();
}

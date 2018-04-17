package org.yangyuan.security.core.common;

/**
 * 安全认证主题定义，非常核心的接口
 * @author yangyuan
 * @date 2017年4月26日 
 * @param <T> 主题承载session key类型
 * @param <E> 主题承载session value类型
 */
public interface Subject<T, E> extends SecuritySerializable{
    
    /**
     * 获取当前主题关联的唯一标识
     * <p>每个标识代表一个用户</p>
     * <p>多个标识可能指向同一个用户</p>
     * @return 唯一标识
     */
    String getPrincipal();
    
    /**
     * 获取当前主题关联的session实例
     * @return session实例
     */
    Session<T, E> getSession();
    
    /**
     * 主题是否有效
     * @return  <b>true</b> 有效，<b>false</b> 无效
     */
    boolean isValid();
}

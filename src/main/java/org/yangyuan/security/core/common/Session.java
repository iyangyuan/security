package org.yangyuan.security.core.common;

/**
 * 会话定义
 * @author yangyuan
 * @date 2017年4月26日 
 * @param <T> key 类型
 * @param <E> value 类型
 */
public interface Session<T, E> {
    
    E get(T key);
    
    E set(T key, E value);
    
}

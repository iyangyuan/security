package org.yangyuan.security.core.common;

/**
 * 会话定义
 * @author yangyuan
 * @date 2017年4月26日 
 * @param <T> key 类型
 * @param <E> value 类型
 */
public interface Session<T, E> extends SecuritySerializable{
    
    /**
     * 获取会话数据
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    E get(T key);
    
    /**
     * 设置会话数据
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key, if the implementation supports null values.)
     */
    E set(T key, E value);
    
}

package org.yangyuan.security.dao.common;

import java.util.List;

import org.yangyuan.security.core.common.Subject;

/**
 * 缓存数据访问层定义
 * @author yangyuan
 * @date 2017年4月26日 
 * @param <K> key 类型
 * @param <V> value 类型
 */
public interface CacheSessionDao<K, V> {
    
    /**
     * 读取主题
     * @param subject 主题
     * @return 缓存中的主题
     */
    Subject<K, V> doRead(Subject<K, V> subject);
    
    /**
     * 创建主题
     * @param subject 主题
     */
    void doCreate(Subject<K, V> subject);
    
    /**
     * 移除主题
     * @param subject 主题
     */
    void doDelete(Subject<K, V> subject);
    
    /**
     * 查询用户主题列表
     * <br>
     * 对于多端登陆的场景，此方法必须返回用户所有端的主题
     * <br>
     * 同一个用户可能对应多个主题
     * @param userUnionid 用户唯一标识
     * @return 用户主题列表
     */
    List<Subject<K, V>> queryUserSubjects(String userUnionid);
    
}

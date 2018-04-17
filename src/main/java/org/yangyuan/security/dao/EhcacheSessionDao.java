package org.yangyuan.security.dao;

import java.util.List;

import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.Subject;
import org.yangyuan.security.dao.common.CacheSessionDao;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache缓存数据访问层实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class EhcacheSessionDao implements CacheSessionDao<String, Object>{
    /**
     * 缓存容器
     */
    private final Cache cache;
    
    public EhcacheSessionDao() {
        /**
         * 初始化底层缓存，这里初始化的缓存是带有自定义配置的
         */
        CacheManager manager = CacheManager.newInstance(ResourceManager.core().getAppClassPath() + "security-ehcache.xml");
        cache = manager.getCache("securitySessionCache");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Subject<String, Object> doRead(Subject<String, Object> subject) {
        Element element = cache.get(subject.getPrincipal());
        if(element == null){
            return null;
        }
        
        return (Subject<String, Object>) element.getObjectValue();
    }
    
    @Override
    public void doCreate(Subject<String, Object> subject) {
        Element element = new Element(subject.getPrincipal(), subject);
        cache.put(element);
    }
    
    @Override
    public void doDelete(Subject<String, Object> subject) {
        cache.remove(subject.getPrincipal());
    }
    
    @Override
    public List<Subject<String, Object>> queryUserSubjects(String userUnionid) {
        throw new RuntimeException("method not implements");
    }
    
}

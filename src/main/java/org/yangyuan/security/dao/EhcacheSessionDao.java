package org.yangyuan.security.dao;

import java.util.List;

import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.Subject;
import org.yangyuan.security.dao.common.CacheSessionDao;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;

/**
 * ehcache缓存数据访问层实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class EhcacheSessionDao implements CacheSessionDao<String, Object>{
    /**
     * 缓存名称
     */
    private static final String CACHE_NAME = "securitySubjectCache";
    
    /**
     * 缓存容器
     */
    private final Cache cache;
    
    @SuppressWarnings("deprecation")
    public EhcacheSessionDao() {
        /**
         * 缓存配置
         */
        CacheConfiguration cacheConfig = new CacheConfiguration();
        cacheConfig.name(CACHE_NAME)
                   .maxEntriesLocalHeap(ResourceManager.cache().getMaxElementsInMemory())
                   .eternal(ResourceManager.cache().isEternal())
                   .timeToIdleSeconds(ResourceManager.cache().getTimeToIdleSeconds())
                   .timeToLiveSeconds(ResourceManager.cache().getTimeToLiveSeconds())
                   .memoryStoreEvictionPolicy(ResourceManager.cache().getMemoryStoreEvictionPolicy())
                   .overflowToDisk(ResourceManager.cache().isOverflowToDisk())
                   .diskPersistent(ResourceManager.cache().isDiskPersistent());
        
        /**
         * 缓存配置容器
         */
        Configuration config = new Configuration();
        config.diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"));
        config.cache(cacheConfig);
        
        /**
         * 缓存管理器
         */
        CacheManager manager = CacheManager.newInstance(config);
        cache = manager.getCache(CACHE_NAME);
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
        throw new SecurityException("method not implements");
    }
    
}

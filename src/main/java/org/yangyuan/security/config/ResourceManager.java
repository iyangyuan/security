package org.yangyuan.security.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yangyuan.security.core.SecurityFilterManager;
import org.yangyuan.security.core.common.CacheManager;
import org.yangyuan.security.core.common.ConcurrentSubjectControl;
import org.yangyuan.security.core.common.PrincipalFactory;
import org.yangyuan.security.core.common.SecurityAuthHandler;
import org.yangyuan.security.core.common.SecurityManager;
import org.yangyuan.security.dao.common.AuthSessionDao;
import org.yangyuan.security.dao.common.CacheSessionDao;
import org.yangyuan.security.dao.common.RedisResourceFactory;
import org.yangyuan.security.realm.common.JdbcRealmAdaptor;
import org.yangyuan.security.realm.common.Realm;
import org.yangyuan.security.realm.common.RemoteRealmAdaptor;
import org.yangyuan.security.spring.SecuritySpringHook;
import org.yangyuan.security.util.SecurityConfigUtils;

/**
 * 统一资源管理器
 * @author yangyuan
 * @date 2018年3月30日
 */
public class ResourceManager {
    
    private static final Log log = LogFactory.getLog(ResourceManager.class);
    
    private static CommonResource commonResource;
    private static CacheResource cacheResource;
    private static CookieResource cookieResource;
    private static CoreResource coreResource;
    private static DaoResource daoResource;
    private static SessionResource sessionResource;
    
    static {
        load();
    }
    
    private static void load(){
        RedisResourceFactory redisResourceFactory = getInstance(SecurityConfigUtils.cellString("common.redisResourceFactory"));
        commonResource =
        CommonResource.custom()
                        .redisResourceFactory(redisResourceFactory)
                        .build();
        
        cacheResource = 
        CacheResource.custom()
                        .maxElementsInMemory(SecurityConfigUtils.cellInt("cache.maxElementsInMemory"))
                        .eternal(SecurityConfigUtils.cellBoolean("cache.eternal"))
                        .overflowToDisk(SecurityConfigUtils.cellBoolean("cache.overflowToDisk"))
                        .diskPersistent(SecurityConfigUtils.cellBoolean("cache.diskPersistent"))
                        .timeToIdleSeconds(SecurityConfigUtils.cellInt("cache.timeToIdleSeconds"))
                        .timeToLiveSeconds(SecurityConfigUtils.cellInt("cache.timeToLiveSeconds"))
                        .memoryStoreEvictionPolicy(SecurityConfigUtils.cellString("cache.memoryStoreEvictionPolicy"))
                        .build();
        
        cookieResource = 
        CookieResource.custom()
                        .name(SecurityConfigUtils.cellString("cookie.name"))
                        .domain(SecurityConfigUtils.cellString("cookie.domain"))
                        .path(SecurityConfigUtils.cellString("cookie.path"))
                        .httpOnly(SecurityConfigUtils.cellBoolean("cookie.http_only"))
                        .maxAge(SecurityConfigUtils.cellInt("cookie.max_age"))
                        .secure(SecurityConfigUtils.cellBoolean("cookie.secure"))
                        .build();
        
        sessionResource = 
        SessionResource.custom()
                        .expiresMilliseconds(SecurityConfigUtils.cellLong("session.expiresMilliseconds"))
                        .gcOpen(SecurityConfigUtils.cellBoolean("session.gc.open"))
                        .gcScript(SecurityConfigUtils.cellString("session.gc.script"))
                        .gcDelaySecond(SecurityConfigUtils.cellLong("session.gc.gcDelaySecond"))
                        .build();
        
        SecurityManager securityManager = getInstance(SecurityConfigUtils.cellString("core.securityManager"));
        PrincipalFactory principalFactory = getInstance(SecurityConfigUtils.cellString("core.principalFactory"));
        CacheManager cacheManager = getInstance(SecurityConfigUtils.cellString("core.cacheManager"));
        SecurityAuthHandler securityAuthHandler = getInstance(SecurityConfigUtils.cellString("core.securityAuthHandler"));
        ConcurrentSubjectControl concurrentSubjectControl = getInstance(SecurityConfigUtils.cellString("core.concurrentSubjectControl"));
        coreResource = 
        CoreResource.custom()
                        .useClientSubjectLogin(SecurityConfigUtils.cellBoolean("core.useClientSubjectLogin"))
                        .securityManager(securityManager)
                        .principalFactory(principalFactory)
                        .cacheManager(cacheManager)
                        .securityAuthHandler(securityAuthHandler)
                        .concurrentSubjectControl(concurrentSubjectControl)
                        .build();
        
        CacheSessionDao<String, Object> ehcacheSessionDao = getInstance(SecurityConfigUtils.cellString("dao.ehcacheSessionDao"));
        CacheSessionDao<String, Object> redisSessionDao = getInstance(SecurityConfigUtils.cellString("dao.redisSessionDao"));
        Realm jdbcRealm = getInstance(SecurityConfigUtils.cellString("dao.jdbcRealm"));
        Realm remoteRealm = getInstance(SecurityConfigUtils.cellString("dao.remoteRealm"));
        AuthSessionDao jdbcSessionDao = getInstance(SecurityConfigUtils.cellString("dao.jdbcSessionDao"));
        AuthSessionDao remoteSessionDao = getInstance(SecurityConfigUtils.cellString("dao.remoteSessionDao"));
        JdbcRealmAdaptor jdbcRealmAdaptor = getInstance(SecurityConfigUtils.cellString("dao.jdbcRealmAdaptor"));
        RemoteRealmAdaptor remoteRealmAdaptor = getInstance(SecurityConfigUtils.cellString("dao.remoteRealmAdaptor"));
        daoResource = 
        DaoResource.custom()
                    .ehcacheSessionDao(ehcacheSessionDao)
                    .redisSessionDao(redisSessionDao)
                    .jdbcRealm(jdbcRealm)
                    .remoteRealm(remoteRealm)
                    .jdbcSessionDao(jdbcSessionDao)
                    .remoteSessionDao(remoteSessionDao)
                    .jdbcRealmAdaptor(jdbcRealmAdaptor)
                    .remoteRealmAdaptor(remoteRealmAdaptor)
                    .build();
        
        log.debug("Security过滤器视图\n" + SecurityFilterManager.view());
    }
    
    /**
     * 公共资源
     * @return
     */
    public static CommonResource common(){
        return commonResource;
    }
    
    /**
     * 缓存资源
     * @return
     */
    public static CacheResource cache(){
        return cacheResource;
    }
    
    /**
     * cookie资源
     * @return
     */
    public static CookieResource cookie(){
        return cookieResource;
    }
    
    /**
     * 核心资源
     * @return
     */
    public static CoreResource core(){
        return coreResource;
    }
    
    /**
     * 数据访问资源
     * @return
     */
    public static DaoResource dao(){
        return daoResource;
    }
    
    /**
     * session资源
     * @return
     */
    public static SessionResource session(){
        return sessionResource;
    }
    
    /**
     * 获取bean实例
     * @param name bean完整类路径或spring ioc管理的bean名称
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    private static <T> T getInstance(String name){
        if(name.indexOf(".") > 0){
            return (T) getInstanceFromClass(name);
        }
        return (T) getInstanceFromSpring(name);
    }
    
    /**
     * 根据bean完整类路径，利用反射获取bean实例
     * @param name bean完整类路径
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    private static <T> T getInstanceFromClass(String name) {
        try {
            Class<?> cls =  Class.forName(name);
            return (T) cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据bean名称获取spring ioc 管理的 bean实例
     * @param name bean名称
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    private static <T> T getInstanceFromSpring(String name) {
        return (T) SecuritySpringHook.getBean(name);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(1024);
        
        builder.append("#CommonResource \n");
        if(common() == null){
            builder.append("null");
        }else{
            builder.append(common().toString());
        }
        builder.append("\n");
        
        builder.append("#CookieResource \n");
        if(cookie() == null){
            builder.append("null");
        }else{
            builder.append(cookie().toString());
        }
        builder.append("\n");
        
        builder.append("#CoreResource \n");
        if(core() == null){
            builder.append("null");
        }else{
            builder.append(core().toString());
        }
        builder.append("\n");
        
        builder.append("#DaoResource \n");
        if(dao() == null){
            builder.append("null");
        }else{
            builder.append(dao().toString());
        }
        builder.append("\n");
        
        builder.append("#SessionResource \n");
        if(session() == null){
            builder.append("null");
        }else{
            builder.append(session().toString());
        }
        builder.append("\n");
        
        return new String(builder);
    }
    
}

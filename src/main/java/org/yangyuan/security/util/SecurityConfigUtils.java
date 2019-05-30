package org.yangyuan.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yangyuan.security.servlet.filter.SecurityHttpServletRequestWrapperFilter;

import java.util.Properties;

/**
 * 配置工具
 * @author yangyuan
 * @date 2018年3月30日
 */
@Configuration
public class SecurityConfigUtils {

    /**
     * session
     */
    @Value("${security.session.expiresMilliseconds:2592000000}")
    private String sessionExpiresMilliseconds;
    @Value("${security.session.gc.open:true}")
    private String sessionGcOpen;
    @Value("${security.session.gc.script:for i=48,83,1 do     local partition     if(i > 57) then         partition = string.char(i + 39)     else         partition = string.char(i)     end          local setkey = 'security:session:set:'..partition     local principals = redis.call('ZRANGEBYSCORE', setkey, '-inf', ARGV[1])     redis.call('ZREMRANGEBYSCORE', setkey, '-inf', ARGV[1])     if(principals and (table.maxn(principals) > 0)) then         for ii,vv in ipairs(principals) do             local hashkey = 'security:session:hash:'..partition             redis.call('HDEL', hashkey, vv)         end     end end}")
    private String sessionGcScript;
    @Value("${security.session.gc.gcDelaySecond:86400}")
    private String sessionGcDelaySecond;

    /**
     * cookie
     */
    @Value("${security.cookie.name:sid}")
    private String cookieName;
    @Value("${security.cookie.domain}")
    private String cookieDomain;
    @Value("${security.cookie.path:/}")
    private String cookiePath;
    @Value("${security.cookie.httpOnly:true}")
    private String cookieHttpOnly;
    @Value("${security.cookie.secure:false}")
    private String cookieSecure;
    @Value("${security.cookie.maxAge:315360000}")
    private String cookieMaxAge;

    /**
     * common
     */
    @Value("${security.common.redisResourceFactory}")
    private String commonRedisResourceFactory;

    /**
     * core
     */
    @Value("${security.core.securityManager:org.yangyuan.security.core.DefaultSecurityManager}")
    private String coreSecurityManager;
    @Value("${security.core.principalFactory:org.yangyuan.security.core.DefaultPrincipalFactory}")
    private String corePrincipalFactory;
    @Value("${security.core.cacheManager:org.yangyuan.security.core.DefaultCacheManager}")
    private String coreCacheManager;
    @Value("${security.core.useClientSubjectLogin:false}")
    private String coreUseClientSubjectLogin;
    @Value("${security.core.concurrentSubjectControl:org.yangyuan.security.core.MultiportConcurrentSubjectControl}")
    private String coreConcurrentSubjectControl;
    @Value("${security.core.securityAuthHandler:}")
    private String coreSecurityAuthHandler;
    @Value("${security.core.principalReader:cookie}")
    private String corePrincipalReader;

    /**
     * dao
     */
    @Value("${security.dao.ehcacheSessionDao:org.yangyuan.security.dao.EhcacheSessionDao}")
    private String daoEhcacheSessionDao;
    @Value("${security.dao.redisSessionDao:org.yangyuan.security.dao.RedisSessionDao}")
    private String daoRedisSessionDao;
    @Value("${security.dao.jdbcRealm:org.yangyuan.security.realm.jdbc.JdbcRealm}")
    private String daoJdbcRealm;
    @Value("${security.dao.captchaRealm:org.yangyuan.security.realm.captcha.CaptchaRealm}")
    private String daoCaptchaRealm;
    @Value("${security.dao.remoteRealm:org.yangyuan.security.realm.remote.RemoteRealm}")
    private String daoRemoteRealm;
    @Value("${security.dao.jdbcSessionDao:org.yangyuan.security.dao.JdbcSessionDao}")
    private String daoJdbcSessionDao;
    @Value("${security.dao.remoteSessionDao:org.yangyuan.security.dao.RemoteSessionDao}")
    private String daoRemoteSessionDao;
    @Value("${security.dao.jdbcRealmAdaptor:}")
    private String daoJdbcRealmAdaptor;
    @Value("${security.dao.captchaRealmAdaptor:}")
    private String daoCaptchaRealmAdaptor;
    @Value("${security.dao.remoteRealmAdaptor:}")
    private String daoRemoteRealmAdaptor;

    /**
     * cache
     */
    @Value("${security.cache.maxElementsInMemory:10000}")
    private String cacheMaxElementsInMemory;
    @Value("${security.cache.eternal:false}")
    private String cacheEternal;
    @Value("${security.cache.overflowToDisk:false}")
    private String cacheOverflowToDisk;
    @Value("${security.cache.diskPersistent:false}")
    private String cacheDiskPersistent;
    @Value("${security.cache.timeToIdleSeconds:900}")
    private String cacheTimeToIdleSeconds;
    @Value("${security.cache.timeToLiveSeconds:7200}")
    private String cacheTimeToLiveSeconds;
    @Value("${security.cache.memoryStoreEvictionPolicy:LRU}")
    private String cacheMemoryStoreEvictionPolicy;

    /**
     * captcha
     */
    @Value("${security.captcha.normal.expireSecond:900}")
    private String captchaNormalExpireSecond;
    @Value("${security.captcha.normal.minIntervalSecond:50}")
    private String captchaNormalMinIntervalSecond;
    @Value("${security.captcha.image.expireSecond:600}")
    private String captchaImageExpireSecond;
    @Value("${security.captcha.image.wrongPeriodSecond:60}")
    private String captchaImageWrongPeriodSecond;
    @Value("${security.captcha.image.periodMaxWrongCount:3}")
    private String captchaImagePeriodMaxWrongCount;

    /**
     * 配置容器
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * 配置注入器
     * @return
     */
    @Bean
    public Object securityConfigUtilsInjector(){
        PROPERTIES.put("session.expiresMilliseconds", sessionExpiresMilliseconds);
        PROPERTIES.put("session.gc.open", sessionGcOpen);
        PROPERTIES.put("session.gc.script", sessionGcScript);
        PROPERTIES.put("session.gc.gcDelaySecond", sessionGcDelaySecond);
        PROPERTIES.put("cookie.name", cookieName);
        PROPERTIES.put("cookie.domain", cookieDomain);
        PROPERTIES.put("cookie.path", cookiePath);
        PROPERTIES.put("cookie.httpOnly", cookieHttpOnly);
        PROPERTIES.put("cookie.secure", cookieSecure);
        PROPERTIES.put("cookie.maxAge", cookieMaxAge);
        PROPERTIES.put("common.redisResourceFactory", commonRedisResourceFactory);
        PROPERTIES.put("core.securityManager", coreSecurityManager);
        PROPERTIES.put("core.principalFactory", corePrincipalFactory);
        PROPERTIES.put("core.cacheManager", coreCacheManager);
        PROPERTIES.put("core.useClientSubjectLogin", coreUseClientSubjectLogin);
        PROPERTIES.put("core.concurrentSubjectControl", coreConcurrentSubjectControl);
        PROPERTIES.put("core.securityAuthHandler", coreSecurityAuthHandler);
        PROPERTIES.put("core.corePrincipalReader", corePrincipalReader);
        PROPERTIES.put("dao.ehcacheSessionDao", daoEhcacheSessionDao);
        PROPERTIES.put("dao.redisSessionDao", daoRedisSessionDao);
        PROPERTIES.put("dao.jdbcRealm", daoJdbcRealm);
        PROPERTIES.put("dao.captchaRealm", daoCaptchaRealm);
        PROPERTIES.put("dao.remoteRealm", daoRemoteRealm);
        PROPERTIES.put("dao.jdbcSessionDao", daoJdbcSessionDao);
        PROPERTIES.put("dao.remoteSessionDao", daoRemoteSessionDao);
        PROPERTIES.put("dao.jdbcRealmAdaptor", daoJdbcRealmAdaptor);
        PROPERTIES.put("dao.captchaRealmAdaptor", daoCaptchaRealmAdaptor);
        PROPERTIES.put("dao.remoteRealmAdaptor", daoRemoteRealmAdaptor);
        PROPERTIES.put("cache.maxElementsInMemory", cacheMaxElementsInMemory);
        PROPERTIES.put("cache.eternal", cacheEternal);
        PROPERTIES.put("cache.overflowToDisk", cacheOverflowToDisk);
        PROPERTIES.put("cache.diskPersistent", cacheDiskPersistent);
        PROPERTIES.put("cache.timeToIdleSeconds", cacheTimeToIdleSeconds);
        PROPERTIES.put("cache.timeToLiveSeconds", cacheTimeToLiveSeconds);
        PROPERTIES.put("cache.memoryStoreEvictionPolicy", cacheMemoryStoreEvictionPolicy);
        PROPERTIES.put("captcha.normal.expireSecond", captchaNormalExpireSecond);
        PROPERTIES.put("captcha.normal.minIntervalSecond", captchaNormalMinIntervalSecond);
        PROPERTIES.put("captcha.image.expireSecond", captchaImageExpireSecond);
        PROPERTIES.put("captcha.image.wrongPeriodSecond", captchaImageWrongPeriodSecond);
        PROPERTIES.put("captcha.image.periodMaxWrongCount", captchaImagePeriodMaxWrongCount);

        return new Object();
    }

    /**
     * 注入过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean securityHttpServletRequestWrapperFilter() {
        FilterRegistrationBean registry = new FilterRegistrationBean();
        registry.setFilter(new SecurityHttpServletRequestWrapperFilter());
        registry.addUrlPatterns("/*");
        registry.setName("securityHttpServletRequestWrapperFilter");
        registry.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registry;
    }

    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置值
     */
    public static String cell(String name){
        String result = PROPERTIES.getProperty(name);
        
        if(StringUtils.isBlank(result)){
            return StringUtils.EMPTY;
        }
        
        return result;
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置int值
     */
    public static int cellInt(String name){
        return Integer.parseInt(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置long值
     */
    public static long cellLong(String name){
        return Long.parseLong(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置double值
     */
    public static double cellDouble(String name){
        return Double.parseDouble(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置boolean值
     */
    public static boolean cellBoolean(String name){
        return Boolean.parseBoolean(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置string值
     */
    public static String cellString(String name){
        return cell(name);
    }
    
}

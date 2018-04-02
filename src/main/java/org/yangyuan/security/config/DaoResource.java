package org.yangyuan.security.config;

import org.yangyuan.security.dao.common.AuthSessionDao;
import org.yangyuan.security.dao.common.CacheSessionDao;
import org.yangyuan.security.realm.common.JdbcRealmAdaptor;
import org.yangyuan.security.realm.common.Realm;
import org.yangyuan.security.realm.common.RemoteRealmAdaptor;

/**
 * 数据访问资源定义
 * @author yangyuan
 * @date 2018年3月31日
 */
public class DaoResource {
    /**
     * ehcache缓存管理器
     */
    private final CacheSessionDao<String, Object> ehcacheSessionDao;
    /**
     * redis缓存管理器
     */
    private final CacheSessionDao<String, Object> redisSessionDao;
    /**
     * 持久化数据源
     */
    private final Realm jdbcRealm;
    /**
     * 第三方授权数据源
     */
    private final Realm remoteRealm;
    /**
     * jdbc认证管理器
     */
    private final AuthSessionDao jdbcSessionDao;
    /**
     * 第三方认证管理器
     */
    private final AuthSessionDao remoteSessionDao;
    /**
     * 持久化数据源适配器
     */
    private final JdbcRealmAdaptor jdbcRealmAdaptor;
    /**
     * 第三方授权数据源适配器
     */
    private final RemoteRealmAdaptor remoteRealmAdaptor;
    
    public DaoResource(Builder builder){
        this.ehcacheSessionDao = builder.ehcacheSessionDao;
        this.redisSessionDao = builder.redisSessionDao;
        this.jdbcRealm = builder.jdbcRealm;
        this.remoteRealm = builder.remoteRealm;
        this.jdbcSessionDao = builder.jdbcSessionDao;
        this.remoteSessionDao = builder.remoteSessionDao;
        this.jdbcRealmAdaptor = builder.jdbcRealmAdaptor;
        this.remoteRealmAdaptor = builder.remoteRealmAdaptor;
    }
    
    public CacheSessionDao<String, Object> getEhcacheSessionDao() {
        return ehcacheSessionDao;
    }
    public CacheSessionDao<String, Object> getRedisSessionDao() {
        return redisSessionDao;
    }
    public Realm getJdbcRealm() {
        return jdbcRealm;
    }
    public Realm getRemoteRealm() {
        return remoteRealm;
    }
    public AuthSessionDao getJdbcSessionDao() {
        return jdbcSessionDao;
    }
    public AuthSessionDao getRemoteSessionDao() {
        return remoteSessionDao;
    }
    public JdbcRealmAdaptor getJdbcRealmAdaptor() {
        return jdbcRealmAdaptor;
    }
    public RemoteRealmAdaptor getRemoteRealmAdaptor() {
        return remoteRealmAdaptor;
    }

    /**
     * 自定义数据访问资源构造器
     * @return
     */
    public static Builder custom(){
        return new Builder();
    }
    
    /**
     * 数据访问资源构造器
     * @author yangyuan
     * @date 2018年3月31日
     */
    public static class Builder {
        /**
         * ehcache缓存管理器
         */
        private CacheSessionDao<String, Object> ehcacheSessionDao;
        /**
         * redis缓存管理器
         */
        private CacheSessionDao<String, Object> redisSessionDao;
        /**
         * 持久化数据源
         */
        private Realm jdbcRealm;
        /**
         * 第三方授权数据源
         */
        private Realm remoteRealm;
        /**
         * jdbc认证管理器
         */
        private AuthSessionDao jdbcSessionDao;
        /**
         * 第三方认证管理器
         */
        private AuthSessionDao remoteSessionDao;
        /**
         * 持久化数据源适配器
         */
        private JdbcRealmAdaptor jdbcRealmAdaptor;
        /**
         * 第三方授权数据源适配器
         */
        private RemoteRealmAdaptor remoteRealmAdaptor;
        
        public Builder ehcacheSessionDao(CacheSessionDao<String, Object> ehcacheSessionDao) {
            this.ehcacheSessionDao = ehcacheSessionDao;
            return this;
        }
        public Builder redisSessionDao(CacheSessionDao<String, Object> redisSessionDao) {
            this.redisSessionDao = redisSessionDao;
            return this;
        }
        public Builder jdbcRealm(Realm jdbcRealm) {
            this.jdbcRealm = jdbcRealm;
            return this;
        }
        public Builder remoteRealm(Realm remoteRealm) {
            this.remoteRealm = remoteRealm;
            return this;
        }
        public Builder jdbcSessionDao(AuthSessionDao jdbcSessionDao) {
            this.jdbcSessionDao = jdbcSessionDao;
            return this;
        }
        public Builder remoteSessionDao(AuthSessionDao remoteSessionDao) {
            this.remoteSessionDao = remoteSessionDao;
            return this;
        }
        public Builder jdbcRealmAdaptor(JdbcRealmAdaptor jdbcRealmAdaptor) {
            this.jdbcRealmAdaptor = jdbcRealmAdaptor;
            return this;
        }
        public Builder remoteRealmAdaptor(RemoteRealmAdaptor remoteRealmAdaptor) {
            this.remoteRealmAdaptor = remoteRealmAdaptor;
            return this;
        }
        public DaoResource build(){
            return new DaoResource(this);
        }
    }
    
    
}

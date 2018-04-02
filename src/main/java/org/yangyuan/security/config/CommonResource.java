package org.yangyuan.security.config;

import org.yangyuan.security.dao.common.RedisResourceFactory;

/**
 * 公共资源定义
 * @author yangyuan
 * @date 2018年4月2日
 */
public class CommonResource {
    /**
     * redis连接工厂
     */
    private final RedisResourceFactory redisResourceFactory;
    
    public CommonResource(Builder builder){
        this.redisResourceFactory = builder.redisResourceFactory;
    }
    
    public RedisResourceFactory getRedisResourceFactory() {
        return redisResourceFactory;
    }
    
    /**
     * 公共资源自定义构造器
     * @return
     */
    public static Builder custom(){
        return new Builder();
    }
    
    /**
     * 公共资源构造器
     * @author yangyuan
     * @date 2018年4月2日
     */
    public static class Builder {
        /**
         * redis连接工厂
         */
        private RedisResourceFactory redisResourceFactory;
        
        public Builder redisResourceFactory(RedisResourceFactory redisResourceFactory) {
            this.redisResourceFactory = redisResourceFactory;
            return this;
        }
        public CommonResource build(){
            return new CommonResource(this);
        }
    }
}

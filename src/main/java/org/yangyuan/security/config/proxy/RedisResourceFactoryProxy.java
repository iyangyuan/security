package org.yangyuan.security.config.proxy;

import org.yangyuan.security.dao.common.RedisResourceFactory;

import redis.clients.jedis.Jedis;

/**
 * redis连接工厂代理
 * @author yangyuan
 * @date 2018年6月20日
 */
public class RedisResourceFactoryProxy implements RedisResourceFactory{
    
    private final RedisResourceFactory redisResourceFactory;
    
    public RedisResourceFactoryProxy(RedisResourceFactory redisResourceFactory){
        this.redisResourceFactory = redisResourceFactory;
    }
    
    @Override
    public Jedis getResource() {
        if(redisResourceFactory == null){
            throw new SecurityException("Load security.properties[common.redisResourceFactory] failed!");
        }
        
        return redisResourceFactory.getResource();
    }
    
}

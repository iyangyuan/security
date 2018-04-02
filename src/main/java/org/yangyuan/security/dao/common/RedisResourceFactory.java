package org.yangyuan.security.dao.common;

import org.yangyuan.security.core.common.ResourceFactory;

import redis.clients.jedis.Jedis;

/**
 * redis连接工厂定义
 * @author yangyuan
 * @date 2018年3月31日
 */
public interface RedisResourceFactory extends ResourceFactory<Jedis>{

}

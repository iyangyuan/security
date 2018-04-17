package org.yangyuan.security.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.CacheManager;
import org.yangyuan.security.core.common.Subject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 缓存管理器默认实现
 * @author yangyuan
 * @date 2017年6月6日
 */
public class DefaultCacheManager implements CacheManager{
    
    private static final Log log = LogFactory.getLog(DefaultCacheManager.class);
    
    /**
     * 消息发布频道
     */
    protected static final String MESSAGE_CHANNEL = "service:security:message:channel";
    /**
     * redis消息订阅
     */
    private static JedisPubSub JEDIS_PUB_SUB;
    /**
     * 消息消费线程池
     */
    private static ExecutorService EXECUTOR_MESSAGE;
    /**
     * 消息监听线程池
     */
    private static ExecutorService EXECUTOR_SUBSCRIBE;
    
    static {
        start();
    }
    
    /**
     * 启动
     */
    public static void start(){
        /**
         * 创建线程池
         */
        EXECUTOR_MESSAGE = Executors.newFixedThreadPool(4);
        EXECUTOR_SUBSCRIBE = Executors.newSingleThreadExecutor();
        
        /**
         * redis消息处理
         */
        JEDIS_PUB_SUB = 
        new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                final String _message = message;
                EXECUTOR_MESSAGE.execute(new Runnable() {
                    @Override
                    public void run() {
                        ResourceManager.dao().getEhcacheSessionDao().doDelete(DefaultSubject.getInstance(_message, false, null));  //缓存失效
                    }
                });
            }
        };
        
        /**
         * redis消息订阅
         */
        EXECUTOR_SUBSCRIBE.execute(new Runnable() {
            @Override
            public void run() {
                Jedis redis = null;
                try {
                    redis = ResourceManager.common().getRedisResourceFactory().getResource();
                    redis.subscribe(JEDIS_PUB_SUB, MESSAGE_CHANNEL);
                }catch (Exception e){
                    log.error("[Security][CacheManager][start]", e);
                }finally {
                    if(redis != null){
                        redis.close();
                    }
                }
            }
        });
    }
    
    /**
     * 销毁
     */
    public static void destroy(){
        try {
            /**
             * 取消消息订阅
             */
            JEDIS_PUB_SUB.unsubscribe(MESSAGE_CHANNEL);
        
            /**
             * 关闭线程池
             */
            EXECUTOR_SUBSCRIBE.shutdown();
            while(!EXECUTOR_SUBSCRIBE.isTerminated()){}
            EXECUTOR_MESSAGE.shutdown();
            while(!EXECUTOR_MESSAGE.isTerminated()){}
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    @Override
    public <K, V> void invalid(Subject<K, V> subject){
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            redis.publish(MESSAGE_CHANNEL, subject.getPrincipal());
        }catch (Exception e){
            log.error("[Security][CacheManager][invalid]", e);
        }finally {
            if(redis != null){
                redis.close();
            }
        }
    }
    
}

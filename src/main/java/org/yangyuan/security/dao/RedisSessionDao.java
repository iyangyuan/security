package org.yangyuan.security.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.yangyuan.security.bean.Role;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.DefaultSession;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.common.Subject;
import org.yangyuan.security.dao.common.CacheSessionDao;
import org.yangyuan.security.dao.common.StatisticalSessionDao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * redis数据访问层实现
 * @author yangyuan
 * @date 2017年4月26日
 */
@SuppressWarnings("unchecked")
public class RedisSessionDao implements CacheSessionDao<String, Object>, StatisticalSessionDao {
    private static final Log log = LogFactory.getLog(RedisSessionDao.class);
    
    /**
     * 垃圾回收线程池
     */
    private static ScheduledExecutorService GC_EXECUTOR;
    
    static {
        gcStart();
    }
    
    /**
     * 启动垃圾回收线程
     */
    private static void gcStart(){
        if(!ResourceManager.session().isGcOpen()){
            return;
        }
        
        /**
         * 预加载lua脚本
         */
        final String gcScriptHash;
        Jedis redis = null;
        try{
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            gcScriptHash = redis.scriptLoad(ResourceManager.session().getGcScript());
        }finally {
            if(redis != null){
                redis.close();
            }
        }
        
        GC_EXECUTOR = Executors.newScheduledThreadPool(1);
        
        /**
         * 垃圾回收线程初始化(1个线程)
         */
        GC_EXECUTOR.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Jedis redis = null;
                try {
                    redis = ResourceManager.common().getRedisResourceFactory().getResource();
                    redis.evalsha(gcScriptHash, 0, String.valueOf(System.currentTimeMillis()));  //执行lua脚本，回收数据
                    
                    log.info("[RedisSessionDao]GC");
                    
                } catch (Exception e) {
                    log.error("[RedisSessionDao]垃圾回收线程", e);
                }finally {
                    if(redis != null){
                        redis.close();
                    }
                }
            }
        }, 0, ResourceManager.session().getGcDelaySecond(), TimeUnit.SECONDS);
    }
    
    /**
     * 停止垃圾回收线程
     */
    private static void gcStop(){
        try {
            if(GC_EXECUTOR == null){
                return;
            }
            GC_EXECUTOR.shutdown();
            while(!GC_EXECUTOR.isTerminated()){}
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    @Override
    public Subject<String, Object> doRead(Subject<String, Object> subject) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            /**
             * 从redis中获取subject数据
             */
            String partition = ResourceManager.core().getPrincipalFactory().getPartition(subject.getPrincipal());
            String subjectStr = redis.hget(buildeHashKey(partition), subject.getPrincipal());
            if(StringUtils.isBlank(subjectStr)){
                return null;
            }
            
            /**
             * 解析subject数据
             */
            subjectStr = new String(Base64.decodeBase64(subjectStr), ResourceManager.core().getCharset());
            JSONObject subjectWrap = JSON.parseObject(subjectStr);
            String principal = subjectWrap.getString("principal");
            boolean valid = subjectWrap.getBooleanValue("valid");
            JSONObject sessionWrap = subjectWrap.getJSONObject("session");
            Map<String, Object> sessionMap = new HashMap<String, Object>();
            Set<Entry<String, Object>> entrySet = sessionWrap.entrySet();
            for(Entry<String, Object> entry : entrySet){
                sessionMap.put(entry.getKey(), entry.getValue());
            }
            String permission = (String) sessionMap.get(DefaultSession.SESSION_ROLES);
            if(StringUtils.isBlank(permission)){
                sessionMap.put(DefaultSession.SESSION_ROLES, new ArrayList<Role>());
            }else{
                sessionMap.put(DefaultSession.SESSION_ROLES, Role.parseRole(permission));
            }
            DefaultSession session = new DefaultSession();
            session.fillMap(sessionMap);
            
            /**
             * 刷新session
             */
            redis.zadd(buildeSetKey(partition), System.currentTimeMillis() + ResourceManager.session().getExpiresMilliseconds(), subject.getPrincipal());
            
            /**
             * 统计
             */
            online(principal, redis);
            
            return DefaultSubject.getInstance(principal, valid, session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(redis != null){
                redis.close();
            }
        }
    }

    @Override
    public void doCreate(Subject<String, Object> subject) {
        
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            /**
             * 序列化subject对象
             */
            Map<String, Object> subjectMap = new HashMap<String, Object>();
            subjectMap.put("principal", subject.getPrincipal());
            subjectMap.put("valid", subject.isValid());
            Map<String, Object> sessionMap = ((DefaultSession) subject.getSession()).toMap();
            List<Role> roles = (List<Role>)sessionMap.get(DefaultSession.SESSION_ROLES);
            if(roles.size() == 0){
                sessionMap.put(DefaultSession.SESSION_ROLES, "");
            }else{
                sessionMap.put(DefaultSession.SESSION_ROLES, Role.toPermission(roles));
            }
            subjectMap.put("session", sessionMap);
            
            String subjectStr = JSON.toJSONString(subjectMap);
            subjectStr = Base64.encodeBase64String(subjectStr.getBytes(ResourceManager.core().getCharset()));
            
            /**
             * 持久化session
             */
            String partition = ResourceManager.core().getPrincipalFactory().getPartition(subject.getPrincipal());
            redis.hset(buildeHashKey(partition), subject.getPrincipal(), subjectStr);
            redis.zadd(buildeSetKey(partition), System.currentTimeMillis() + ResourceManager.session().getExpiresMilliseconds(), subject.getPrincipal());
            
            /**
             * 统计
             */
            online(subject.getPrincipal(), redis);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(redis != null){
                redis.close();
            }
        }
        
    }

    @Override
    public void doDelete(Subject<String, Object> subject) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            /**
             * 移除session
             */
            String partition = ResourceManager.core().getPrincipalFactory().getPartition(subject.getPrincipal());
            redis.hdel(buildeHashKey(partition), subject.getPrincipal());
            redis.zrem(buildeSetKey(partition), subject.getPrincipal());
            
        } finally {
            if(redis != null){
                redis.close();
            }
        }
    }
    
    @Override
    public List<Subject<String, Object>> queryUserSubjects(String userUnionid) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            List<String> principals = new ArrayList<String>();
            String partition = ResourceManager.core().getPrincipalFactory().getPartition(ResourceManager.core().getPrincipalFactory().newPrincipal(userUnionid));
            ScanParams params = new ScanParams();
            params.match(userUnionid + "_*");
            params.count(1000);
            ScanResult<Entry<String, String>> result;
            String finished = "0";
            String cursor = finished;
            while(true){
                result = redis.hscan(buildeHashKey(partition), cursor, params);
                cursor = result.getStringCursor();
                if(result.getResult() == null || result.getResult().size() == 0){
                    if(cursor.equals(finished)){
                        break;
                    }
                    continue;
                }
                for(Entry<String, String> entrie : result.getResult()){
                    principals.add(entrie.getKey());
                }
                if(cursor.equals(finished)){
                    break;
                }
            }
            
            List<Subject<String, Object>> subjects = new ArrayList<Subject<String, Object>>();
            for(String principal : principals){
                Subject<String, Object> subject = doRead(DefaultSubject.getInstance(principal, true, null));
                subjects.add(subject);
            }
            
            return subjects;
        } finally {
            if(redis != null){
                redis.close();
            }
        }
    }
    
    @Override
    public long numberOfOnline() {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            return redis.pfcount(buildOnlineKey());
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    /**
     * 用户上线
     * @param principal 安全唯一标识
     * @param redis redis客户端连接
     */
    private void online(String principal, Jedis redis){
        String key = buildOnlineKey();
        boolean newKey = false;
        if(!redis.exists(key)){
            newKey = true;
        }
        
        redis.pfadd(key, ResourceManager.core().getPrincipalFactory().getUserUnionid(principal));
        
        if(newKey){
            redis.expire(key, buildOnlineKeyTtl());
        }
    }
    
    @Override
    public long numberOfActivity() {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            String key;
            long n = 0;
            for(int i = 48; i < 123; i++){
                if(i == 58){
                    i = 97;
                }
                
                key = buildeHashKey(String.valueOf((char) i));
                n = n + redis.hlen(key);
            }
            
            return n;
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    /**
     * 构造在线统计key
     * @return key
     */
    private static String buildOnlineKey(){
        return "security:session:statistical:online";
    }
    
    /**
     * 构造在线统计key有效期
     * <br>
     * 此有效期为缓存有效期的<b>2</b>倍，即<b>security-ehcache.xml</b>配置文件中<b>timeToLiveSeconds</b>值的<b>2</b>倍
     * @return 有效期
     */
    private static int buildOnlineKeyTtl(){
        return 14400;
    }
    
    /**
     * 构造subject hash key
     * <br>
     * hash key 用来存储subject数据
     * @param partition 分区
     * @return
     */
    private static String buildeHashKey(String partition){
        return "security:session:hash:".concat(partition);
    }
    
    /**
     * 构造subject set key
     * <br>
     * set key 用来维护subject生命周期
     * @param partition 分区
     * @return
     */
    private static String buildeSetKey(String partition){
        return "security:session:set:".concat(partition);
    }
    
    /**
     * 应用关闭监听器
     * @author yangyuan
     * @date 2018年3月30日
     */
    @Component
    private static class RedisSessionDaoShutdownListener implements ApplicationListener<ContextClosedEvent> {
        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            gcStop();
        }
    }
    
}

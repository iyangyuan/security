package org.yangyuan.security.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.Role;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.Session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 默认会话实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class DefaultSession implements Session<String, Object>{
    /**
     * 会话中保存的用户全局唯一id域
     */
    public static final String SESSION_UNIONID = "_unionid";
    
    /**
     * 会话中保存的用户角色域
     */
    public static final String SESSION_ROLES = "_roles";
    
    /**
     * 会话中保存的记住登陆状态域
     */
    public static final String SESSION_REMEMBER = "_remember";
    
    /**
     * 会话数据容器
     */
    private Map<String, Object> container = new HashMap<String, Object>();
    
    @Override
    public Object get(String key) {
        return container.get(key);
    }
    
    @Override
    public Object set(String key, Object value) {
        return container.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public byte[] getBytes() {
        try {
            Map<String, Object> map = simpleCopy(this.container);
            List<Role> roles = (List<Role>) this.container.get(SESSION_ROLES);
            map.put(SESSION_ROLES, "");
            if(roles.size() > 0){
                map.put(SESSION_ROLES, Role.getRoles(roles));
            }
            
            String json = JSON.toJSONString(map);
            return json.getBytes(ResourceManager.core().getCharset());
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
    
    /**
     * 反序列化
     * @param bytes 原始字节数组
     * @return session实例
     */
    public static DefaultSession parse(byte[] bytes){
        try {
            DefaultSession session = new DefaultSession();
            String json = new String(bytes, ResourceManager.core().getCharset());
            JSONObject jsonObject = JSON.parseObject(json);
            Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
            for(Entry<String, Object> entry : entrySet){
                session.container.put(entry.getKey(), entry.getValue());
            }
            String roles = (String) session.container.get(SESSION_ROLES);
            session.container.put(SESSION_ROLES, new ArrayList<Role>());
            if(StringUtils.isNotBlank(roles)){
                session.container.put(SESSION_ROLES, Role.parseRoles(roles));
            }
            
            return session;
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
    
    private Map<String, Object> simpleCopy(Map<String, Object> map){
        Map<String, Object> _map = new HashMap<String, Object>();
        Set<Entry<String, Object>> entrySet = map.entrySet();
        for(Entry<String, Object> entry : entrySet){
            _map.put(entry.getKey(), entry.getValue());
        }
        
        return _map;
    }
    
    @Override
    public String toString() {
        try {
            return new String(getBytes(), ResourceManager.core().getCharset());
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
    
}

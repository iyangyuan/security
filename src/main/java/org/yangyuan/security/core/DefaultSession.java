package org.yangyuan.security.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.yangyuan.security.core.common.Session;

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
    
    private Map<String, Object> container = new HashMap<String, Object>();
    
    @Override
    public Object get(String key) {
        return container.get(key);
    }
    
    @Override
    public Object set(String key, Object value) {
        return container.put(key, value);
    }
    
    public Map<String, Object> toMap(){
        return simpleCopy(this.container);
    }
    
    public void fillMap(Map<String, Object> map){
        this.container = simpleCopy(map);
    }
    
    private Map<String, Object> simpleCopy(Map<String, Object> map){
        Map<String, Object> _map = new HashMap<String, Object>();
        Set<Entry<String, Object>> entrySet = map.entrySet();
        for(Entry<String, Object> entry : entrySet){
            _map.put(entry.getKey(), entry.getValue());
        }
        
        return _map;
    }
}

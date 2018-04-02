package org.yangyuan.security.util;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.yangyuan.security.bean.Role;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.DefaultSession;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.SessionManager;
import org.yangyuan.security.core.common.CacheManager;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.core.common.Session;
import org.yangyuan.security.core.common.Subject;

/**
 * 安全认证工具类
 * @author yangyuan
 * @date 2017年4月26日
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SecurityUtils {
    /**
     * 获取当前上下文中的主题
     * @return 如果用户未登录，返回null
     */
    public static Subject getSubject(){
        Subject subject = SessionManager.getSubject();
        
        /**
         * 未登录
         */
        if(subject == null){
            return null;
        }
        
        /**
         * 无效登录
         */
        if(!subject.isValid()){
            return null;
        }
        
        /**
         * 返回主题
         */
        return subject;
    }
    
    /**
     * 获取当前上下文中的会话
     * @return 如果用户未登录，返回null
     */
    public static Session<String, Object> getSession(){
        Subject<String, Object> subject = SecurityUtils.getSubject();
        
        if(subject == null){
            return null;
        }
        
        return subject.getSession();
    }
    
    /**
     * 获取当前上下文中关联的用户全局唯一id
     * @return 如果用户未登录，返回null
     */
    public static String  getUnionid(){
        Session<String, Object> session = SecurityUtils.getSession();
        
        if(session == null){
            return null;
        }
        
        return (String) session.get(DefaultSession.SESSION_UNIONID);
    }
    
    /**
     * 设置当前上下文中关联的用户全局唯一id
     * <br>
     * <b>注意：此方法只应该在登陆时调用，其它任何情况都不要修改subject关联的unionid!</b>
     * @param unionid 用户全局唯一id
     */
    protected static void  setUnionid(String unionid){
        Session<String, Object> session = SecurityUtils.getSession();
        
        if(session == null){
            return;
        }
        
        session.set(DefaultSession.SESSION_UNIONID, unionid);
    }
    
    /**
     * 获取当前上下文中关联的用户角色列表
     * @return 如果用户未登录，返回null
     */
    public static List<Role> getRoles(){
        Session<String, Object> session = SecurityUtils.getSession();
        
        if(session == null){
            return null;
        }
        
        return (List<Role>) session.get(DefaultSession.SESSION_ROLES);
    }
    
    /**
     * 设置当前上下文中关联的用户角色列表
     * @param roles 用户角色列表
     */
    public static void setRoles(List<Role> roles){
        Session<String, Object> session = SecurityUtils.getSession();
        
        if(session == null){
            return;
        }
        
        session.set(DefaultSession.SESSION_ROLES, roles);
        ResourceManager.dao().getRedisSessionDao().doCreate(getSubject());  //保存到redis中
        CacheManager<String, Object> cacheManager = ResourceManager.core().getCacheManager();
        cacheManager.invalid((DefaultSubject) getSubject());  //强制缓存失效
    }
    
    /**
     * 设置用户上下文中关联的角色列表
     * @param roles 用户角色列表
     * @param userUnionid 用户唯一标识
     */
    public static void setRoles(List<Role> roles, String userUnionid){
        List<Subject<String, Object>> subjects = ResourceManager.dao().getRedisSessionDao().queryUserSubjects(userUnionid);
        for(Subject<String, Object> subject : subjects){
            subject.getSession().set(DefaultSession.SESSION_ROLES, roles);
            ResourceManager.dao().getRedisSessionDao().doCreate(subject);
            CacheManager<String, Object> cacheManager = ResourceManager.core().getCacheManager();
            cacheManager.invalid(subject);
        }
    }
    
    /**
     * 登陆
     * @param token 认证令牌
     * @param response http响应对象
     */
    public static void login(SecurityToken token, HttpServletResponse response){
        ResourceManager.core().getSecurityManager().login(token, response);
    }
    
    /**
     * 登出
     * @param response http响应对象
     */
    public static void logout(HttpServletResponse response){
        ResourceManager.core().getSecurityManager().logout(response);
    }
    
    /**
     * 失效当前上下文中的缓存(分布式实现)
     */
    public static void invalidCache(){
        CacheManager<String, Object> cacheManager = ResourceManager.core().getCacheManager();
        cacheManager.invalid((DefaultSubject) getSubject());
    }
}

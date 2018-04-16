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
import org.yangyuan.security.dao.common.CacheSessionDao;
import org.yangyuan.security.dao.common.StatisticalSessionDao;

/**
 * 安全认证工具类
 * @author yangyuan
 * @date 2017年4月26日
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SecurityUtils {
    
    /**
     * 判断用户是否为匿名状态
     * 
     * @return 
     *      <b>true</b> 匿名状态
     *      <br>
     *      <b>false</b> 登陆状态
     */
    public static boolean isAnonymous(){
        return !isAuthenticated();
    }
    
    /**
     * 判断用户是否为登陆状态
     * 
     * @return 
     *      <b>true</b> 已登陆
     *      <br>
     *      <b>false</b> 未登陆
     */
    public static boolean isAuthenticated(){
        return getUnionid() != null;
    }
    
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
    public static String getUnionid(){
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
     * 判断当前上下文中关联的用户是否拥有指定的角色
     * @param roles 指定角色，可以为一个或多个，多个角色之间为逻辑与(and)关系
     * @return
     *      <b>true</b> 用户拥有所有指定的角色
     *      <br>
     *      <b>false</b> 其他情况
     */
    public static boolean hasRoles(Role... roles){
        List<Role> hasRoles = getRoles();
        if(hasRoles == null || hasRoles.size() == 0){
            return false;
        }
        boolean match = false;
        for(Role role : roles){
            for(Role hasRole : hasRoles){
                match = role.matches(hasRole);
                if(match){
                    break;
                }
            }
            if(!match){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 判断当前上下文中关联的用户是否拥有指定的角色
     * @param roles 指定角色，可以为一个或多个，多个角色之间为逻辑与(and)关系
     * @return
     *      <b>true</b> 用户拥有所有指定的角色
     *      <br>
     *      <b>false</b> 其他情况
     */
    public static boolean hasRoles(List<Role> roles){
        if(roles == null || roles.size() == 0){
            return false;
        }
        
        Role[] _roles = new Role[roles.size()];
        for(int i = 0; i < _roles.length; i++){
            _roles[i] = roles.get(i);
        }
        
        return hasRoles(_roles);
    }
    
    /**
     * 判断当前上下文中关联的用户是否拥有指定的角色
     * @param roles 指定角色，可以为一个或多个，多个角色之间为逻辑或(or)关系
     * @return
     *      <b>true</b> 用户至少拥有一个指定的角色
     *      <br>
     *      <b>false</b> 其他情况
     */
    public static boolean hasAnyRole(Role... roles){
        List<Role> hasRoles = getRoles();
        if(hasRoles == null || hasRoles.size() == 0){
            return false;
        }
        for(Role role : roles){
            for(Role hasRole : hasRoles){
                if(role.matches(hasRole)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * 判断当前上下文中关联的用户是否拥有指定的角色
     * @param roles 指定角色，可以为一个或多个，多个角色之间为逻辑或(or)关系
     * @return
     *      <b>true</b> 用户至少拥有一个指定的角色
     *      <br>
     *      <b>false</b> 其他情况
     */
    public static boolean hasAnyRole(List<Role> roles){
        if(roles == null || roles.size() == 0){
            return false;
        }
        
        Role[] _roles = new Role[roles.size()];
        for(int i = 0; i < _roles.length; i++){
            _roles[i] = roles.get(i);
        }
        
        return hasAnyRole(_roles);
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
     * 登出(当前上下文)
     * @param response http响应对象
     */
    public static void logout(HttpServletResponse response){
        ResourceManager.core().getSecurityManager().logout(response);
    }
    
    /**
     * 登出(指定主题)
     * @param response http响应对象
     * @param subject 主题
     */
    public static void logout(HttpServletResponse response, Subject<String, Object> subject){
        ResourceManager.core().getSecurityManager().logout(response, subject);
    }
    
    /**
     * 登出(指定用户)
     * @param userUnionid 用户唯一标识
     */
    public static void logout(String userUnionid){
        List<Subject<String, Object>> subjects = ResourceManager.dao().getRedisSessionDao().queryUserSubjects(userUnionid);
        for(Subject<String, Object> subject : subjects){
            ResourceManager.core().getSecurityManager().logout(null, subject);
        }
    }
    
    /**
     * 失效当前上下文中的缓存(分布式实现)
     */
    public static void invalidCache(){
        CacheManager<String, Object> cacheManager = ResourceManager.core().getCacheManager();
        cacheManager.invalid((DefaultSubject) getSubject());
    }
    
    /**
     * 获取在线人数
     * @return 在线人数
     */
    public static long getStatisticalNumberOfOnline(){
        CacheSessionDao<String, Object> redisSessionDao = ResourceManager.dao().getRedisSessionDao();
        if(redisSessionDao instanceof StatisticalSessionDao){
            return ((StatisticalSessionDao) redisSessionDao).numberOfOnline();
        }
        
        return 0l;
    }
    
    /**
     * 获取活跃人数
     * @return 活跃人数
     */
    public static long getStatisticalNumberOfActivity(){
        CacheSessionDao<String, Object> redisSessionDao = ResourceManager.dao().getRedisSessionDao();
        if(redisSessionDao instanceof StatisticalSessionDao){
            return ((StatisticalSessionDao) redisSessionDao).numberOfActivity();
        }
        
        return 0l;
    }
}

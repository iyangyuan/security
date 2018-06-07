package org.yangyuan.security.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.yangyuan.security.bean.User;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.CacheManager;
import org.yangyuan.security.core.common.SecurityManager;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.core.common.Subject;
import org.yangyuan.security.exception.SecurityFilterAuthException;
import org.yangyuan.security.exception.SecurityFilterBasicAuthException;
import org.yangyuan.security.exception.SecurityFilterForbiddenException;
import org.yangyuan.security.exception.common.AuthenticationException;
import org.yangyuan.security.filter.BasicHttpAuthenticationSecurityFilter;
import org.yangyuan.security.util.SecurityUtils;

/**
 * 默认安全管理器实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class DefaultSecurityManager implements SecurityManager{

    @Override
    public User login(SecurityToken token, HttpServletResponse response) throws AuthenticationException {
        
        /**
         * 识别登录方式
         */
        User user = null;
        if(token instanceof UsernamePasswordToken){  //本地账号密码登录
            user = ResourceManager.dao().getJdbcSessionDao().auth(token);
        }
        if(token instanceof RemoteToken){  //第三方登录
            user = ResourceManager.dao().getRemoteSessionDao().auth(token);
        }
        
        /**
         * 并发主题控制
         */
        ResourceManager.core().getConcurrentSubjectControl().control(user);
        
        /**
         * 创建subject
         */
        DefaultSubject clientSubject = SecurityUtils.getSubject();
        DefaultSubject subject = null;
        if(ResourceManager.core().isUseClientSubjectLogin() && clientSubject != null){
            subject = DefaultSubject.valid(clientSubject);
        }
        if(subject == null){
            subject = DefaultSubject.getInstance(ResourceManager.core().getPrincipalFactory().newPrincipal(user.getUnionid()), true, null);
        }
        
        /**
         * subject关联用户
         */
        DefaultSession session = (DefaultSession) subject.getSession();
        session.set(DefaultSession.SESSION_UNIONID, user.getUnionid());
        session.set(DefaultSession.SESSION_ROLES, user.getRoles());
        session.set(DefaultSession.SESSION_REMEMBER, token.isRemember());
        ResourceManager.dao().getRedisSessionDao().doCreate(subject);  //持久化到redis
        ResourceManager.dao().getEhcacheSessionDao().doCreate(subject);  //缓存
        SessionManager.setSubject(subject);  //写入会话
        
        /**
         * 设置客户端响应cookie
         */
        Cookie cookie;
        if(token.isRemember()){ //记住登陆状态
            cookie = new PrincipalPersistentCookie(subject.getPrincipal()).toHttpCookie();
        }else{  //临时登录
            cookie = new PrincipalSessionCookie(subject.getPrincipal()).toHttpCookie();
        }
        response.addCookie(cookie);
        
        return user;
    }

    @Override
    public void logout(HttpServletResponse response) {
        DefaultSubject subject = SecurityUtils.getSubject();
        logout(response, subject);
    }
    
    @Override
    public void logout(HttpServletResponse response, Subject<String, Object> subject) {
        /**
         * 未登录直接跳过
         */
        if(subject == null){
            return;
        }
        
        /**
         * 登录无效直接跳过
         */
        if(!subject.isValid()){
            return;
        }
        
        /**
         * 服务端数据移除
         */
        ResourceManager.dao().getRedisSessionDao().doDelete(subject);
        CacheManager cacheManager = ResourceManager.core().getCacheManager();
        cacheManager.invalid(subject);
        
        /**
         * 客户端cookie移除
         */
        if(response != null){
            PrincipalInvalidCookie cookie = new PrincipalInvalidCookie();
            response.addCookie(cookie.toHttpCookie());
        }
    }
    
    @Override
    public boolean auth(String permission, HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(StringUtils.isBlank(permission)){
            return true;
        }
        
        try {
            SecurityFilterManager.doFilter(permission, request);
        } catch (SecurityFilterAuthException e) {
            ResourceManager.core()
                            .getSecurityAuthHandler()
                            .onAuthFail(request, response, (HandlerMethod) handler);
            return false;
        } catch (SecurityFilterForbiddenException e) {
            ResourceManager.core()
                            .getSecurityAuthHandler()
                            .onForbiddenFail(request, response, (HandlerMethod) handler);
            return false;
        } catch (SecurityFilterBasicAuthException e) {
            BasicHttpAuthenticationSecurityFilter.sendChallenge(response);
            return false;
        }
        
        ResourceManager.core()
                        .getSecurityAuthHandler()
                        .onSuccess(request, response, (HandlerMethod) handler);
        return true;
    }
    
}

package org.yangyuan.security.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.SessionManager;
import org.yangyuan.security.core.annotation.Security;
import org.yangyuan.security.exception.SecurityFilterAuthException;
import org.yangyuan.security.exception.SecurityFilterBasicAuthException;
import org.yangyuan.security.exception.SecurityFilterForbiddenException;
import org.yangyuan.security.filter.BasicHttpAuthenticationSecurityFilter;

/**
 * spring mvc 拦截器，实现认证拦截
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        /**
         * 非controller请求直接放过
         */
        if(!handler.getClass().isAssignableFrom(HandlerMethod.class)){
            return true;
        }
        
        /**
         * 用户识别
         */
        DefaultSubject subjectClient = DefaultSubject.of(request);
        SessionManager.setSubject(subjectClient);  //保持客户端principal
        if(subjectClient != null){
            DefaultSubject subjectServer = (DefaultSubject) ResourceManager.dao().getEhcacheSessionDao().doRead(subjectClient);
            if(subjectServer != null){
                SessionManager.setSubject(subjectServer);
            }else{
                subjectServer = (DefaultSubject) ResourceManager.dao().getRedisSessionDao().doRead(subjectClient);
                if(subjectServer != null){
                    SessionManager.setSubject(subjectServer);
                    ResourceManager.dao().getEhcacheSessionDao().doCreate(subjectServer);  //缓存
                }
            }
        }
        
        /**
         * 用户认证
         */
        Security securityAnnotation = ((HandlerMethod) handler).getMethodAnnotation(Security.class);
        if(securityAnnotation == null){
            return true;
        }
        String permission = securityAnnotation.permission();
        try {
            ResourceManager.core().getSecurityManager().auth(permission, request);
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

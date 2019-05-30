package org.yangyuan.security.servlet;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.SessionManager;
import org.yangyuan.security.core.annotation.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
         * 获取注解
         */
        Security securityAnnotation = ((HandlerMethod) handler).getMethodAnnotation(Security.class);
        String permission = null;
        if(securityAnnotation != null){
            permission = securityAnnotation.permission();
        }
        
        /**
         * 用户认证
         */
        return ResourceManager.core().getSecurityManager().auth(permission, request, response, handler);
    }
    
}

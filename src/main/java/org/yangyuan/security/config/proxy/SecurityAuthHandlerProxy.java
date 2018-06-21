package org.yangyuan.security.config.proxy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.yangyuan.security.core.common.SecurityAuthHandler;

/**
 * 认证回调代理
 * @author yangyuan
 * @date 2018年6月20日
 */
public class SecurityAuthHandlerProxy implements SecurityAuthHandler{
    
    private final SecurityAuthHandler securityAuthHandler;
    
    public SecurityAuthHandlerProxy(SecurityAuthHandler securityAuthHandler){
        this.securityAuthHandler = securityAuthHandler;
    }
    
    private void checkSecurityAuthHandler(){
        if(securityAuthHandler == null){
            throw new SecurityException("Load security.properties[core.securityAuthHandler] failed!");
        }
    }
    
    @Override
    public void onSuccess(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        checkSecurityAuthHandler();
        securityAuthHandler.onSuccess(request, response, handler);
    }

    @Override
    public void onAuthFail(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        checkSecurityAuthHandler();
        securityAuthHandler.onAuthFail(request, response, handler);
    }

    @Override
    public void onForbiddenFail(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        checkSecurityAuthHandler();
        securityAuthHandler.onForbiddenFail(request, response, handler);
    }

}

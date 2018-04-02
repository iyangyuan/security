package org.yangyuan.security.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

/**
 * 认证回调定义
 * @author yangyuan
 * @date 2018年4月2日
 */
public interface SecurityAuthHandler {
    
    /**
     * 认证成功
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation 
     */
    void onSuccess(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);
    
    /**
     * 基础认证失败(未登录)
     * @param request request current HTTP request
     * @param response response current HTTP response
     * @param handler handler chosen handler to execute, for type and/or instance evaluation
     */
    void onAuthFail(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);
    
    /**
     * 角色认证失败(无权限)
     * @param request request current HTTP request
     * @param response response current HTTP response
     * @param handler handler chosen handler to execute, for type and/or instance evaluation
     */
    void onForbiddenFail(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);
    
}

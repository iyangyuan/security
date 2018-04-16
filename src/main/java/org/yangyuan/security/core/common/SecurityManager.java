package org.yangyuan.security.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yangyuan.security.bean.User;

/**
 * 安全管理器定义
 * @author yangyuan
 * @date 2017年4月26日
 */
public interface SecurityManager {
    /**
     * 登录
     * <p>如果正常执行，说明登录成功；登录失败会抛出对应的运行时异常</p>
     * @param token 登录令牌
     * @param response http响应对象
     * @return 登录成功后返回用户全局唯一id(unionid)
     */
    User login(SecurityToken token, HttpServletResponse response);
    
    /**
     * 登出
     * @param response http响应对象
     */
    void logout(HttpServletResponse response);
    
    /**
     * 统一认证
     * <p>此方法是实现@Security注解的入口</p>
     * @param permission 认证表达式
     * @param request http请求对象
     * @param response http响应对象
     * @param handler handler chosen handler to execute, for type and/or instance evaluation
     * @return
     *        <b>true</b> 认证成功
     *        <br>
     *        <b>false</b> 认证失败
     */
    boolean auth(String permission, HttpServletRequest request, HttpServletResponse response, Object handler);
}

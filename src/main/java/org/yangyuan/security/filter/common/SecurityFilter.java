package org.yangyuan.security.filter.common;

import javax.servlet.http.HttpServletRequest;

import org.yangyuan.security.exception.FilterException;

/**
 * 安全过滤器定义
 * @author yangyuan
 * @date 2017年4月26日
 */
public interface SecurityFilter {
    
    /**
     * 认证试探
     * @param permission 认证表达式
     * @return
     *      <b>true</b> 此过滤器能够处理指定的认证表达式
     *      <br>
     *      <b>false</b> 此过滤器不能够处理指定的认证表达式
     */
    boolean approve(String permission);
    
    /**
     * 认证
     * <p>正常执行，认证通过</p>
     * <p>认证失败会抛出对应的异常，通过异常机制实现认证交互</p>
     * @param permission 认证表达式
     * @param request http请求对象
     * @throws FilterException 安全过滤器相关异常，具体含义参考子类定义
     */
    void doFilter(String permission, HttpServletRequest request) throws FilterException;
    
}

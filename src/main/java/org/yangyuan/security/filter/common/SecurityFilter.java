package org.yangyuan.security.filter.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全过滤器定义
 * @author yangyuan
 * @date 2017年4月26日
 */
public interface SecurityFilter {
    
    /**
     * 获取过滤器链下一个节点
     * @return
     */
    SecurityFilter getNext();
    
    /**
     * 设置过滤器链下一个节点
     * @param filter
     */
    void setNext(SecurityFilter filter);
    
    /**
     * 认证
     * <p>正常执行，认证通过；认证失败会抛出对应的异常</p>
     * @param permission 认证表达式
     * @param request http请求对象
     */
    void doFilter(String permission, HttpServletRequest request);
    
}

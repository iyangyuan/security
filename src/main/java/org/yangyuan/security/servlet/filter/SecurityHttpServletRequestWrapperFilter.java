package org.yangyuan.security.servlet.filter;

import org.yangyuan.security.config.ResourceManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 封装body可多次读取的请求过滤器
 * 用于注入封装后的请求对象
 * @Auther: yangyuan
 * @Date: 2019/5/30 11:02
 */
public class SecurityHttpServletRequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            chain.doFilter(ResourceManager.core().getPrincipalReader().wrap((HttpServletRequest)request), response);
        }else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // do nothing
    }

}

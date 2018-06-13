package org.yangyuan.security.filter;

import javax.servlet.http.HttpServletRequest;

import org.yangyuan.security.bean.Permission;
import org.yangyuan.security.core.annotation.SecurityFilterComponent;
import org.yangyuan.security.exception.common.FilterException;
import org.yangyuan.security.filter.common.SecurityFilter;

/**
 * 匿名认证实现
 * @author yangyuan
 * @date 2017年4月26日
 */
@SecurityFilterComponent(value = "index/1")
public class AnonSecurityFilter implements SecurityFilter{
    
    private static final String FILETER_NAME = "anon";
    
    @Override
    public boolean approve(Permission permission) {
        return permission.getName().toLowerCase().equals(FILETER_NAME);
    }
    
    @Override
    public void doFilter(Permission permission, HttpServletRequest request) throws FilterException{
        return;
    }
    
}

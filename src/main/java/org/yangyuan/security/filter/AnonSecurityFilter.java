package org.yangyuan.security.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.core.annotation.SecurityFilterComponent;
import org.yangyuan.security.exception.FilterException;
import org.yangyuan.security.exception.SecurityFilterErrorException;
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
    public boolean approve(String permission) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        return permission.toLowerCase().equals(FILETER_NAME);
    }
    
    @Override
    public void doFilter(String permission, HttpServletRequest request) throws FilterException{
        return;
    }
    
}

package org.yangyuan.security.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.annotation.SecurityFilterComponent;
import org.yangyuan.security.exception.FilterException;
import org.yangyuan.security.exception.SecurityFilterAuthException;
import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.filter.common.SecurityFilter;
import org.yangyuan.security.util.SecurityUtils;

/**
 * 基础认证实现
 * @author yangyuan
 * @date 2017年4月26日
 */
@SecurityFilterComponent(value = "index/2")
public class AuthcSecurityFilter implements SecurityFilter{
    
    private static final String FILETER_NAME = "authc";
    
    @Override
    public boolean approve(String permission) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        return permission.toLowerCase().equals(FILETER_NAME);
    }
    
    @Override
    public void doFilter(String permission, HttpServletRequest request) throws FilterException{
        DefaultSubject subject = SecurityUtils.getSubject();
        
        /**
         * 未登录
         */
        if(subject == null){
            throw new SecurityFilterAuthException();
        }
        
        /**
         * 登录无效
         */
        if(!subject.isValid()){
            throw new SecurityFilterAuthException();
        }
        
        return;
    }

}

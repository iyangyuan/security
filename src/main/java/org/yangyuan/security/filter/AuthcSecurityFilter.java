package org.yangyuan.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.SessionManager;
import org.yangyuan.security.exception.SecurityFilterAuthException;
import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.filter.common.AbstractSecurityFilter;

/**
 * 基础认证实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class AuthcSecurityFilter extends AbstractSecurityFilter{

    private static final String FILETER_NAME = "authc";
    
    @Override
    public void doFilter(String permission) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        if(permission.toLowerCase().equals(FILETER_NAME)){
            DefaultSubject subject = (DefaultSubject) SessionManager.getSubject();
            
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
        
        next(permission);
    }

}

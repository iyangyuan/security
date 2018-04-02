package org.yangyuan.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.filter.common.AbstractSecurityFilter;

/**
 * 匿名认证实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class AnonSecurityFilter extends AbstractSecurityFilter{
    
    private static final String FILETER_NAME = "anon";
    
    @Override
    public void doFilter(String permission) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        if(permission.toLowerCase().equals(FILETER_NAME)){
            return;
        }
        
        next(permission);
    }
    
}

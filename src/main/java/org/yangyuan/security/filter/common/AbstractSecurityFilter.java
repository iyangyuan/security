package org.yangyuan.security.filter.common;

import org.yangyuan.security.exception.SecurityFilterErrorException;

/**
 * 认证过滤器抽象实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public abstract class AbstractSecurityFilter implements SecurityFilter{
    
    private SecurityFilter filter;
    
    @Override
    public SecurityFilter getNext() {
        return filter;
    }

    @Override
    public void setNext(SecurityFilter filter) {
        this.filter = filter;
    }
    
    public void next(String permission) {
        if(getNext() != null){
            getNext().doFilter(permission);
            return;
        }
        
        throw new SecurityFilterErrorException("permission express: " + permission + ", none security filter can execute");
    }
    
}

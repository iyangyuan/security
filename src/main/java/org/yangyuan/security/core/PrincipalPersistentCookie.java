package org.yangyuan.security.core;

import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.AbstractCookie;

/**
 * 持久的主cookie实现
 * @author yangyuan
 * @date 2018年4月3日
 */
public class PrincipalPersistentCookie extends AbstractCookie{
    private final String principal;
    
    public PrincipalPersistentCookie(String principal){
        this.principal = principal;
    }
    
    @Override
    protected String getName() {
        return ResourceManager.cookie().getName();
    }

    @Override
    protected String getValue() {
        return this.principal;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}

package org.yangyuan.security.core;

import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.AbstractCookie;

/**
 * 基于会话的主cookie实现
 * @author yangyuan
 * @date 2018年3月5日
 */
public class PrincipalSessionCookie extends AbstractCookie{
    
    private final String principal;
    
    public PrincipalSessionCookie(String principal){
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
    protected int getMaxAge() {
        return -1;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}

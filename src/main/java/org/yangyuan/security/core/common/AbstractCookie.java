package org.yangyuan.security.core.common;

import org.yangyuan.security.config.ResourceManager;

/**
 * security cookie抽象实现
 * @author yangyuan
 * @date 2018年3月5日
 */
public abstract class AbstractCookie implements Cookie{
    
    /**
     * 获取cookie生命周期
     * @return an integer specifying the maximum age of the cookie in seconds; if negative, means the cookie is not stored; if zero, deletes the cookie
     */
    protected int getMaxAge(){
        return ResourceManager.cookie().getMaxAge();
    }
    
    protected abstract String getName();
    protected abstract String getValue();
    
    protected String getDomain(){
        return ResourceManager.cookie().getDomain();
    }
    
    protected String getPath(){
        return ResourceManager.cookie().getPath();
    }
    
    protected boolean getHttpOnly(){
        return ResourceManager.cookie().isHttpOnly();
    }
    
    protected boolean getSecure(){
        return ResourceManager.cookie().isSecure();
    }
    
    /**
     * 构造Http Cookie
     * @return
     */
    private javax.servlet.http.Cookie buildHttpCookie(){
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(getName(), getValue());
        cookie.setDomain(getDomain());
        cookie.setPath(getPath());
        cookie.setHttpOnly(getHttpOnly());
        cookie.setSecure(getSecure());
        cookie.setMaxAge(getMaxAge());
        
        return cookie;
    }
    
    @Override
    public javax.servlet.http.Cookie toHttpCookie() {
        return buildHttpCookie();
    }
}

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[name](");
        builder.append(getName());
        builder.append(")\n");
        
        builder.append("[value](");
        builder.append(getValue());
        builder.append(")\n");
        
        builder.append("[domain](");
        builder.append(getDomain());
        builder.append(")\n");
        
        builder.append("[path](");
        builder.append(getPath());
        builder.append(")\n");
        
        builder.append("[httpOnly](");
        builder.append(getHttpOnly());
        builder.append(")\n");
        
        builder.append("[maxAge](");
        builder.append(getMaxAge());
        builder.append(")\n");
        
        builder.append("[secure](");
        builder.append(getSecure());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

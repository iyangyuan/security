package org.yangyuan.security.core;

import org.yangyuan.security.core.common.SecurityToken;

/**
 * 本地用户名、密码模式令牌实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class UsernamePasswordToken implements SecurityToken{
    /**
     * 用户名
     */
    private final String username;
    /**
     * 密码
     */
    private final String passwrod;
    /**
     * 记住我
     */
    private final boolean remember;
    
    public UsernamePasswordToken(String username, String passwrod){
        this.username = username;
        this.passwrod = passwrod;
        this.remember = true;
    }
    
    public UsernamePasswordToken(String username, String passwrod, boolean remember){
        this.username = username;
        this.passwrod = passwrod;
        this.remember = remember;
    }
    
    public String getUsername() {
        return username;
    }
    public String getPasswrod() {
        return passwrod;
    }
    public boolean isRemember() {
        return remember;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[username](");
        builder.append(getUsername());
        builder.append(")\n");
        
        builder.append("[passwrod](");
        builder.append(getPasswrod());
        builder.append(")\n");
        
        builder.append("[remember](");
        builder.append(isRemember());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

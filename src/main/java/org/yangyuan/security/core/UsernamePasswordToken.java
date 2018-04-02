package org.yangyuan.security.core;

import org.yangyuan.security.core.common.SecurityToken;

/**
 * 本地用户名、密码模式令牌实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class UsernamePasswordToken implements SecurityToken{
    private final String username;
    private final String passwrod;
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
}

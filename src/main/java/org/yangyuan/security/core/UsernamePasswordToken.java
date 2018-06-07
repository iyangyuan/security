package org.yangyuan.security.core;

import org.yangyuan.security.core.common.AbstractSecurityToken;

/**
 * 本地用户名、密码模式令牌实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class UsernamePasswordToken extends AbstractSecurityToken {
    /**
     * 用户名
     */
    private final String username;
    /**
     * 密码
     */
    private final String passwrod;
    
    /**
     * remember固定为true的构造方法
     * @param username 用户名
     * @param passwrod 密码
     */
    public UsernamePasswordToken(String username, String passwrod){
        super(true);
        this.username = username;
        this.passwrod = passwrod;
    }
    
    /**
     * 自定义remember的构造方法
     * @param username 用户名
     * @param passwrod 密码
     * @param remember 记住我
     */
    public UsernamePasswordToken(String username, String passwrod, boolean remember){
        super(remember);
        this.username = username;
        this.passwrod = passwrod;
    }
    
    public String getUsername() {
        return username;
    }
    public String getPasswrod() {
        return passwrod;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append(super.toString());
        
        builder.append("[username](");
        builder.append(getUsername());
        builder.append(")\n");
        
        builder.append("[passwrod](");
        builder.append(getPasswrod());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

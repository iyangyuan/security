package org.yangyuan.security.core;

import org.yangyuan.security.core.common.AbstractSecurityToken;

/**
 * 本地登陆令牌
 * @author yangyuan
 * @date 2018年7月4日
 */
public abstract class JdbcToken extends AbstractSecurityToken{
    /**
     * 用户名
     */
    private final String username;
    
    public JdbcToken(String username, boolean remember) {
        super(remember);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append(super.toString());
        
        builder.append("[username](");
        builder.append(getUsername());
        builder.append(")\n");
        
        return new String(builder);
    }
}

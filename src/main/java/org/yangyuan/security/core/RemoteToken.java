package org.yangyuan.security.core;

import org.yangyuan.security.core.common.AbstractSecurityToken;

/**
 * 第三方登录令牌
 * @author yangyuan
 * @date 2017年4月26日
 */
public abstract class RemoteToken extends AbstractSecurityToken {
    /**
     * 第三方令牌
     */
    private final String accessToken;
    
    /**
     * 构造方法
     * @param accessToken 第三方令牌
     * @param remember 记住我
     */
    public RemoteToken(String accessToken, boolean remember) {
        super(remember);
        this.accessToken = accessToken;
    }
    
    /**
     * 第三方平台代码
     * <br>
     * 1 qq， 2 微信， 3 微博
     * 
     * @return
     */
    public abstract int getPlanform();
    
    public String getAccessToken() {
        return accessToken;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append(super.toString());
        
        builder.append("[accessToken](");
        builder.append(getAccessToken());
        builder.append(")\n");
        
        builder.append("[planform](");
        builder.append(getPlanform());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

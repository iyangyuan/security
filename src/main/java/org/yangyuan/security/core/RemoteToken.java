package org.yangyuan.security.core;

import org.yangyuan.security.core.common.SecurityToken;

/**
 * 第三方登录令牌
 * @author yangyuan
 * @date 2017年4月26日
 */
public abstract class RemoteToken implements SecurityToken {
    
    public RemoteToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    /**
     * 第三方令牌
     */
    private String accessToken;
    
    /**
     * 第三方平台代码
     * 
     * 1 qq， 2 微信， 3 微博
     * 
     * @return
     */
    public abstract int getPlanform();
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    
}

package org.yangyuan.security.core;

import org.yangyuan.security.core.common.SecurityToken;

/**
 * 第三方登录令牌
 * @author yangyuan
 * @date 2017年4月26日
 */
public class RemoteToken implements SecurityToken {
    
    public RemoteToken(String accessToken, int planform) {
        this.accessToken = accessToken;
        this.planform = planform;
    }
    
    public RemoteToken(String accessToken, int planform, String openid) {
        this.accessToken = accessToken;
        this.planform = planform;
        this.openid = openid;
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
     */
    private int planform;
    
    /**
     * 授权用户唯一标识(微信授权用)
     */
    private String openid;
    
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getPlanform() {
        return planform;
    }

    public void setPlanform(int planform) {
        this.planform = planform;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
}

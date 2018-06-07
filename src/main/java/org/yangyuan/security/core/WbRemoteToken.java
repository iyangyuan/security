package org.yangyuan.security.core;

/**
 * 微博第三方登录令牌
 * @author yangyuan
 * @date 2018年4月3日
 */
public class WbRemoteToken extends RemoteToken{
    
    /**
     * remember固定为true的构造方法
     * @param accessToken 第三方令牌
     */
    public WbRemoteToken(String accessToken) {
        super(accessToken, true);
    }
    
    /**
     * 自定义remember的构造方法
     * @param accessToken 第三方令牌
     * @param remember 记住我
     */
    public WbRemoteToken(String accessToken, boolean remember) {
        super(accessToken, remember);
    }
    
    @Override
    public int getPlanform() {
        return 3;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}

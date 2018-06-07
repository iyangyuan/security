package org.yangyuan.security.core;

/**
 * 微信第三方登录令牌
 * @author yangyuan
 * @date 2018年4月3日
 */
public class WxRemoteToken extends RemoteToken{
    /**
     * 授权用户唯一标识
     */
    private final String openid;
    
    /**
     * remember固定为true的构造方法
     * @param accessToken 第三方令牌
     * @param openid 授权用户唯一标识
     */
    public WxRemoteToken(String accessToken, String openid) {
        super(accessToken, true);
        this.openid = openid;
    }
    
    /**
     * 自定义remember的构造方法
     * @param accessToken 第三方令牌
     * @param openid 授权用户唯一标识
     * @param remember 记住我
     */
    public WxRemoteToken(String accessToken, String openid, boolean remember) {
        super(accessToken, remember);
        this.openid = openid;
    }
    
    public String getOpenid() {
        return openid;
    }
    
    @Override
    public int getPlanform() {
        return 2;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append(super.toString());
        
        builder.append("[openid](");
        builder.append(getOpenid());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

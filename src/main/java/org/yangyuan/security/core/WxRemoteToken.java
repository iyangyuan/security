package org.yangyuan.security.core;

/**
 * 微信第三方登录令牌
 * @author yangyuan
 * @date 2018年4月3日
 */
public class WxRemoteToken extends RemoteToken{
    
    public WxRemoteToken(String accessToken, String openid) {
        super(accessToken);
        this.openid = openid;
    }
    
    /**
     * 授权用户唯一标识
     */
    private String openid;
    
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

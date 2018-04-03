package org.yangyuan.security.core;

/**
 * 微博第三方登录令牌
 * @author yangyuan
 * @date 2018年4月3日
 */
public class WbRemoteToken extends RemoteToken{
    public WbRemoteToken(String accessToken) {
        super(accessToken);
    }

    @Override
    public int getPlanform() {
        return 3;
    }

}

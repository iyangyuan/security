package org.yangyuan.security.realm.bean;

/**
 * 第三方授权用户数据
 * @author yangyuan
 * @date 2018年5月31日
 */
public class RemoteUser {
    
    /**
     * 构造方法
     * @param nickname 用户昵称
     * @param portrait 用户头像地址
     * @param openid 第三方授权唯一id
     */
    public RemoteUser(String nickname, String portrait, String openid){
        this.nickname = nickname;
        this.portrait = portrait;
        this.openid = openid;
    }
    
    /**
     * 用户昵称
     */
    private final String nickname;
    
    /**
     * 用户头像地址
     */
    private final String portrait;
    
    /**
     * 第三方授权唯一id
     */
    private final String openid;
    
    public String getNickname() {
        return nickname;
    }
    
    public String getPortrait() {
        return portrait;
    }
    
    public String getOpenid() {
        return openid;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[nickname](");
        builder.append(getNickname());
        builder.append(")\n");
        
        builder.append("[portrait](");
        builder.append(getPortrait());
        builder.append(")\n");
        
        builder.append("[openid](");
        builder.append(getOpenid());
        builder.append(")\n");
        
        return new String(builder);
    }
}

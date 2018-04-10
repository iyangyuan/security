package org.yangyuan.security.realm.bean;

/**
 * 用户信息适配器
 * <br>
 * 完成数据库系统用户到安全认证系统用户的转换
 * @author yangyuan
 * @date 2018年3月14日
 */
public class UserAdaptor {
    
    /**
     * 用户密码
     */
    private String password;
    
    /**
     * 用户全局唯一id
     */
    private String unionid;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户头像地址
     */
    private String portrait;
    
    /**
     * 用户角色列表，多个以逗号分隔，忽略空格
     */
    private String roles;
    
    /**
     * 第三方授权唯一id
     */
    private String openid;
    
    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[password](");
        builder.append(getPassword());
        builder.append(")\n");
        
        builder.append("[unionid](");
        builder.append(getUnionid());
        builder.append(")\n");
        
        builder.append("[nickname](");
        builder.append(getNickname());
        builder.append(")\n");
        
        builder.append("[portrait](");
        builder.append(getPortrait());
        builder.append(")\n");
        
        builder.append("[roles](");
        builder.append(getRoles());
        builder.append(")\n");
        
        builder.append("[openid](");
        builder.append(getOpenid());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

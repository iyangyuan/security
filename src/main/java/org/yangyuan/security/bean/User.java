package org.yangyuan.security.bean;

import java.util.List;

/**
 * 用户模型
 * @author yangyuan
 * @date 2017年4月26日
 */
public class User {
    
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
     * 用户角色列表
     */
    List<Role> roles;
    
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
}

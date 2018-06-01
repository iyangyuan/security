package org.yangyuan.security.bean;

import java.util.List;

/**
 * 用户模型
 * @author yangyuan
 * @date 2017年4月26日
 */
public class User {
    
    /**
     * 构造方法
     * @param unionid 用户全局唯一id
     * @param roles 用户角色列表
     */
    public User(String unionid, List<Role> roles){
        this.unionid = unionid;
        this.roles = roles;
    }
    
    /**
     * 用户全局唯一id
     */
    private final String unionid;
    
    /**
     * 用户角色列表
     */
    private final List<Role> roles;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户头像地址
     */
    private String portrait;
    
    public String getUnionid() {
        return unionid;
    }
    
    public List<Role> getRoles() {
        return roles;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(256);
        
        builder.append("{");
        
        builder.append("\"unionid\": ");
        if(this.unionid == null){
            builder.append("null, ");
        }else{
            builder.append("\"");
            builder.append(this.unionid);
            builder.append("\", ");
        }
        
        builder.append("\"nickname\": ");
        if(this.nickname == null){
            builder.append("null, ");
        }else{
            builder.append("\"");
            builder.append(this.nickname);
            builder.append("\", ");
        }
        
        builder.append("\"portrait\": ");
        if(this.portrait == null){
            builder.append("null, ");
        }else{
            builder.append("\"");
            builder.append(this.portrait);
            builder.append("\", ");
        }
        
        builder.append("\"roles\": ");
        if(this.roles == null){
            builder.append("null");
        }else if(this.roles.size() == 0){
            builder.append("[]");
        }else {
            builder.append("[");
            int limit = this.roles.size() - 1;
            for(int i = 0; i < this.roles.size(); i++){
                builder.append("\"");
                builder.append(this.roles.get(i).toString());
                builder.append("\"");
                
                if(i != limit){
                    builder.append(", ");
                }
            }
            builder.append("]");
        }
        
        builder.append("}");
        
        return new String(builder);
    }
    
}

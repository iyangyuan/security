package org.yangyuan.security.realm.bean;

/**
 * 公共用户数据适配器
 * @author yangyuan
 * @date 2018年3月14日
 */
public class UserAdaptor {
    
    /**
     * 构造方法
     * @param unionid 用户全局唯一id
     * @param roles 用户角色列表，多个以逗号分隔，忽略空格
     */
    public UserAdaptor(String unionid, String roles){
        this.unionid = unionid;
        this.roles = roles;
    }
    
    /**
     * 用户全局唯一id
     */
    private final String unionid;
    /**
     * 用户角色列表，多个以逗号分隔，忽略空格
     */
    private final String roles;
    
    public String getUnionid() {
        return unionid;
    }

    public String getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[unionid](");
        builder.append(getUnionid());
        builder.append(")\n");
        
        builder.append("[roles](");
        builder.append(getRoles());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

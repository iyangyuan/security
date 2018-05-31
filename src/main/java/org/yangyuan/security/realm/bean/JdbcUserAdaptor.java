package org.yangyuan.security.realm.bean;

/**
 * 持久化用户数据适配器
 * @author yangyuan
 * @date 2018年5月31日
 */
public class JdbcUserAdaptor extends UserAdaptor{
    
    /**
     * 构造方法
     * @param unionid 用户全局唯一id
     * @param roles 用户角色列表，多个以逗号分隔，忽略空格
     * @param password 登陆账号对应的密码
     */
    public JdbcUserAdaptor(String unionid, String roles, String password){
        super(unionid, roles);
        this.password = password;
    }
    
    /**
     * 登陆账号对应的密码
     */
    private final String password;

    public String getPassword() {
        return password;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append(super.toString());
        
        builder.append("[password](");
        builder.append(getPassword());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

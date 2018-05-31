package org.yangyuan.security.realm.bean;

/**
 * 持久化用户数据
 * @author yangyuan
 * @date 2018年5月31日
 */
public class JdbcUser {
    
    /**
     * 构造方法
     * @param username 登陆账号
     */
    public JdbcUser(String username){
        this.username = username;
    }
    
    /**
     * 登陆账号
     */
    private final String username;
    
    public String getUsername() {
        return username;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[username](");
        builder.append(getUsername());
        builder.append(")\n");
        
        return new String(builder);
    }
}

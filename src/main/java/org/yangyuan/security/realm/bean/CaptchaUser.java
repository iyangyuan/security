package org.yangyuan.security.realm.bean;

/**
 * 验证码用户数据
 * @author yangyuan
 * @date 2018年7月4日
 */
public class CaptchaUser {
    /**
     * 用户名
     */
    private final String username;
    
    /**
     * 验证码
     */
    private final String code;
    
    public CaptchaUser(String username, String code){
        this.username = username;
        this.code = code;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getCode() {
        return code;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[username](");
        builder.append(getUsername());
        builder.append(")\n");
        
        builder.append("[code](");
        builder.append(getCode());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

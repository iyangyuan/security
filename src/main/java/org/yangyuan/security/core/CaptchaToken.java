package org.yangyuan.security.core;

/**
 * 本地验证码模式令牌实现
 * @author yangyuan
 * @date 2018年7月4日
 */
public class CaptchaToken extends JdbcToken{
    /**
     * 验证码
     */
    private final String code;
    
    /**
     * remember固定为true的构造方法
     * @param username 用户名
     * @param code 验证码
     */
    public CaptchaToken(String username, String code) {
        super(username, true);
        this.code = code;
    }
    
    /**
     * 自定义remember的构造方法
     * @param username 用户名
     * @param code 验证码
     * @param remember 记住我
     */
    public CaptchaToken(String username, String code, boolean remember) {
        super(username, remember);
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append(super.toString());
        
        builder.append("[code](");
        builder.append(getCode());
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

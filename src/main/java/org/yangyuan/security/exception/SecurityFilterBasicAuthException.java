package org.yangyuan.security.exception;

/**
 * http basic authentication认证失败异常
 * @author yangyuan
 * @date 2018年4月12日
 */
public class SecurityFilterBasicAuthException extends RuntimeException{
    private static final long serialVersionUID = 8393729605818992365L;
    
    public SecurityFilterBasicAuthException() {
        super();
    }

    public SecurityFilterBasicAuthException(String s) {
        super(s);
    }
    
}

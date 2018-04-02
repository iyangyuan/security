package org.yangyuan.security.exception;

/**
 * 基础认证失败异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterAuthException extends RuntimeException{
    private static final long serialVersionUID = 4317541537006535402L;
    
    public SecurityFilterAuthException() {
        super();
    }

    public SecurityFilterAuthException(String s) {
        super(s);
    }
    
}

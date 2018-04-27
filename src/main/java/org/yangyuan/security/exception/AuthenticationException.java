package org.yangyuan.security.exception;

/**
 * 安全认证相关异常
 * @author yangyuan
 * @date 2018年4月27日
 */
public class AuthenticationException extends RuntimeException{
    private static final long serialVersionUID = -8592640217397866095L;
    
    public AuthenticationException() {
        super();
    }
    public AuthenticationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
    
}

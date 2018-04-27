package org.yangyuan.security.exception;

/**
 * 用户不存在异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class AuthUsernameNotFoundException extends AuthenticationException{
    private static final long serialVersionUID = 4317541537006535403L;
    
    public AuthUsernameNotFoundException() {
        super();
    }

    public AuthUsernameNotFoundException(String s) {
        super(s);
    }

    public AuthUsernameNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthUsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthUsernameNotFoundException(Throwable cause) {
        super(cause);
    }
    
}

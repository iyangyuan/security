package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.AuthenticationException;

/**
 * 密码不正确异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class AuthPasswordWrongException extends AuthenticationException{
    private static final long serialVersionUID = 4317541537006535402L;
    
    public AuthPasswordWrongException() {
        super();
    }

    public AuthPasswordWrongException(String s) {
        super(s);
    }

    public AuthPasswordWrongException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthPasswordWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthPasswordWrongException(Throwable cause) {
        super(cause);
    }
    
}

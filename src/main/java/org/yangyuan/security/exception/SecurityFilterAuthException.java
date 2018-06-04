package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.FilterException;

/**
 * 基础认证失败异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterAuthException extends FilterException{
    private static final long serialVersionUID = 4317541537006535404L;
    
    public SecurityFilterAuthException() {
        super();
    }

    public SecurityFilterAuthException(String s) {
        super(s);
    }

    public SecurityFilterAuthException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SecurityFilterAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityFilterAuthException(Throwable cause) {
        super(cause);
    }
    
}

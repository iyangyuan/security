package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.FilterException;

/**
 * 认证过程出现严重bug，无法继续进行异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterErrorException extends FilterException{
    private static final long serialVersionUID = 4317541537006535405L;
    
    public SecurityFilterErrorException() {
        super();
    }

    public SecurityFilterErrorException(String s) {
        super(s);
    }

    public SecurityFilterErrorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SecurityFilterErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityFilterErrorException(Throwable cause) {
        super(cause);
    }
    
}

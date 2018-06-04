package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.FilterException;

/**
 * 角色认证失败异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterForbiddenException extends FilterException{
    private static final long serialVersionUID = 8393729605818992367L;
    
    public SecurityFilterForbiddenException() {
        super();
    }

    public SecurityFilterForbiddenException(String s) {
        super(s);
    }

    public SecurityFilterForbiddenException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SecurityFilterForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityFilterForbiddenException(Throwable cause) {
        super(cause);
    }
    
}

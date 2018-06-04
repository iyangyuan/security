package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.AuthenticationException;

/**
 * 第三方授权失败异常
 * @author yangyuan
 * @date 2018年3月16日
 */
public class AuthRemoteFailException extends AuthenticationException{
    private static final long serialVersionUID = -2498726338813641428L;
    
    public AuthRemoteFailException() {
        super();
    }
    
    public AuthRemoteFailException(String message) {
        super(message);
    }

    public AuthRemoteFailException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthRemoteFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthRemoteFailException(Throwable cause) {
        super(cause);
    }
}

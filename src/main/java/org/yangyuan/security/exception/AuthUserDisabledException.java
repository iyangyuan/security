package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.AuthenticationBusinessException;

/**
 * 用户不可用异常
 * @author yangyuan
 * @date 2018年6月4日
 */
public class AuthUserDisabledException extends AuthenticationBusinessException{
    private static final long serialVersionUID = -7455615842195744230L;
    
    public AuthUserDisabledException() {
        super();
    }

    public AuthUserDisabledException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthUserDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthUserDisabledException(String message) {
        super(message);
    }

    public AuthUserDisabledException(Throwable cause) {
        super(cause);
    }
    
}

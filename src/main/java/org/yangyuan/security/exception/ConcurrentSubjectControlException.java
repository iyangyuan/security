package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.AuthenticationException;

/**
 * 并发安全认证主题控制异常
 * @author yangyuan
 * @date 2018年4月25日
 */
public class ConcurrentSubjectControlException extends AuthenticationException{
    private static final long serialVersionUID = 6681662640973349551L;

    public ConcurrentSubjectControlException() {
        super();
    }

    public ConcurrentSubjectControlException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConcurrentSubjectControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConcurrentSubjectControlException(String message) {
        super(message);
    }

    public ConcurrentSubjectControlException(Throwable cause) {
        super(cause);
    }

}

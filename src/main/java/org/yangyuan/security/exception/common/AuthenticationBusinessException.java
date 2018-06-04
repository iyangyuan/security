package org.yangyuan.security.exception.common;

/**
 * 业务逻辑相关异常定义
 * <p>此类异常实现业务逻辑与框架之间的交互，完全由框架使用者决定何时抛出</p>
 * <p>使用者根据具体业务逻辑可以判定某些用户不符合认证条件，那么可以借助此异常提前终止认证</p>
 * @author yangyuan
 * @date 2018年6月4日
 */
public class AuthenticationBusinessException extends AuthenticationException{
    private static final long serialVersionUID = -949903305690611971L;
    
    public AuthenticationBusinessException() {
        super();
    }

    public AuthenticationBusinessException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AuthenticationBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationBusinessException(String message) {
        super(message);
    }

    public AuthenticationBusinessException(Throwable cause) {
        super(cause);
    }
    
}

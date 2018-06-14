package org.yangyuan.security.exception.common;

/**
 * 安全认证通用异常
 * <p>此异常一般由底层方法抛出，用来泛指安全框架内部的异常</p>
 * @author yangyuan
 * @date 2018年6月14日
 */
public class SecurityException extends RuntimeException{
    private static final long serialVersionUID = -1375582426321088142L;
    
    public SecurityException() {
        super();
    }
    
    public SecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SecurityException(String message) {
        super(message);
    }
    
    public SecurityException(Throwable cause) {
        super(cause);
    }
    
}

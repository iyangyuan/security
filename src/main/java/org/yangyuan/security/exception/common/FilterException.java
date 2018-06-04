package org.yangyuan.security.exception.common;

/**
 * 安全过滤器异常
 * @author yangyuan
 * @date 2018年4月27日
 */
public class FilterException extends RuntimeException{
    private static final long serialVersionUID = -3599190239223935230L;
    
    public FilterException() {
        super();
    }
    public FilterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }
    public FilterException(String message) {
        super(message);
    }
    public FilterException(Throwable cause) {
        super(cause);
    }
    
}

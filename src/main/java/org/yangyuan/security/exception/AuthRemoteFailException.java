package org.yangyuan.security.exception;

/**
 * 第三方授权失败异常
 * @author yangyuan
 * @date 2018年3月16日
 */
public class AuthRemoteFailException extends RuntimeException{
    private static final long serialVersionUID = -2498726338813641428L;
    
    public AuthRemoteFailException() {
        super();
    }
    
    public AuthRemoteFailException(String message) {
        super(message);
    }
    
}

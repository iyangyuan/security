package org.yangyuan.security.exception;

/**
 * 认证过程出现严重bug，无法继续进行异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterErrorException extends RuntimeException{
    private static final long serialVersionUID = 4317541537006535402L;
    
    public SecurityFilterErrorException() {
        super();
    }

    public SecurityFilterErrorException(String s) {
        super(s);
    }
    
}

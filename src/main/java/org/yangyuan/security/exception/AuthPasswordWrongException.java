package org.yangyuan.security.exception;

/**
 * 密码不正确异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class AuthPasswordWrongException extends RuntimeException{
    private static final long serialVersionUID = 4317541537006535402L;
    
    public AuthPasswordWrongException() {
        super();
    }

    public AuthPasswordWrongException(String s) {
        super(s);
    }
    
}

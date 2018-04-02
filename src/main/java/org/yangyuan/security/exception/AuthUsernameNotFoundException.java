package org.yangyuan.security.exception;

/**
 * 用户不存在异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class AuthUsernameNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 4317541537006535402L;
    
    public AuthUsernameNotFoundException() {
        super();
    }

    public AuthUsernameNotFoundException(String s) {
        super(s);
    }
    
}

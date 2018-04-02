package org.yangyuan.security.exception;

/**
 * 角色认证失败异常
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterForbiddenException extends RuntimeException{
    private static final long serialVersionUID = 8393729605818992365L;
    
    public SecurityFilterForbiddenException() {
        super();
    }

    public SecurityFilterForbiddenException(String s) {
        super(s);
    }
    
}

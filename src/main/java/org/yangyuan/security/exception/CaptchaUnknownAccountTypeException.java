package org.yangyuan.security.exception;

/**
 * 发送验证码时发现未知的账号类型
 * <p>目前仅支持手机号、邮箱</p>
 * @author yangyuan
 * @date 2018年5月4日
 */
public class CaptchaUnknownAccountTypeException extends CaptchaException{
    private static final long serialVersionUID = 4324314748795356440L;
    
    public CaptchaUnknownAccountTypeException() {
        super();
    }

    public CaptchaUnknownAccountTypeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CaptchaUnknownAccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaUnknownAccountTypeException(String message) {
        super(message);
    }

    public CaptchaUnknownAccountTypeException(Throwable cause) {
        super(cause);
    }
    
}

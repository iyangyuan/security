package org.yangyuan.security.exception;

/**
 * 验证码相关异常
 * @author yangyuan
 * @date 2018年5月4日
 */
public class CaptchaException extends RuntimeException{
    private static final long serialVersionUID = 9211333410802654975L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }
}

package org.yangyuan.security.exception;

import org.yangyuan.security.exception.common.CaptchaException;

/**
 * 验证码发送频率过快异常
 * @author yangyuan
 * @date 2018年5月4日
 */
public class CaptchaSendTooFastException extends CaptchaException{
    private static final long serialVersionUID = 2287247809868089731L;

    public CaptchaSendTooFastException() {
        super();
    }

    public CaptchaSendTooFastException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CaptchaSendTooFastException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaSendTooFastException(String message) {
        super(message);
    }

    public CaptchaSendTooFastException(Throwable cause) {
        super(cause);
    }
}

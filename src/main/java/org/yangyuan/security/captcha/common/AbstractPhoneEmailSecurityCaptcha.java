package org.yangyuan.security.captcha.common;

import java.util.regex.Pattern;

import org.yangyuan.security.exception.CaptchaUnknownAccountTypeException;

/**
 * 手机短信、邮件安全认证验证码抽象实现
 * @author yangyuan
 * @date 2018年5月4日
 */
public abstract class AbstractPhoneEmailSecurityCaptcha extends AbstractSecurityCaptcha{
    /**
     * 手机号正则
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$");
    /**
     * 邮箱正则
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@([^@]+\\.)+[^@.]+$");
    
    /**
     * 获取验证码标题文本
     * @return 验证码标题文本
     */
    protected abstract String title();
    
    /**
     * 获取验证码内容文本
     * @param code 验证码
     * @return 验证码内容文本
     */
    protected abstract String content(String code);
    
    /**
     * 发送手机验证码
     * <p>此方法是发送手机短信的具体实现</p>
     * <p>一般通过对接阿里云等第三方平台实现短信发送功能</p>
     * @param account 手机号
     * @param code 验证码
     */
    protected abstract void sendToPhone(String account, String code);
    
    /**
     * 发送邮件验证码
     * <p>此方法是发送邮件的具体实现</p>
     * <p>一般通过使用javax.mail包实现企业邮箱操作</p>
     * @param account 邮箱
     * @param code 验证码
     */
    protected abstract void sendToEmail(String account, String code);
    
    @Override
    protected void sendCode(String account, String code) {
        if(PHONE_PATTERN.matcher(account).matches()){
            sendToPhone(account, code);
            return;
        }
        if(EMAIL_PATTERN.matcher(account).matches()){
            sendToEmail(account, code);
            return;
        }
        throw new CaptchaUnknownAccountTypeException();
    }
    
}

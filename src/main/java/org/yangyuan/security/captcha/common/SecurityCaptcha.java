package org.yangyuan.security.captcha.common;

import org.yangyuan.security.exception.CaptchaException;

/**
 * 安全认证验证码定义
 * @author yangyuan
 * @date 2018年5月3日
 */
public interface SecurityCaptcha {
    
    /**
     * 向指定账号发送验证码
     * @param account 账号
     * @throws CaptchaException
     */
    void send(String account) throws CaptchaException;
    
    /**
     * 匹配指定账号对应的验证码
     * @param account 账号
     * @param code 验证码
     * @return <b>true</b> 如果给定的账号匹配给定的验证码<br><b>false</b> 其他情况
     */
    boolean match(String account, String code);
    
    /**
     * 移除指定账号对应的验证码
     * @param account 账号
     */
    void remove(String account);
    
    /**
     * 生成一个新的验证码
     * <p>验证码生成策略交予框架使用者实现</p>
     * <p>因为这是一个非常灵活的实现</p>
     * <p>对于手机短信、邮箱邮件验证码，建议生成纯数字</p>
     * <p>对于图形验证码，需要配合具体图形生成器实现。有些图形生成器支持汉字，那么此方法可以返回汉字；若不支持，那么只能返回字母或数字。</p>
     * <p>理论上生成的验证码允许重复，但重复率不要过高</p>
     * @param size 验证码长度
     * @return 验证码
     */
    String newCode(int size);
}

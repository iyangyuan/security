package org.yangyuan.security.captcha.common;

/**
 * 安全认证图形验证码定义
 * @author yangyuan
 * @date 2018年5月3日
 */
public interface SecurityImageCaptcha extends SecurityCaptcha{
    
    /**
     * 创建一个新的唯一的令牌
     * <p>对于图形验证码而言，令牌是区分不同客户端的唯一标识</p>
     * @return 唯一令牌
     */
    String newToken();
    
    /**
     * 获取指定令牌对应的验证码
     * <p>每个令牌对应唯一的验证码，验证码以图像的形式展示给用户</p>
     * @param token 令牌，客户端标识
     * @return 
     *         验证码。
     *         <br>
     *         如果指定令牌未发送过任何验证码或已经失效，则会返回null
     */
    String getCode(String token);
    
}

package org.yangyuan.security.captcha.common;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.exception.CaptchaException;
import org.yangyuan.security.exception.CaptchaSendTooFastException;

import redis.clients.jedis.Jedis;

/**
 * 安全认证验证码抽象实现
 * @author yangyuan
 * @date 2018年5月3日
 */
public abstract class AbstractSecurityCaptcha implements SecurityCaptcha{
    
    /**
     * 账号-验证码 redis key
     */
    private static final String ACCOUNT_CODE_KEY_TMPL = "service:captcha:{name}:account:{account}:code";
    /**
     * 账号-验证码频率限制 redis key
     */
    private static final String ACCOUNT_CODE_LIMIT_KEY_TMPL = "service:captcha:{name}:account:{account}:limit";
    
    /**
     * 发送验证码核心实现
     * @param account 账号
     * @param code 验证码
     */
    protected abstract void sendCode(String account, String code);
    
    /**
     * 获取服务名称
     * <p>用来区分不同的业务，防止不同业务之间数据混乱</p>
     * <p>比如：注册验证码、找回密码验证码等业务，必须维护各自私有的数据，不能共享</p>
     * @return 服务名称
     */
    protected abstract String name();
    
    @Override
    public void send(String account) throws CaptchaException{
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            /**
             * 生成redis key
             */
            String codeKey = tmplRender(ACCOUNT_CODE_KEY_TMPL, account);
            String codeLimitKey = tmplRender(ACCOUNT_CODE_LIMIT_KEY_TMPL, account);
            
            /**
             * 发送频率检查
             */
            if(redis.exists(codeLimitKey)){
                throw new CaptchaSendTooFastException();
            }
            
            /**
             * 发送验证码
             */
            String code = newCode(6);
            sendCode(account, code);
            
            /**
             * 记录验证码
             */
            redis.set(codeKey, code);
            redis.expire(codeKey, ResourceManager.captcha().getNormalExpireSecond());
            
            /**
             * 发送频率限制
             */
            redis.set(codeLimitKey, account);
            redis.expire(codeLimitKey, ResourceManager.captcha().getNormalMinIntervalSecond());
            
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    @Override
    public boolean match(String account, String code) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            String codeKey = tmplRender(ACCOUNT_CODE_KEY_TMPL, account);
            String _code = redis.get(codeKey);
            
            return StringUtils.isNotBlank(_code) && _code.equalsIgnoreCase(code);
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    @Override
    public void remove(String account) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            String codeKey = tmplRender(ACCOUNT_CODE_KEY_TMPL, account);
            redis.del(codeKey);
            
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    /**
     * redis key模板渲染
     * @param tmpl 模板
     * @param account 账号
     * @return redis key
     */
    private String tmplRender(String tmpl, String account){
        return tmpl.replace("{name}", name())
                   .replace("{account}", account);
    }
}

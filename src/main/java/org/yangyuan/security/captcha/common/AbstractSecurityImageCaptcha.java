package org.yangyuan.security.captcha.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.exception.common.CaptchaException;

import redis.clients.jedis.Jedis;

/**
 * 安全认证图形验证码抽象实现
 * @author yangyuan
 * @date 2018年5月4日
 */
public abstract class AbstractSecurityImageCaptcha implements SecurityImageCaptcha{
    /**
     * token-验证码 redis key
     */
    private static final String TOKEN_CODE_KEY_TMPL = "service:captcha:image:{name}:token:{token}:code";
    /**
     * token-验证码错误频率检测 redis key
     */
    private static final String TOKEN_CODE_LIMIT_KEY_TMPL = "service:captcha:image:{name}:token:{token}:limit";
    
    /**
     * 获取服务名称
     * <p>用来区分不同的业务，防止不同业务之间数据混乱</p>
     * <p>比如：注册图形验证码、登陆图形验证码等业务，必须维护各自私有的数据，不能共享</p>
     * @return 服务名称
     */
    protected abstract String name();
    
    @Override
    public void send(String token) throws CaptchaException{
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            /**
             * 生成redis key
             */
            String codeKey = tmplRender(TOKEN_CODE_KEY_TMPL, token);
            String codeLimitKey = tmplRender(TOKEN_CODE_LIMIT_KEY_TMPL, token);
            
            /**
             * 错误频率检查
             */
            String code = newCode(4);
            String limit = redis.get(codeLimitKey);
            if(StringUtils.isNotBlank(limit) 
                    && Integer.parseInt(limit) >= ResourceManager.captcha().getImagePeriodMaxWrongCount()){
                code = newCode(6);
            }
            
            /**
             * 记录验证码
             */
            redis.set(codeKey, code);
            redis.expire(codeKey, ResourceManager.captcha().getImageExpireSecond());
            
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    @Override
    public boolean match(String token, String code) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            /**
             * 生成redis key
             */
            String codeKey = tmplRender(TOKEN_CODE_KEY_TMPL, token);
            String codeLimitKey = tmplRender(TOKEN_CODE_LIMIT_KEY_TMPL, token);
            
            /**
             * 匹配验证码
             */
            String _code = redis.get(codeKey);
            boolean result = StringUtils.isNotBlank(_code) && _code.equalsIgnoreCase(code);
            if(result){  //认证成功，清除错误检测
                redis.del(codeLimitKey);
            }else{       //认证失败，更新错误检测数据
                redis.incr(codeLimitKey);
                redis.expire(codeLimitKey, ResourceManager.captcha().getImageWrongPeriodSecond());
            }
            
            return result;
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    @Override
    public void remove(String token) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            String codeKey = tmplRender(TOKEN_CODE_KEY_TMPL, token);
            redis.del(codeKey);
            
        } finally {
            if(redis != null){
                redis.close();
                redis = null;
            }
        }
    }
    
    @Override
    public String newToken() {
        return name() + "_" + RandomStringUtils.randomNumeric(32);
    }
    
    @Override
    public String getCode(String token) {
        Jedis redis = null;
        try {
            redis = ResourceManager.common().getRedisResourceFactory().getResource();
            
            String codeKey = tmplRender(TOKEN_CODE_KEY_TMPL, token);
            
            return redis.get(codeKey);
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
     * @param token 令牌
     * @return redis key
     */
    private String tmplRender(String tmpl, String token){
        return tmpl.replace("{name}", name())
                   .replace("{token}", token);
    }
}

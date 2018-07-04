package org.yangyuan.security.dao;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.CaptchaToken;
import org.yangyuan.security.core.UsernamePasswordToken;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.dao.common.AuthSessionDao;
import org.yangyuan.security.exception.common.SecurityException;

/**
 * 本地认证数据访问层实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class JdbcSessionDao implements AuthSessionDao{
    
    @Override
    public User auth(SecurityToken token) {
        if(token instanceof UsernamePasswordToken){
            return ResourceManager.dao().getJdbcRealm().getUser(token);
        }
        if(token instanceof CaptchaToken){
            return ResourceManager.dao().getCaptchaRealm().getUser(token);
        }
        throw new SecurityException("Unknown token type[" + token.getClass().getName() + "]");
    }
    
}

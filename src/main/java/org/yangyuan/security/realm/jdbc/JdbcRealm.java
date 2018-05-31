package org.yangyuan.security.realm.jdbc;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.UsernamePasswordToken;
import org.yangyuan.security.core.common.PasswordManager;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.exception.AuthPasswordWrongException;
import org.yangyuan.security.exception.AuthUsernameNotFoundException;
import org.yangyuan.security.realm.bean.JdbcUser;
import org.yangyuan.security.realm.bean.JdbcUserAdaptor;
import org.yangyuan.security.realm.common.AbstractRealm;

/**
 * 持久化数据源实现
 * @author yangyuan
 * @date 2018年3月15日
 */
public class JdbcRealm extends AbstractRealm{
    
    @Override
    public User getUser(SecurityToken token) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        
        /**
         * 适配数据
         */
        JdbcUser jdbcUser = new JdbcUser(usernamePasswordToken.getUsername());
        JdbcUserAdaptor userAdaptor = ResourceManager.dao().getJdbcRealmAdaptor().selectByJdbcUser(jdbcUser);
        
        /**
         * 用户不存在
         */
        if(userAdaptor == null){
            throw new AuthUsernameNotFoundException("用户不存在");
        }
        
        /**
         * 密码不正确
         */
        if(!PasswordManager.matches(usernamePasswordToken.getPasswrod(), userAdaptor.getPassword())){
            throw new AuthPasswordWrongException("密码不正确");
        }
        
        return getUser(userAdaptor.getUnionid(), userAdaptor.getRoles());
    }
    
}

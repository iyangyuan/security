package org.yangyuan.security.dao;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.dao.common.AuthSessionDao;

/**
 * 本地认证数据访问层实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class JdbcSessionDao implements AuthSessionDao{
    
    @Override
    public User auth(SecurityToken token) {
        return ResourceManager.dao().getJdbcRealm().getUser(token);
    }
    
}

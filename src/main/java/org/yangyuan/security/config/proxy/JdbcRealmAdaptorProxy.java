package org.yangyuan.security.config.proxy;

import org.yangyuan.security.exception.common.AuthenticationBusinessException;
import org.yangyuan.security.realm.bean.JdbcUser;
import org.yangyuan.security.realm.bean.JdbcUserAdaptor;
import org.yangyuan.security.realm.common.JdbcRealmAdaptor;

/**
 * 持久化数据源适配器代理
 * @author yangyuan
 * @date 2018年6月20日
 */
public class JdbcRealmAdaptorProxy implements JdbcRealmAdaptor{
    
    private final JdbcRealmAdaptor jdbcRealmAdaptor;
    
    public JdbcRealmAdaptorProxy(JdbcRealmAdaptor jdbcRealmAdaptor){
        this.jdbcRealmAdaptor = jdbcRealmAdaptor;
    }
    
    @Override
    public JdbcUserAdaptor selectByJdbcUser(JdbcUser user) throws AuthenticationBusinessException {
        if(jdbcRealmAdaptor == null){
            throw new SecurityException("Load security.properties[dao.jdbcRealmAdaptor] failed!");
        }
        
        return jdbcRealmAdaptor.selectByJdbcUser(user);
    }
    
}

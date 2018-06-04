package org.yangyuan.security.realm.common;

import org.yangyuan.security.exception.common.AuthenticationBusinessException;
import org.yangyuan.security.realm.bean.JdbcUser;
import org.yangyuan.security.realm.bean.JdbcUserAdaptor;

/**
 * 持久化数据源适配器定义
 * @author yangyuan
 * @date 2018年3月15日
 */
public interface JdbcRealmAdaptor {
    /**
     * 适配持久化用户数据
     * @param user 持久化用户数据
     * @return 持久化用户数据适配器
     * @throws AuthenticationBusinessException
     */
    JdbcUserAdaptor selectByJdbcUser(JdbcUser user) throws AuthenticationBusinessException;
}

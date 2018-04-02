package org.yangyuan.security.realm.common;

import org.yangyuan.security.realm.bean.UserAdaptor;

/**
 * 持久化数据源适配器定义
 * @author yangyuan
 * @date 2018年3月15日
 */
public interface JdbcRealmAdaptor {
    
    /**
     * 根据用户登录账号查询用户信息
     * @param username 登陆账号
     * @return 
     *      <ul>
     *          <li>password</li>
     *          <li>unionid</li>
     *          <li>roles</li>
     *      </ul>
     */
    UserAdaptor selectByUsername(String username);
    
}

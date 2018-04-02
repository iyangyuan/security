package org.yangyuan.security.realm.common;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.core.common.SecurityToken;

/**
 * 数据源定义
 * @author yangyuan
 * @date 2018年3月15日
 */
public interface Realm {
    
    /**
     * 获取认证系统用户
     * @param token 令牌
     * @return 认证系统用户，目前包含unionid、roles
     */
    User getUser(SecurityToken token);
    
}

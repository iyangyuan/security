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
     * @return 认证系统用户，实例中必须包含[用户全局唯一id(unionid)]和[用户角色列表(roles)]
     */
    User getUser(SecurityToken token);
    
}

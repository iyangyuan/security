package org.yangyuan.security.dao.common;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.core.common.SecurityToken;

/**
 * 认证数据访问层定义
 * @author yangyuan
 * @date 2017年4月26日
 */
public interface AuthSessionDao {
    
    /**
     * 认证
     * @param token 令牌
     * @return 正常执行，认证成功；认证失败会抛出相应的运行时异常。返回的User模型中unionid必然非空。
     */
    User auth(SecurityToken token);
    
}

package org.yangyuan.security.core;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.core.common.ConcurrentSubjectControl;
import org.yangyuan.security.util.SecurityUtils;

/**
 * 单端覆盖登陆并发安全认证主题控制实现
 * <p>同一个账号同一时刻只能在一个客户端登陆，如果之前在其他客户端登陆过，那么之前的登陆将失效</p>
 * @author yangyuan
 * @date 2018年4月25日
 */
public class SingleConcurrentSubjectControl implements ConcurrentSubjectControl{
    
    @Override
    public void control(User user) {
        SecurityUtils.logout(user.getUnionid());
    }
    
}

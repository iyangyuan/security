package org.yangyuan.security.core;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.core.common.ConcurrentSubjectControl;

/**
 * 多端登陆并发安全认证主题控制实现
 * <p>允许同一个账号同时在不同客户端登陆</p>
 * @author yangyuan
 * @date 2018年4月25日
 */
public class MultiportConcurrentSubjectControl implements ConcurrentSubjectControl{
    
    @Override
    public void control(User user) {
        return;
    }
    
}

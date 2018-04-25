package org.yangyuan.security.core.common;

import org.yangyuan.security.bean.User;

/**
 * 并发安全认证主题控制定义
 * @author yangyuan
 * @date 2018年4月25日
 */
public interface ConcurrentSubjectControl {
    /**
     * 控制
     * @param user 用户模型
     */
    void control(User user);
}

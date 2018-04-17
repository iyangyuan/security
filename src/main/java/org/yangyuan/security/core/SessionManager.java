package org.yangyuan.security.core;

import org.yangyuan.security.core.common.Subject;

/**
 * 会话管理器
 * @author yangyuan
 * @date 2017年4月26日
 */
@SuppressWarnings("rawtypes")
public class SessionManager{
    protected static final ThreadLocal<Subject> LOCAL_SUBJECT = new ThreadLocal<Subject>();
    
    /**
     * 获取当前线程中的主题
     * @return 主题
     */
    public static Subject getSubject() {
        return LOCAL_SUBJECT.get();
    }
    
    /**
     * 设置当前线程中的主题
     * @param subject 主题
     */
    public static void setSubject(Subject subject) {
        LOCAL_SUBJECT.set(subject);
    }
    
}

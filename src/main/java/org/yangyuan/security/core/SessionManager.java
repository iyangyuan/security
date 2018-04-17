package org.yangyuan.security.core;

import org.yangyuan.security.core.common.Subject;

/**
 * 会话管理器
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SessionManager{
    protected static final ThreadLocal<Subject<?, ?>> LOCAL_SUBJECT = new ThreadLocal<Subject<?, ?>>();
    
    /**
     * 获取当前线程中的主题
     * @return 主题
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Subject<K, V> getSubject() {
        return (Subject<K, V>) LOCAL_SUBJECT.get();
    }
    
    /**
     * 设置当前线程中的主题
     * @param subject 主题
     */
    public static <K, V> void setSubject(Subject<K, V> subject) {
        LOCAL_SUBJECT.set(subject);
    }
    
}

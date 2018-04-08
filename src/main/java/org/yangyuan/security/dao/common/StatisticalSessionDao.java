package org.yangyuan.security.dao.common;

/**
 * 统计数据访问层定义
 * @author yangyuan
 * @date 2018年4月8日
 */
public interface StatisticalSessionDao {
    
    /**
     * 获取在线人数
     * @return 在线人数，不要求精确值
     */
    long numberOfOnline();
    
    /**
     * 获取活跃人数
     * @return 活跃人数，不要求精确值
     */
    long numberOfActivity();
    
}

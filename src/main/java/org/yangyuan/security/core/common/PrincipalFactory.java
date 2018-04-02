package org.yangyuan.security.core.common;

/**
 * 安全唯一标识生成器定义
 * @author yangyuan
 * @date 2017年4月26日
 */
public interface PrincipalFactory {
    
    /**
     * 创建安全唯一标识
     * @param userUnionid 绑定的用户唯一标识
     * @return 随机安全唯一标识
     */
    String newPrincipal(String userUnionid);
    
    /**
     * 获取安全唯一标识中绑定的用户唯一标识
     * @param principal 安全唯一标识
     * @return 用户唯一标识
     */
    String getUserUnionid(String principal);
    
    /**
     * 获取安全唯一标识所属分区
     * <br>
     * 此方法一般用于底层数据存储分区优化，避免数据堆积
     * @param principal 安全唯一标识
     * @return 分区
     */
    String getPartition(String principal);
    
}

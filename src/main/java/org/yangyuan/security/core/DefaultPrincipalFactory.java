package org.yangyuan.security.core;

import org.yangyuan.security.core.common.PrincipalFactory;

/**
 * 默认安全唯一标识生成器实现
 * @author yangyuan
 * @date 2017年4月26日
 */
public class DefaultPrincipalFactory implements PrincipalFactory{
    
    @Override
    public String newPrincipal(String userUnionid) {
        return userUnionid + "_" + System.nanoTime();
    }
    
    @Override
    public String getUserUnionid(String principal) {
        return principal.split("_")[0];
    }
    
    @Override
    public String getPartition(String principal) {
        String userUnionid = getUserUnionid(principal);
        
        return userUnionid.substring(0, 1);
    }
    
}

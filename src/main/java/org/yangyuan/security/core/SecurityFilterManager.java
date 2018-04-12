package org.yangyuan.security.core;

import org.yangyuan.security.filter.AnonSecurityFilter;
import org.yangyuan.security.filter.AuthcSecurityFilter;
import org.yangyuan.security.filter.BasicHttpAuthenticationSecurityFilter;
import org.yangyuan.security.filter.RoleSecurityFilter;
import org.yangyuan.security.filter.common.SecurityFilter;

/**
 * 安全过滤器管理器
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterManager {
    private static final SecurityFilter chainPointer;
    
    static {
        /**
         * 初始化安全过滤器调用链
         */
        AnonSecurityFilter anonSecurityFilter = new AnonSecurityFilter();  //匿名过滤器
        AuthcSecurityFilter authcSecurityFilter = new AuthcSecurityFilter();  //基础认证过滤器
        RoleSecurityFilter roleSecurityFilter = new RoleSecurityFilter();  //角色认证过滤器
        BasicHttpAuthenticationSecurityFilter basicHttpAuthenticationSecurityFilter = new BasicHttpAuthenticationSecurityFilter();  //http basic authentication认证过滤器
        anonSecurityFilter.setNext(authcSecurityFilter);
        authcSecurityFilter.setNext(roleSecurityFilter);
        roleSecurityFilter.setNext(basicHttpAuthenticationSecurityFilter);
        
        chainPointer = anonSecurityFilter;
    }
    
    /**
     * 获取安全过滤器调用链
     * @return
     */
    public static SecurityFilter getFilterChain(){
        return chainPointer;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(256);
        SecurityFilter filter = getFilterChain();
        if(filter != null){
            builder.append("[");
            builder.append(filter.getClass().getName());
            builder.append("]");
            while(filter.getNext() != null){
                filter = filter.getNext();
                builder.append("->[");
                builder.append(filter.getClass().getName());
                builder.append("]");
            }
        }
        
        return new String(builder);
    }
    
}

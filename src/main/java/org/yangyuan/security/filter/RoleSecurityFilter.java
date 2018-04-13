package org.yangyuan.security.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.Role;
import org.yangyuan.security.core.DefaultSubject;
import org.yangyuan.security.core.SessionManager;
import org.yangyuan.security.core.annotation.SecurityFilterComponent;
import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.exception.SecurityFilterForbiddenException;
import org.yangyuan.security.filter.common.SecurityFilter;
import org.yangyuan.security.util.SecurityUtils;

/**
 * 角色认证实现
 * @author yangyuan
 * @date 2017年4月26日
 */
@SecurityFilterComponent(value = "index/3")
public class RoleSecurityFilter implements SecurityFilter{
    
    private static final String FILETER_NAME = "roles";
    
    @Override
    public boolean approve(String permission) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        return permission.toLowerCase().startsWith(FILETER_NAME);
    }

    @Override
    public void doFilter(String permission, HttpServletRequest request) {
        DefaultSubject subject = (DefaultSubject) SessionManager.getSubject();
        
        /**
         * 未登录
         */
        if(subject == null){
            throw new SecurityFilterForbiddenException();
        }
        
        /**
         * 登录无效
         */
        if(!subject.isValid()){
            throw new SecurityFilterForbiddenException();
        }
        
        /**
         * 角色验证
         */
        List<Role> roles = Role.parseRole(permission);
        if(SecurityUtils.hasAnyRole(roles)){
            return;  //认证通过
        }
        
        /**
         * 角色认证未通过
         */
        throw new SecurityFilterForbiddenException();
    }

}

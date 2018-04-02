package org.yangyuan.security.realm.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.Role;
import org.yangyuan.security.bean.User;

/**
 * 数据源抽象实现
 * @author yangyuan
 * @date 2018年3月15日
 */
public abstract class AbstractRealm implements Realm{
    
    /**
     * 构造认证系统用户
     * @param unionid 用户唯一id
     * @param roles 字符串表示的用户角色列表，多个角色逗号分隔
     * @return 认证系统用户，目前包含unionid、roles
     */
    protected User getUser(String unionid, String roles){
        User user = new User();
        user.setUnionid(unionid);
        List<Role> _roles = new ArrayList<Role>();
        if(StringUtils.isNotBlank(roles)){
            String permission = "roles[" + roles + "]";
            _roles = Role.parseRole(permission);
        }
        user.setRoles(_roles);
        
        return user;
    }
    
}

package org.yangyuan.security.core;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.core.common.ConcurrentSubjectControl;
import org.yangyuan.security.exception.ConcurrentSubjectControlException;
import org.yangyuan.security.util.SecurityUtils;

/**
 * 单端独占登陆并发安全认证主题控制实现
 * <p>同一个账号同一时刻只能在一个客户端登陆，如果之前在其他客户端登陆过，那么本次登陆将会失败，除非其他客户端主动退出登陆</p>
 * @author yangyuan
 * @date 2018年4月25日
 */
public class RefuseConcurrentSubjectControl implements ConcurrentSubjectControl{
    
    @Override
    public void control(User user) {
        if(SecurityUtils.getUserSubjects(user.getUnionid()).size() > 0){
            throw new ConcurrentSubjectControlException();
        }
    }
    
}

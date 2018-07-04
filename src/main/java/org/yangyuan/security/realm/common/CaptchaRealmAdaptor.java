package org.yangyuan.security.realm.common;

import org.yangyuan.security.exception.common.AuthenticationBusinessException;
import org.yangyuan.security.realm.bean.CaptchaUser;
import org.yangyuan.security.realm.bean.CaptchaUserAdaptor;

/**
 * 验证码数据源适配器定义
 * @author yangyuan
 * @date 2018年7月4日
 */
public interface CaptchaRealmAdaptor {
    /**
     * 适配验证码用户数据
     * @param user 验证码用户数据
     * @return 验证码用户数据适配器
     * @throws AuthenticationBusinessException
     */
    CaptchaUserAdaptor selectByCaptchaUser(CaptchaUser user) throws AuthenticationBusinessException;
}

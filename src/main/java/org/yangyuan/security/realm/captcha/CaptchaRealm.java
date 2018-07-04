package org.yangyuan.security.realm.captcha;

import org.yangyuan.security.bean.User;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.CaptchaToken;
import org.yangyuan.security.core.common.SecurityToken;
import org.yangyuan.security.exception.AuthUsernameNotFoundException;
import org.yangyuan.security.realm.bean.CaptchaUser;
import org.yangyuan.security.realm.bean.CaptchaUserAdaptor;
import org.yangyuan.security.realm.common.AbstractRealm;

/**
 * 验证码数据源实现
 * @author yangyuan
 * @date 2018年7月4日
 */
public class CaptchaRealm extends AbstractRealm{

    @Override
    public User getUser(SecurityToken token) {
        CaptchaToken captchaToken = (CaptchaToken) token;
        
        /**
         * 适配数据
         */
        CaptchaUser captchaUser = new CaptchaUser(captchaToken.getUsername(), captchaToken.getCode());
        CaptchaUserAdaptor userAdaptor = ResourceManager.dao().getCaptchaRealmAdaptor().selectByCaptchaUser(captchaUser);
        
        /**
         * 用户不存在
         */
        if(userAdaptor == null){
            throw new AuthUsernameNotFoundException("用户不存在");
        }
        
        return getUser(userAdaptor.getUnionid(), userAdaptor.getRoles());
    }

}

package org.yangyuan.security.config.proxy;

import org.yangyuan.security.exception.common.AuthenticationBusinessException;
import org.yangyuan.security.realm.bean.CaptchaUser;
import org.yangyuan.security.realm.bean.CaptchaUserAdaptor;
import org.yangyuan.security.realm.common.CaptchaRealmAdaptor;

/**
 * 验证码数据源适配器代理
 * @author yangyuan
 * @date 2018年7月4日
 */
public class CaptchaRealmAdaptorProxy implements CaptchaRealmAdaptor{
    
    private final CaptchaRealmAdaptor captchaRealmAdaptor;
    
    public CaptchaRealmAdaptorProxy(CaptchaRealmAdaptor captchaRealmAdaptor){
        this.captchaRealmAdaptor = captchaRealmAdaptor;
    }
    
    @Override
    public CaptchaUserAdaptor selectByCaptchaUser(CaptchaUser user) throws AuthenticationBusinessException {
        if(captchaRealmAdaptor == null){
            throw new SecurityException("Load security.properties[dao.captchaRealmAdaptor] failed!");
        }
        
        return captchaRealmAdaptor.selectByCaptchaUser(user);
    }

}

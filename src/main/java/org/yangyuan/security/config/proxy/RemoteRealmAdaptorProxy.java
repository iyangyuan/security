package org.yangyuan.security.config.proxy;

import org.yangyuan.security.exception.common.AuthenticationBusinessException;
import org.yangyuan.security.realm.bean.RemoteUser;
import org.yangyuan.security.realm.bean.RemoteUserAdaptor;
import org.yangyuan.security.realm.common.RemoteRealmAdaptor;

/**
 * 第三方授权数据源适配器代理
 * @author yangyuan
 * @date 2018年6月20日
 */
public class RemoteRealmAdaptorProxy implements RemoteRealmAdaptor{
    
    private final RemoteRealmAdaptor remoteRealmAdaptor;
    
    public RemoteRealmAdaptorProxy(RemoteRealmAdaptor remoteRealmAdaptor){
        this.remoteRealmAdaptor = remoteRealmAdaptor;
    }
    
    @Override
    public RemoteUserAdaptor selectByRemoteUser(RemoteUser user) throws AuthenticationBusinessException {
        if(remoteRealmAdaptor == null){
            throw new SecurityException("Load security.properties[dao.remoteRealmAdaptor] failed!");
        }
        
        return remoteRealmAdaptor.selectByRemoteUser(user);
    }

}

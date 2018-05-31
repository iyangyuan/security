package org.yangyuan.security.realm.common;

import org.yangyuan.security.realm.bean.RemoteUser;
import org.yangyuan.security.realm.bean.RemoteUserAdaptor;

/**
 * 第三方授权数据源适配器定义
 * @author yangyuan
 * @date 2018年3月15日
 */
public interface RemoteRealmAdaptor {
    /**
     * 适配第三方授权用户数据
     * @param user 第三方授权用户数据
     * @return 第三方授权用户数据适配器
     */
    RemoteUserAdaptor selectByRemoteUser(RemoteUser user);
}

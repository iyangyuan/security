package org.yangyuan.security.realm.common;

import org.yangyuan.security.realm.bean.UserAdaptor;

/**
 * 第三方授权数据源适配器定义
 * @author yangyuan
 * @date 2018年3月15日
 */
public interface RemoteRealmAdaptor {
    
    /**
     * 根据第三方用户信息查询本地用户信息
     * @param user 第三方用户信息
     *      <ul>
     *          <li>openid</li>
     *          <li>nickname</li>
     *          <li>portrait</li>
     *      </ul>
     * @return
     *      <ul>
     *          <li>unionid</li>
     *          <li>roles</li>
     *      </ul>
     */
    UserAdaptor selectByUser(UserAdaptor user);
    
}

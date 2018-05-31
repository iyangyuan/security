package org.yangyuan.security.realm.bean;

/**
 * 第三方授权用户数据适配器
 * @author yangyuan
 * @date 2018年5月31日
 */
public class RemoteUserAdaptor extends UserAdaptor{
    /**
     * 构造方法
     * @param unionid 用户全局唯一id
     * @param roles 用户角色列表，多个以逗号分隔，忽略空格
     */
    public RemoteUserAdaptor(String unionid, String roles){
        super(unionid, roles);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}

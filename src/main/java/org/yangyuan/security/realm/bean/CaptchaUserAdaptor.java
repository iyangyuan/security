package org.yangyuan.security.realm.bean;

/**
 * 验证码用户数据适配器
 * @author yangyuan
 * @date 2018年7月4日
 */
public class CaptchaUserAdaptor extends UserAdaptor{
    
    /**
     * 构造方法
     * @param unionid 用户全局唯一id
     * @param roles 用户角色列表，多个以逗号分隔，忽略空格
     */
    public CaptchaUserAdaptor(String unionid, String roles) {
        super(unionid, roles);
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}

package org.yangyuan.security.core.common;

/**
 * 抽象认证令牌实现
 * @author yangyuan
 * @date 2018年6月7日
 */
public abstract class AbstractSecurityToken implements SecurityToken{
    /**
     * 记住我
     */
    private final boolean remember;
    
    /**
     * 构造方法
     * @param remember 记住我
     */
    public AbstractSecurityToken(boolean remember){
        this.remember = remember;
    }

    @Override
    public boolean isRemember() {
        return this.remember;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[remember](");
        builder.append(isRemember());
        builder.append(")\n");
        
        return new String(builder);
    }
}

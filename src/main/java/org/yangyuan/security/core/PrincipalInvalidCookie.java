package org.yangyuan.security.core;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.core.common.AbstractCookie;

/**
 * 无效的主cookie实现
 * @author yangyuan
 * @date 2018年4月3日
 */
public class PrincipalInvalidCookie extends AbstractCookie{

    @Override
    protected String getName() {
        return ResourceManager.cookie().getName();
    }

    @Override
    protected String getValue() {
        return StringUtils.EMPTY;
    }

    @Override
    protected int getMaxAge() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}

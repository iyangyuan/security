package org.yangyuan.security.core.common;

import org.yangyuan.security.servlet.http.SecurityHttpServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: yangyuan
 * @Date: 2019/5/29 17:13
 */
public abstract class AbstractPrincipalReader implements PrincipalReader{
    @Override
    public HttpServletRequest wrap(HttpServletRequest request) {
        return new SecurityHttpServletRequestWrapper(request);
    }
}

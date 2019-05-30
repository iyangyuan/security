package org.yangyuan.security.core;

import org.yangyuan.security.core.common.PrincipalReader;

import javax.servlet.http.HttpServletRequest;

/**
 * 从Header中读取Principal
 * @Auther: yangyuan
 * @Date: 2019/5/29 16:44
 */
public class HeaderPrincipalReader implements PrincipalReader {
    private String headerName;

    public HeaderPrincipalReader(String headerName){
        this.headerName = headerName;
    }

    @Override
    public HttpServletRequest wrap(HttpServletRequest request) {
        return request;
    }

    @Override
    public String read(HttpServletRequest request) {
        return request.getHeader(headerName);
    }
}

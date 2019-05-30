package org.yangyuan.security.core;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.core.common.PrincipalReader;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 从cookie中读取Principal
 * @Auther: yangyuan
 * @Date: 2019/5/29 16:33
 */
public class CookiePrincipalReader implements PrincipalReader {
    private String cookieName;

    public CookiePrincipalReader(String cookieName){
        this.cookieName = cookieName;
    }

    @Override
    public HttpServletRequest wrap(HttpServletRequest request) {
        return request;
    }

    @Override
    public String read(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }

        String clientPrincipal = null;
        for(Cookie cookie : cookies){
            if(cookieName.equals(cookie.getName())){
                clientPrincipal = cookie.getValue();
                break;
            }
        }
        if(StringUtils.isBlank(clientPrincipal)){
            return null;
        }

        return clientPrincipal;
    }
}

package org.yangyuan.security.core;

import org.yangyuan.security.core.common.PrincipalReader;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 从url中读取Principal
 * @Auther: yangyuan
 * @Date: 2019/5/29 16:37
 */
public class UrlPrincipalReader implements PrincipalReader {
    private String paramName;

    public UrlPrincipalReader(String paramName){
        this.paramName = paramName;
    }

    @Override
    public HttpServletRequest wrap(HttpServletRequest request) {
        return request;
    }

    @Override
    public String read(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        for(String key : params.keySet()){
            if(paramName.equals(key)){
                return params.get(key)[0];
            }
        }
        return null;
    }
}

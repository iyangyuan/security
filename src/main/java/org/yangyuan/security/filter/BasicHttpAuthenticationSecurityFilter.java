package org.yangyuan.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.BasicAuth;
import org.yangyuan.security.core.annotation.SecurityFilterComponent;
import org.yangyuan.security.exception.FilterException;
import org.yangyuan.security.exception.SecurityFilterBasicAuthException;
import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.filter.common.AbstractSecurityCacheFilter;

/**
 * http basic authentication认证实现
 * @author yangyuan
 * @date 2018年4月12日
 */
@SecurityFilterComponent(value = "index/4")
public class BasicHttpAuthenticationSecurityFilter extends AbstractSecurityCacheFilter{
    
    private static final String FILETER_NAME = "basic";
    
    /**
     * HTTP basic authentication header
     */
    protected static final String AUTHORIZATION_HEADER = "Authorization";
    
    /**
     * HTTP basic authenticate header
     */
    protected static final String AUTHENTICATE_HEADER = "WWW-Authenticate";
    
    /**
     * HTTP basic authenticate header scheme
     */
    protected static final String AUTHENTICATE_SCHEME = HttpServletRequest.BASIC_AUTH;
    
    @Override
    public boolean approve(String permission) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        return permission.toLowerCase().startsWith(FILETER_NAME);
    }

    @Override
    public void doFilter(String permission, HttpServletRequest request) throws FilterException{
        /**
         * 获取客户端授权凭证
         */
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.isBlank(authorizationHeader)){
            throw new SecurityFilterBasicAuthException();
        }
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2){
            throw new SecurityFilterBasicAuthException();
        }
        String authorization = authorizations[1];
        
        /**
         * 解析
         */
        BasicAuth basicAuth = cached(permission);  //优先从缓存中获取
        if(basicAuth == null){  //缓存未命中
            basicAuth = BasicAuth.parseBasicAuth(permission);  //解析
            caching(permission, basicAuth);  //缓存
        }
        
        /**
         * 认证
         */
        if(basicAuth.contains(authorization)){
            return;  //认证通过
        }
        
        /**
         * 认证失败
         */
        throw new SecurityFilterBasicAuthException();
    }
    
    /**
     * 向客户端响应http basic authentication授权
     * @param response http响应对象
     */
    public static void sendChallenge(HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String authenticateHeader = AUTHENTICATE_SCHEME + " realm=\"Security Application\"";
        response.setHeader(AUTHENTICATE_HEADER, authenticateHeader);
    }
    
}

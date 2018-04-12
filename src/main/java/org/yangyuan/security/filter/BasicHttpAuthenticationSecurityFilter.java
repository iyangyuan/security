package org.yangyuan.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.bean.BasicAuth;
import org.yangyuan.security.exception.SecurityFilterBasicAuthException;
import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.filter.common.AbstractSecurityFilter;

/**
 * http basic authentication认证实现
 * @author yangyuan
 * @date 2018年4月12日
 */
public class BasicHttpAuthenticationSecurityFilter extends AbstractSecurityFilter{
    
    private static final String FILETER_NAME = "basic[";
    
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
    public void doFilter(String permission, HttpServletRequest request) {
        if(StringUtils.isBlank(permission)){
            throw new SecurityFilterErrorException("permission is blank");
        }
        
        if(permission.toLowerCase().startsWith(FILETER_NAME)){
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
             * 解析表达式
             */
            BasicAuth basicAuth = BasicAuth.parseBasicAuth(permission);
            
            /**
             * 认证成功
             */
            if(basicAuth.contains(authorization)){
                return;
            }
            
            /**
             * 认证失败
             */
            throw new SecurityFilterBasicAuthException();
        }
        
        next(permission, request);
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

package org.yangyuan.security.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.yangyuan.security.config.ResourceManager;

/**
 * http basic authentication认证模型
 * @author yangyuan
 * @date 2018年4月12日
 */
public class BasicAuth {
    /**
     * 授权列表
     */
    private final List<String> authorizations;
    
    private BasicAuth(List<String> authorizations){
        this.authorizations = authorizations;
    }
    
    /**
     * 认证授权
     * @param authorization 授权
     * @return 
     *      <b>true</b> 认证成功
     *      <br>
     *      <b>false</b> 认证失败
     */
    public boolean contains(String authorization){
        return authorizations.contains(authorization);
    }
    
    /**
     * 解析http basic authentication表达式
     * @param users http basic authentication表达式
     * @return http basic authentication认证模型实例
     */
    public static BasicAuth parseBasicAuth(String users){
        try {
            /**
             * 解析授权
             */
            String[] _users = users.split(",");
            List<String> authorizations = new ArrayList<String>();
            String authorization;
            byte[] bytes;
            for(String user : _users){
                bytes = user.getBytes(ResourceManager.core().getCharset());
                bytes = Base64.encodeBase64(bytes);
                authorization = new String(bytes, ResourceManager.core().getCharset());
                authorizations.add(authorization);
            }
            
            return new BasicAuth(authorizations);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
    
    @Override
    public String toString() {
        return this.authorizations.toString();
    }
    
}

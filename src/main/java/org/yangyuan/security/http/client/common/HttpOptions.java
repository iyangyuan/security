package org.yangyuan.security.http.client.common;

import java.util.HashMap;
import java.util.Map;

import org.yangyuan.security.config.ResourceManager;

/**
 * HTTP客户端通用请求配置
 * @author yangyuan
 *
 */
public abstract class HttpOptions {
    
    /**
     * 自定义请求头域
     * <p><b>不建议用此方法定义Content-Type域</b></p>
     * <p>定义Content-Type域请用getContentType方法</p>
     * @return
     */
    public Map<String, String> getHeaders(){
        return new HashMap<String, String>();
    }
    
    /**
     * 自定义请求体媒体类型
     * <p>本方法会被getHeaders方法中定义的Content-Type域覆盖</p>
     * @return
     */
    public String getContentType(){
        return "text/plain; charset=utf-8";
    }
    
    /**
     * 自定义请求体编码
     * @return
     */
    public String getRequestBodyCharset(){
        return ResourceManager.core().getCharset();
    }
    
    /**
     * 自定义响应体编码
     * @return
     */
    public String getResponseBodyCharset(){
        return ResourceManager.core().getCharset();
    }
    
}

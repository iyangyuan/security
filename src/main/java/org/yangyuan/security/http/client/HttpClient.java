package org.yangyuan.security.http.client;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.yangyuan.security.http.client.common.AbstractHttpClient;

/**
 * 
 * 普通通用HTTP客户端
 * <p><b>普通</b>的含义即：非HTTP SSL客户端</p>
 * @author yangyuan
 * 
 */
public class HttpClient extends AbstractHttpClient{
    /**
     * 核心请求对象
     */
    private CloseableHttpClient httpclient;
    
    private static final HttpClient CLIENT = new HttpClient();
    
    private HttpClient(){
        /**
         * 请求配置
         */
        RequestConfig globalConfig = RequestConfig.
                                        custom().
                                        setCookieSpec(CookieSpecs.DEFAULT).
                                        setSocketTimeout(10000).
                                        setConnectTimeout(20000).
                                        setConnectionRequestTimeout(20000).
                                        build();
        /**
         * cookie容器
         */
        CookieStore cookieStore = new BasicCookieStore();
        
        /**
         * 核心请求对象
         */
        httpclient = HttpClients.
                        custom().
                        setDefaultRequestConfig(globalConfig).
                        setDefaultCookieStore(cookieStore).
                        build();
    }
    
    @Override
    protected CloseableHttpClient getCloseableHttpClient() {
        return httpclient;
    }
    
    public static HttpClient getClient(){
        return CLIENT;
    }
    
}

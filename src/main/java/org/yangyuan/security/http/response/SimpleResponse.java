package org.yangyuan.security.http.response;

import java.nio.charset.Charset;

import org.apache.http.Header;

/**
 * 通用HTTP响应封装
 * @author 杨元
 *
 */
public class SimpleResponse {
    
    /**
     * 响应状态码
     */
    private int code;
    
    /**
     * 响应体编码
     */
    private String charset;
    
    /**
     * 响应内容
     */
    private byte[] body;
    
    /**
     * 响应头信息
     */
    private Header[] headers;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取响应体原始字节数组
     * @return
     */
    public byte[] getByteBody() {
        return body;
    }
    
    /**
     * 获取响应体编码后的字符串
     * @return
     */
    public String getStringBody() {
        if(body == null){
            return "";
        }
        
        return new String(body, Charset.forName(charset));
    }
    
    public void setBody(byte[] body) {
        this.body = body;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    /**
     * 设置响应体编码
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }
}

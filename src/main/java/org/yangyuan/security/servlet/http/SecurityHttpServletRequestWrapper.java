package org.yangyuan.security.servlet.http;

import org.yangyuan.security.config.ResourceManager;
import org.yangyuan.security.util.SecurityHttpUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 封装body可多次读取的请求
 * @Auther: yangyuan
 * @Date: 2019/5/30 10:18
 */
public class SecurityHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    private byte[] body;

    public SecurityHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
        setBody(SecurityHttpUtil.readBytes(request));
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] bytes) {
        this.body = bytes;
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            ByteArrayInputStream bis = new ByteArrayInputStream(body);
            @Override
            public int read() throws IOException {
                return bis.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String charset = request.getCharacterEncoding() == null ? ResourceManager.core().getCharset() : request.getCharacterEncoding();
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(body), charset));
    }
}

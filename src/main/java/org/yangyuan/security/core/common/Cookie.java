package org.yangyuan.security.core.common;

/**
 * security cookie定义
 * @author yangyuan
 * @date 2018年3月5日
 */
public interface Cookie {
    /**
     * 转换为Http协议标准Cookie
     * @return Http协议标准Cookie
     */
    javax.servlet.http.Cookie toHttpCookie();
}

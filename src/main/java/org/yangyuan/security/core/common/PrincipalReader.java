package org.yangyuan.security.core.common;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * 客户端principal解析器
 *
 * @Auther: yangyuan
 * @Date: 2019/5/29 16:14
 */
public interface PrincipalReader {
    /**
     * 封装
     * @param request 请求对象
     * @return 请求对象封装
     */
    HttpServletRequest wrap(HttpServletRequest request);

    /**
     * 读取principal
     * @param request 请求对象
     * @return principal，未找到返回null
     */
    String read(HttpServletRequest request);
}

package org.yangyuan.security.util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * http工具类
 * @Auther: yangyuan
 * @Date: 2019/5/30 10:33
 */
public class SecurityHttpUtil {

    /**
     * 读取请求体二进制数据
     * @param request
     * @return
     */
    public static byte[] readBytes(HttpServletRequest request) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ServletInputStream input = request.getInputStream();
            if(input == null) {
                return new byte[0];
            }
            int len = 0;
            byte[] buffer = new byte[8*1024];
            while((len=input.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.yangyuan.security.util;

import java.security.MessageDigest;

import org.yangyuan.security.config.ResourceManager;

/**
 * MD5摘要工具
 * @author yangyuan
 * @date 2017年5月4日
 */
public class SecurityMD5 {
    /**
     * 计算MD5
     * @param text 文本
     * @return MD5摘要十六进制小写字符串表示
     */
    public static String encode(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String md5 = SecurityHexUtil.byteArrayToHexString(md.digest(text.getBytes(ResourceManager.core().getCharset())));
            
            return md5;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 计算MD5
     * @param bytes 字节数组
     * @return MD5摘要十六进制小写字符串表示
     */
    public static String encode(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String md5 = SecurityHexUtil.byteArrayToHexString(md.digest(bytes));
            
            return md5;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 计算MD5
     * @param bytes 字节数组
     * @return MD5摘要原始字节数组
     */
    public static byte[] simpleEncode(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            return md.digest(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package org.yangyuan.security.util;

/**
 * 十六进制工具类
 * @author yangyuan
 * @date 2018年4月8日
 */
public class SecurityHexUtil {
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};
    
    /**
     * 转换字节数组为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder(64);
        for(byte b : bytes) {
            builder.append(byteToHexString(b));
        }
        
        return builder.toString();
    }
    
    /**
     * 转换byte到十六进制
     * @param b 要转换的byte
     * @return 十六进制字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if(n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}

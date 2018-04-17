package org.yangyuan.security.core.common;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.yangyuan.security.util.SecurityHexUtil;
import org.yangyuan.security.util.SecurityMD5;


/**
 * 密码加密管理器
 * @author yangyuan
 * @date 2017年4月26日
 */
public class PasswordManager {
    /**
     * hash算法
     */
    public static final String DEFAULT_HASH_ALGORITHM = "SHA-256";
    /**
     * 编码
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";
    
    /**
     * 动态加密
     * @param text 明文
     * @return 小写形式的66位字符串
     */
    public static String encrypt(String text){
        int factor = RandomUtils.nextInt(10, 65);
        return encrypt(text, factor);
    }
    
    /**
     * 核心加密算法
     * @param text 明文
     * @param factor 加密因子。范围[10, 64]
     * @return 小写形式的66位字符串
     */
    private static String encrypt(String text, int factor){
        /**
         * md5
         * 此步骤建议在自己的真实项目中移除，因为它会弱化密码安全性。作者加上这步是为了项目兼容性升级
         */
        text = SecurityMD5.encode(text);
        
        /**
         * hash
         */
        String sha256 = sha256(text);
        
        /**
         * reverse
         */
        byte[] bytes = sha256.getBytes(Charset.forName(DEFAULT_CHARSET));
        int start = factor % 10;
        int end = factor;
        ArrayUtils.reverse(bytes, start, end);
        
        /**
         * xor
         */
        byte _factor = (byte) factor;
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = (byte) (bytes[i] ^ _factor);
        }
        
        /**
         * hex
         */
        String cert = SecurityHexUtil.byteArrayToHexString(bytes);
        
        /**
         * hash
         */
        cert = sha256(cert);
        
        /**
         * factor
         */
        cert = cert + factor;
        
        return cert.toLowerCase();
    }
    
    /**
     * 匹配凭证
     * @param text 明文
     * @param cert 凭证
     * @return
     */
    public static boolean matches(String text, String cert){
        int factor = Integer.parseInt(cert.substring(cert.length() - 2, cert.length()));
        String _cert = encrypt(text, factor);
        
        return _cert.equals(cert.toLowerCase());
    }
    
    /**
     * SHA-256实现
     * @param text 文本
     * @return 摘要
     */
    private static String sha256(String text){
        try {
            /**
             * 创建SHA-256加密对象
             */
            MessageDigest messageDigest = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
            
            /**
             * 计算摘要
             */
            messageDigest.update(text.getBytes(Charset.forName(DEFAULT_CHARSET)));
            byte[] bytes = messageDigest.digest();
            StringBuilder builder = new StringBuilder(128);
            for (int i = 0; i < bytes.length; i++){
              String hex = Integer.toHexString(0xff & bytes[i]);
              if (hex.length() == 1){
                  builder.append('0');
              }
              builder.append(hex);
            }
            
            return builder.toString();
        }  catch (NoSuchAlgorithmException e)  {
            throw new RuntimeException(e);
        }
    }
    
}

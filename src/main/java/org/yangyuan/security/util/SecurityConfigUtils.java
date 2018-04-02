package org.yangyuan.security.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.yangyuan.security.config.CoreResource;

/**
 * 配置工具
 * @author yangyuan
 * @date 2018年3月30日
 */
public class SecurityConfigUtils {
    private static final Properties PROPERTIES = new Properties();
    
    static {
        SecurityConfigUtils.init();
    }
    
    /**
     * 初始化配置服务
     */
    private static void init(){
        InputStream is = null;
        try{
            is = new FileInputStream(CoreResource.APP_CLASS_PATH + "security.properties");
            PROPERTIES.load(is);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (Exception e) {}
                is = null;
            }
        }
    }
    
    /**
     * 读取配置文件
     * @param name 
     * @return
     */
    public static String cell(String name){
        String result = PROPERTIES.getProperty(name);
        
        if(StringUtils.isBlank(result)){
            throw new RuntimeException("name not found");
        }
        
        return result;
    }
    
    /**
     * 读取配置文件, int值
     * @param name
     */
    public static int cellInt(String name){
        return Integer.parseInt(cell(name));
    }
    
    /**
     * 读取配置文件, long值
     * @param name
     */
    public static long cellLong(String name){
        return Long.parseLong(cell(name));
    }
    
    /**
     * 读取配置文件, double值
     * @param name
     */
    public static double cellDouble(String name){
        return Double.parseDouble(cell(name));
    }
    
    /**
     * 读取配置文件, boolean值
     * @param name
     */
    public static boolean cellBoolean(String name){
        return Boolean.parseBoolean(cell(name));
    }
    
    /**
     * 读取配置文件, string值
     * @param name
     */
    public static String cellString(String name){
        return cell(name);
    }
    
}

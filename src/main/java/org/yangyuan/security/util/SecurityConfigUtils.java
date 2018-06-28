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
    /**
     * 配置容器
     */
    private static final Properties PROPERTIES = new Properties();
    /**
     * 配置文件路径
     */
    private static final String PROPERTIES_FILE_PATH = CoreResource.APP_CLASS_PATH + "security.properties";
    
    static {
        SecurityConfigUtils.init();
    }
    
    /**
     * 初始化配置服务
     */
    private static void init(){
        InputStream is = null;
        try{
            is = new FileInputStream(PROPERTIES_FILE_PATH);
            PROPERTIES.load(is);
        } catch(Exception e) {
            throw new SecurityException(e);
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
     * 读取配置
     * @param name 配置名称
     * @return 配置值
     */
    public static String cell(String name){
        String result = PROPERTIES.getProperty(name);
        
        if(StringUtils.isBlank(result)){
            throw new SecurityException("can't find name[" + name + "] from config[" + PROPERTIES_FILE_PATH + "]!");
        }
        
        return result;
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置int值
     */
    public static int cellInt(String name){
        return Integer.parseInt(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置long值
     */
    public static long cellLong(String name){
        return Long.parseLong(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置double值
     */
    public static double cellDouble(String name){
        return Double.parseDouble(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置boolean值
     */
    public static boolean cellBoolean(String name){
        return Boolean.parseBoolean(cell(name));
    }
    
    /**
     * 读取配置
     * @param name 配置名称
     * @return 配置string值
     */
    public static String cellString(String name){
        return cell(name);
    }
    
}

package org.yangyuan.security.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * 认证表达式模型
 * @author yangyuan
 * @date 2018年6月13日
 */
public class Permission {
    /**
     * 表达式值开始标记
     */
    private static final char PERMISSION_VALUE_PREFIX = '[';
    
    /**
     * 表达式值结束标记
     */
    private static final char PERMISSION_VALUE_SUFFIX = ']';
    
    /**
     * 表达式名称
     */
    private final String name;
    
    /**
     * 表达式值
     */
    private final String value;
    
    public Permission(String name, String value){
        this.name = name;
        this.value = value;
    }
    
    /**
     * 获取表达式名称
     * @return 表达式名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 获取表达式值
     * @return 表达式值
     */
    public String getValue() {
        return value;
    }
    
    /**
     * 获取认证表达式字符串
     * @return 认证表达式字符串
     */
    public String getPermission() {
        if(StringUtils.isBlank(getValue())){
            return getName();
        }
        
        return getName() + PERMISSION_VALUE_PREFIX + getValue() + PERMISSION_VALUE_SUFFIX;
    }
    
    @Override
    public String toString() {
        return this.getPermission();
    }
    
    /**
     * 解析认证表达式
     * @param permission 认证表达式字符串
     * @return 认证表达式模型
     */
    public static Permission valueOf(String permission){
        /**
         * 格式规范
         */
        permission = permission.replace(" ", "");
        
        int offsetPrefix = -1;
        int offsetSuffix = -1;
        
        /**
         * 定位
         */
        for(int i = 0; i < permission.length(); i++){
            if(permission.charAt(i) == PERMISSION_VALUE_PREFIX){
                offsetPrefix = i;
                continue;
            }
            if(permission.charAt(i) == PERMISSION_VALUE_SUFFIX){
                offsetSuffix = i;
                break;
            }
        }
        
        /**
         * 有效性预测
         */
        if(offsetPrefix * offsetSuffix == 0){  //缺少表达式名称，例如：[xxx]
            throw new SecurityException("permission missing name");
        }
        if(offsetPrefix * offsetSuffix < 0){  //缺少值开始或结束标记，例如：aa[bb、aabb]、aa]bb[
            throw new SecurityException("permission missing '[' or ']'");
        }
        if(offsetSuffix - offsetPrefix == 1){  //缺少表达式值，例如：aa[]。如果不需要表达式值，直接写aa即可。
            throw new SecurityException("permission missing value");
        }
        
        /**
         * 取值
         */
        String name = permission;
        String value = "";
        if(offsetPrefix > 0){
            name = permission.substring(0, offsetPrefix);
            value = permission.substring(offsetPrefix + 1, offsetSuffix);
        }
        
        return new Permission(name, value);
    }
    
}

package org.yangyuan.security.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.yangyuan.security.exception.SecurityFilterErrorException;
import org.yangyuan.security.exception.common.FilterException;
import org.yangyuan.security.filter.common.SecurityFilter;
import org.yangyuan.security.spring.SecuritySpringHook;
import org.yangyuan.security.util.SecuritySort;

/**
 * 安全过滤器管理器
 * @author yangyuan
 * @date 2017年4月26日
 */
public class SecurityFilterManager {
    /**
     * 过滤器链
     */
    private static final List<SecurityFilter> FILTERS_CHAIN;
    
    static {
        /**
         * 加载过滤器
         */
        Map<String, SecurityFilter> filters = SecuritySpringHook.getBeansOfType(SecurityFilter.class);
        Set<Entry<String, SecurityFilter>> entrySet = filters.entrySet();
        Iterator<Entry<String, SecurityFilter>> iterator = entrySet.iterator();
        Entry<String, SecurityFilter> entry;
        List<SecuritySort> sorts = new ArrayList<SecuritySort>();
        FILTERS_CHAIN = new ArrayList<SecurityFilter>();
        while(iterator.hasNext()){  //获取所有过滤器
            entry = iterator.next();
            sorts.add(new SecuritySort(getIndex(entry.getKey()), entry.getValue(), SecuritySort.ORDER_ASC));
        }
        Collections.sort(sorts);  //排序
        SecurityFilter filter;
        for(SecuritySort sort : sorts){  //组装过滤器链
            filter = sort.getValue();
            FILTERS_CHAIN.add(filter);
        }
    }
    
    /**
     * 获取过滤器排序序号
     * @param name 过滤器名称
     * @return 排序序号，如果过滤器名称中未指定顺序，默认为0
     */
    private static int getIndex(String name){
        if(name.indexOf("/") < 0){
            return Integer.MAX_VALUE;
        }
        String[] fragments = name.split("/");
        if(fragments.length < 2){
            return Integer.MAX_VALUE;
        }
        
        return Integer.parseInt(fragments[1]);
    }
    
    /**
     * 调用过滤器链
     * @param permission 认证表达式
     * @param request http请求对象
     * @throws FilterException 安全过滤器相关异常，具体含义参考子类定义
     */
    public static void doFilter(String permission, HttpServletRequest request) throws FilterException{
        for(SecurityFilter filter : FILTERS_CHAIN){
            if(filter.approve(permission)){
                filter.doFilter(permission, request);
                return;
            }
        }
        
        throw new SecurityFilterErrorException("permission express: " + permission + ", none security filter can execute");
    }
    
    /**
     * 过滤器链视图
     * @return
     */
    public static String view(){
        StringBuilder builder = new StringBuilder(256);
        builder.append("[root]");
        for(SecurityFilter filter : FILTERS_CHAIN){
            builder.append("->[");
            builder.append(filter.getClass().getName());
            builder.append("]");
        }
        
        return new String(builder);
    }
    
    @Override
    public String toString() {
        return view();
    }
    
}

package org.yangyuan.security.spring;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.yangyuan.security.core.DefaultCacheManager;
import org.yangyuan.security.dao.RedisSessionDao;

/**
 * spring 钩子
 * @author yangyuan
 * @date 2018年4月13日
 */
@Component
public class SecuritySpringHook implements ApplicationContextAware, ApplicationListener<ContextClosedEvent>{
    private static final Log log = LogFactory.getLog(SecuritySpringHook.class);
    
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SecuritySpringHook.applicationContext = applicationContext;
    }
    
    /**
     * 系统关闭监听
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        RedisSessionDao.gcStop();
        DefaultCacheManager.destroy();
    }
    
    /**
     * 根据bean类型获取spring ioc管理的bean实例
     * @param type bean类型，class或interface
     * @return bean实例集合
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type){
        return applicationContext.getBeansOfType(type);
    }
    
    /**
     * 根据bean名称获取spring ioc管理的bean实例
     * @param name bean名称
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        T t = null;
        try {
            t = (T) applicationContext.getBean(name);
        } catch (Exception e) {
            log.warn("can't load bean[" + name + "] from spring ioc container!", e);
        }
        return t;
    }
}

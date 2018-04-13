package org.yangyuan.security.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.yangyuan.security.dao.RedisSessionDao;

/**
 * spring 钩子
 * @author yangyuan
 * @date 2018年4月13日
 */
@Component
public class SecuritySpringHook implements ApplicationContextAware, ApplicationListener<ContextClosedEvent>{
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
    }
    
    /**
     * 根据bean名称获取spring ioc 管理的 bean实例
     * @param name bean名称
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        T t = (T) applicationContext.getBean(name);
        if(t == null){
            throw new RuntimeException("bean[" + name + "] not found");
        }
        return t;
    }
}

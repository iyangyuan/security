package org.yangyuan.security.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yangyuan.security.servlet.SecurityInterceptor;

/**
 * 将security组件加入到Spring MVC拦截器链中
 */
@Configuration
public class SecurityWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}

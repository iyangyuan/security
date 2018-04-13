package org.yangyuan.security.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 安全认证过滤器注解
 * <br>
 * 任何过滤器实现类，只需配置此注解即可生效
 * <br>
 * 此注解的value值可以用来对过滤器进行排序，非必填，如果不指定value值，顺序随机，指定了value值的过滤器，一定排在未指定value值的过滤器之前
 * <h4>排序表达式说明：</h4>
 * <br>
 * <b>index/?</b>，“?”代表排序序号，例如：<b>index/123</b>
 * <br>
 * 排序序号为大于<b>100</b>的整数，<b>不可重复</b>，序号数值<b>越大</b>过滤器<b>越靠后</b>
 * @author yangyuan
 * @date 2018年4月13日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface SecurityFilterComponent {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";
}

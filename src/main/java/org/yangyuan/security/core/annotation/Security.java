package org.yangyuan.security.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 安全认证注解
 * <p>此注解配置在controller层中，配合spring mvc controller实现安全认证</p>
 * <p>如果controller方法不配置此注解，默认此方法可以被随意访问，无任何限制</p>
 * <ul>
 *  <li>
 *      <p><b>匿名访问配置(anon)：</b>@Security(permission = "anon")</p>
 *      <p>效果等同于没有配置Security注解</p>
 *  </li>
 *  <li>
 *      <p><b>基础认证配置(authc)：</b>@Security(permission = "authc")</p>
 *      <p>只要登录，即可自由访问</p>
 *  </li>
 *  <li>
 *      <p><b>角色认证配置(roles)：</b>@Security(permission = "roles[admin, &lt;vip{5}, vip{9}]")</p>
 *      <p>角色认证第一步检查是否登录，然后才会验证角色是否匹配</p>
 *      <p>角色认证支持范围表达，目前支持&lt;(小于)、&gt;(大于)两种操作符，使用范围操作符时，必须在角色名称后指定级别，花括号(name{?})中的数字，即级别</p>
 *      <p>roles中的角色可以配置多个，以英文逗号分隔，为并列关系，即逻辑“或”</p>
 *      <p>本示例表达式代表的含义为：任何拥有[admin角色]或者[拥有vip角色并且级别为9]或者[拥有vip角色并且级别小于5]的用户，均可通过认证，满足任何一个或多个条件均可</p>
 *  </li>
 *  <li>
 *      <p><b>HTTP BASIC认证配置(basic)：</b>@Security(permission = "basic[username1:password1, username2:password2]")</p>
 *      <p>HTTP基础认证实现</p>
 *      <p>以英文冒号分隔用户名和密码</p>
 *      <p>支持配置多用户，以英文逗号分隔</p>
 *  </li>
 * </ul>
 * @author yangyuan
 * @date 2017年4月26日
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {
    /**
     * 
     * 认证表达式
     * 
     * @return
     */
    String permission() default "";
}

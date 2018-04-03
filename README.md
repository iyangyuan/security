## 简介

本框架基于`Spring MVC`开发，是一款轻量级的安全认证框架。

抛弃`Shiro`、`Spring Security`等安全框架繁琐的配置，改为注解实现权限管理，配合`Spring MVC`的`RequestMapping`注解，完美实现细粒度的权限控制。

本框架以`Redis`作为持久化数据库，`Ehcache`作为内存级缓存，满足高性能需求。

本框架删繁就简，以角色作为权限认证的唯一标准，并非传统的`RBAC`权限模型，在这里没有权限的概念，只有角色，角色就是权限，权限就是角色，因此本框架适合应用于互联网项目，不适合传统项目。

目前实现*匿名认证*、*基础的登陆认证*、*基于角色的权限管理*、*支持基于范围表达式的权限管理*、*第三方登陆*。

## 主要依赖

* Spring MVC，基础依赖
* Httpclient，第三方登陆依赖
* FastJson，序列化依赖
* Ehcache，缓存依赖
* Redis，持久化依赖

## 集成

> 添加Maven项目依赖

```
<!-- security frame work -->
<dependency>
    <groupId>org.yangyuan</groupId>
    <artifactId>security</artifactId>
    <version>0.0.1</version>
</dependency>
```

> 与Spring MVC集成

```
<!-- 扫描spring注解 -->
<context:component-scan base-package="com.yourself, org.yangyuan.security" />
<!-- 身份认证拦截器 -->
<mvc:interceptors>
    <bean class="org.yangyuan.security.servlet.SecurityInterceptor"></bean>
</mvc:interceptors>
```

> 添加配置文件

将项目中的`ehcache.xml`、`security.properties`文件拷贝到项目`resources`根目录下，与`log4j.properties`位置相同，即保证编译后这两个文件在`classes`目录下。

> ehcache.xml说明

`Ehcache`用来做服务端`session`缓存，只需要关注文件中的如下配置：

```
<cache name="securitySessionCache"
    maxElementsInMemory="10000"
    eternal="false"
    overflowToDisk="false"
    timeToIdleSeconds="7200"
    timeToLiveSeconds="2588400"
    memoryStoreEvictionPolicy="LRU"
    diskPersistent="false"/>
```

一般来讲，我们只关心`maxElementsInMemory`、`timeToIdleSeconds`、`timeToLiveSeconds`这三项即可。

* **maxElementsInMemory**，缓存Session最大数量，视内存配置和用户日活而定。
* **timeToIdleSeconds**，缓存有效期，超过设置的时间不活跃，则进行清理，单位s，取值一定要小于`timeToLiveSeconds`。这个值取决于用户平均活跃时间，如果设置太长，浪费内存；如果设置太短，则会造成缓存命中率下降。
* **timeToLiveSeconds**，缓存最大有效期，单位s，取值一定要小于`security.properties`中的`session.expiresMilliseconds`的二分之一，防止缓存一直有效，而持久化层的Session得不到访问，造成用户不活跃的假象，最终导致Session丢失。

>security.properties说明

```
#Session有效期，单位s，这是一个相对值，相对于用户最后一次访问的时间
#也就是说，只有当用户超过此时间不活跃，Session才会失效
session.expiresMilliseconds=2592000000
session.gc.open=true
session.gc.script=for i=48,83,1 do     local partition     if(i > 57) then         partition = string.char(i + 39)     else         partition = string.char(i)     end          local setkey = 'security:session:set:'..partition     local principals = redis.call('ZRANGEBYSCORE', setkey, '-inf', ARGV[1])     redis.call('ZREMRANGEBYSCORE', setkey, '-inf', ARGV[1])     if(principals and (table.maxn(principals) > 0)) then         for ii,vv in ipairs(principals) do             local hashkey = 'security:session:hash:'..partition             redis.call('HDEL', hashkey, vv)         end     end end
session.gc.gcDelaySecond=86400

#cookie名称
cookie.name=sid
#cookie域名
cookie.domain=.cospace.xyz
#cookie路径
cookie.path=/
#此配置为true时，cookie无法通过js脚本操作
cookie.http_only=true
#是否启用HTTPS
cookie.secure=true
#cookie有效期，一般不需要改动，目前设置的是最大值，相当于永不过期
#因为cookie的生命周期由服务器端维护，所以客户端不需要关心过期时间
cookie.max_age=315360000

#Redis 连接工厂
common.redisResourceFactory=cc.cospace.web.security.dao.DefaultRedisResourceFactory

core.securityManager=org.yangyuan.security.core.DefaultSecurityManager
core.principalFactory=org.yangyuan.security.core.DefaultPrincipalFactory
core.cacheManager=org.yangyuan.security.core.DefaultCacheManager
#是否复用客户端subject
#如果设为true，客户端登陆时如果携带有subject信息，那么复用此subject，不再创建新的subject
#如果设为false，则登录时忽略客户端携带的subject信息，总是创建新的subject
core.useClientSubjectLogin=false
#认证回调
core.securityAuthHandler=cc.cospace.web.security.core.DefaultSecurityAuthHandler

dao.ehcacheSessionDao=org.yangyuan.security.dao.EhcacheSessionDao
dao.redisSessionDao=org.yangyuan.security.dao.RedisSessionDao
dao.jdbcRealm=org.yangyuan.security.realm.jdbc.JdbcRealm
dao.remoteRealm=org.yangyuan.security.realm.remote.RemoteRealm
dao.jdbcSessionDao=org.yangyuan.security.dao.JdbcSessionDao
dao.remoteSessionDao=org.yangyuan.security.dao.RemoteSessionDao
#用户名密码登录适配器
dao.jdbcRealmAdaptor=userService
#第三方登录适配器
dao.remoteRealmAdaptor=userService
```

> 具体业务类实现

* common.redisResourceFactory，为框架提供Redis连接，实现`RedisResourceFactory`接口。
* core.securityAuthHandler，自定义认证结果行为，用来处理认证成功、未登录、权限不足的具体业务，实现`SecurityAuthHandler`接口。
* dao.jdbcRealmAdaptor，提供用户名/密码登陆模式必要的数据，具体出参入参参考源码注释，实现`JdbcRealmAdaptor`接口。
* dao.remoteRealmAdaptor，提供第三方登陆模式必要的数据，具体出参入参参考源码注释，实现`RemoteRealmAdaptor`接口。

`security.properties`文件中配置的所有类型，可以配置成完整类名(包名+类名)，也可以配置成`spring IOC`中中的`Bean`名称，根据业务情况自由选择。

## 使用

经过前期配置之后，使用就非常简单了！

只需要在`Controller`层使用`Security`注解即可，`Security`注解具体使用方法请参考源码注释。


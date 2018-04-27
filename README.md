## 简介

本框架基于`Spring MVC`开发，是一款轻量级的安全认证框架。

抛弃`Shiro`、`Spring Security`等安全框架繁琐的配置，改为注解实现权限管理，配合`Spring MVC`的`RequestMapping`注解，完美实现细粒度的权限控制。

本框架以`Redis`作为持久化数据库，`Ehcache`作为内存级缓存，满足高性能需求。

本框架删繁就简，以角色作为权限认证的唯一标准，并非传统的`RBAC`权限模型，在这里没有权限的概念，只有角色，角色就是权限，权限就是角色，因此本框架适合应用于互联网项目，尤其适合前后端分离模式下的后端接口。

目前实现*匿名认证*、*基础的登陆认证*、*基于角色的权限管理*、*支持基于范围表达式的权限管理*、*第三方登陆*。

本框架支持Session共享、分布式部署。

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

将本项目中的`security.properties`文件拷贝到真实项目`resources`根目录下，与`log4j.properties`位置相同，即保证编译后这个文件在`classes`目录下。

>security.properties说明

```
#Session有效期
#这是一个相对值，相对于用户最后一次访问的时间
#也就是说，只有当用户超过此时间不活跃，Session才会失效
#单位秒(s)
session.expiresMilliseconds=2592000000
#是否启用Session垃圾回收器
session.gc.open=true
#Session垃圾回收器Lua脚本
session.gc.script=for i=48,83,1 do     local partition     if(i > 57) then         partition = string.char(i + 39)     else         partition = string.char(i)     end          local setkey = 'security:session:set:'..partition     local principals = redis.call('ZRANGEBYSCORE', setkey, '-inf', ARGV[1])     redis.call('ZREMRANGEBYSCORE', setkey, '-inf', ARGV[1])     if(principals and (table.maxn(principals) > 0)) then         for ii,vv in ipairs(principals) do             local hashkey = 'security:session:hash:'..partition             redis.call('HDEL', hashkey, vv)         end     end end
#Session垃圾回收器执行时间间隔
#单位秒(s)
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



#Redis客户端连接工厂
#负责提供Redis客户端连接
common.redisResourceFactory=cc.cospace.web.security.dao.DefaultRedisResourceFactory



#安全管理器实现
core.securityManager=org.yangyuan.security.core.DefaultSecurityManager
#安全唯一标识生成器实现
core.principalFactory=org.yangyuan.security.core.DefaultPrincipalFactory
#缓存管理器实现
core.cacheManager=org.yangyuan.security.core.DefaultCacheManager
#是否复用客户端subject
#如果设为true，客户端登陆时如果携带有subject信息，那么复用此subject，不再创建新的subject
#如果设为false，则登录时忽略客户端携带的subject信息，总是创建新的subject
core.useClientSubjectLogin=false
#并发主题控制器
#[org.yangyuan.security.core.MultiportConcurrentSubjectControl]允许同一个账号同时在不同客户端登陆
#[org.yangyuan.security.core.SingleConcurrentSubjectControl]同一个账号同一时刻只能在一个客户端登陆，如果之前在其他客户端登陆过，那么之前的登陆将失效
#[org.yangyuan.security.core.RefuseConcurrentSubjectControl]同一个账号同一时刻只能在一个客户端登陆，如果之前在其他客户端登陆过，那么本次登陆将会失败，除非其他客户端主动退出登陆
core.concurrentSubjectControl=org.yangyuan.security.core.MultiportConcurrentSubjectControl
#认证回调
#此处理器用来响应认证结果(成功、失败、拒绝访问)
#具体的响应依赖于具体的业务，框架只负责通知认证结果
core.securityAuthHandler=cc.cospace.web.security.core.DefaultSecurityAuthHandler



#ehcache缓存数据访问层(缓存层)
dao.ehcacheSessionDao=org.yangyuan.security.dao.EhcacheSessionDao
#redis数据访问层(持久化层)
dao.redisSessionDao=org.yangyuan.security.dao.RedisSessionDao
#持久化数据源(用户名密码模式)
dao.jdbcRealm=org.yangyuan.security.realm.jdbc.JdbcRealm
#第三方数据源
dao.remoteRealm=org.yangyuan.security.realm.remote.RemoteRealm
#本地认证数据访问层(用户名密码模式)
dao.jdbcSessionDao=org.yangyuan.security.dao.JdbcSessionDao
#第三方登录认证数据访问层
dao.remoteSessionDao=org.yangyuan.security.dao.RemoteSessionDao
#用户名密码模式登录适配器
#此适配器实现安全认证与具体项目用户数据存储之间的解耦
dao.jdbcRealmAdaptor=userService
#第三方登录适配器
#此适配器实现安全认证与具体项目用户数据存储之间的解耦
dao.remoteRealmAdaptor=userService



#cache在内存中最多可以存放的元素的数量。
#0表示没有限制。
#如果放入cache中的元素超过这个数值，有两种可能：
#1、若overflowToDisk的属性值为true，会将cache中多出的元素放入磁盘文件中。
#2、若overflowToDisk的属性值为false，会根据memoryStoreEvictionPolicy的策略替换cache中原有的元素。
cache.maxElementsInMemory=10000
#缓存是否永驻内存。
#如果值是true，cache中的元素将一直保存在内存中，不会因为时间超时而丢失。
#因此在这个值为true的时候，timeToIdleSeconds和timeToLiveSeconds两个属性的值就不起作用了。
cache.eternal=false
#内存中的元素数量溢出是否写入磁盘。
#系统会根据标签<diskStore path="java.io.tmpdir"/>中path的值查找对应的属性值。
#如果系统的java.io.tmpdir的值是/temp，写入磁盘的文件就会放在这个文件夹下，文件的名称是cache的名称，后缀名为data。
cache.overflowToDisk=false
#是否持久化内存中的缓存到磁盘。
#当这个属性的值为true时，系统在初始化的时候会在磁盘中查找文件名为cache名称，后缀名为index的的文件，如CACHE_FUNC.index。
#这个文件中存放了已经持久化在磁盘中的cache的index，找到后把cache加载到内存。
cache.diskPersistent=false
#访问cache中元素的最大间隔时间。
#如果超过此时间cache中的某个元素没有任何访问，那么这个元素将被从cache中清除。
cache.timeToIdleSeconds=900
#cache中元素的总生存时间，cache中的某个元素从创建到消亡的时间。
#从创建开始计时，当超过这个时间，这个元素将被从cache中清除，即便是这个元素被频繁访问。
cache.timeToLiveSeconds=7200
#内存存储与释放清理策略
#LRU最近最少使用
#LFU历史访问频率最低
#FIFO先进先出
cache.memoryStoreEvictionPolicy=LRU
```

> 具体业务类实现

* common.redisResourceFactory，为框架提供Redis连接，实现`RedisResourceFactory`接口。
* core.securityAuthHandler，自定义认证结果行为，用来处理认证成功、未登录、权限不足的具体业务，实现`SecurityAuthHandler`接口。
* dao.jdbcRealmAdaptor，提供用户名/密码登陆模式必要的数据，具体出参入参参考源码注释，实现`JdbcRealmAdaptor`接口。
* dao.remoteRealmAdaptor，提供第三方登陆模式必要的数据，具体出参入参参考源码注释，实现`RemoteRealmAdaptor`接口。

`security.properties`文件中配置的所有类型，可以配置成完整类名(包名+类名)，也可以配置成`spring IOC`中的`Bean`名称，根据业务情况自由选择。

一般来讲，除非需要自己扩展框架，否则只需要实现具体业务类，然后修改一下cookie相关配置即可，其他配置项均可使用默认配置。

## 使用

经过前期配置之后，使用就非常简单了！

只需要在`Controller`层使用`Security`注解即可，`Security`注解具体使用方法请参考源码注释。

本框架只关注角色认证，而不关注角色的存储、定义，彻底实现安全认证框架与实际项目之间的解耦。

在定义角色名称时，不应该出现框架已经占用的关键字，包括：`[`、`]`、`{`、`}`、`>`、`<`、`,`，否则会引起冲突。


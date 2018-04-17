package org.yangyuan.security.config;

import java.util.regex.Pattern;

import org.yangyuan.security.core.common.CacheManager;
import org.yangyuan.security.core.common.PrincipalFactory;
import org.yangyuan.security.core.common.SecurityAuthHandler;
import org.yangyuan.security.core.common.SecurityManager;

/**
 * 核心资源定义
 * @author yangyuan
 * @date 2018年3月31日
 */
public class CoreResource {
    private static final Pattern DISK_PATTERN = Pattern.compile("^/[a-zA-Z]:/");
    
    private static final String UNIFY_CHARSET = "UTF-8";
    
    public static final String APP_CLASS_PATH;
    
    static {
        String path = CoreResource.class.getClassLoader().getResource("/").getPath();
        if(DISK_PATTERN.matcher(path).find()){
            path = path.replaceFirst("/", "");
        }
        APP_CLASS_PATH = path;
    }
    
    /**
     * 全局编码
     */
    private final String charset;
    /**
     * 应用class目录
     */
    private final String appClassPath;
    /**
     * 是否使用客户端记录的subject登陆
     * <br>
     * 如果设为true，客户端登陆时如果携带有subject信息，那么复用此subject，不再创建新的subject。
     * <br>
     * 如果设为false，则登录时忽略客户端携带的subject信息，总是创建新的subject。
     */
    private final boolean useClientSubjectLogin;
    /**
     * 安全管理器
     */
    private final SecurityManager securityManager;
    /**
     * 安全唯一标识生成器
     */
    private final PrincipalFactory principalFactory;
    /**
     * 缓存管理器
     */
    private final CacheManager cacheManager;
    /**
     * 认证回调处理器
     */
    private final SecurityAuthHandler securityAuthHandler;
    
    public CoreResource(Builder builder){
        this.charset = builder.charset;
        this.appClassPath = builder.appClassPath;
        this.useClientSubjectLogin = builder.useClientSubjectLogin;
        this.securityManager = builder.securityManager;
        this.principalFactory = builder.principalFactory;
        this.cacheManager = builder.cacheManager;
        this.securityAuthHandler = builder.securityAuthHandler;
    }
    
    public String getCharset() {
        return charset;
    }
    public String getAppClassPath() {
        return appClassPath;
    }
    public boolean isUseClientSubjectLogin() {
        return useClientSubjectLogin;
    }
    public SecurityManager getSecurityManager() {
        return securityManager;
    }
    public PrincipalFactory getPrincipalFactory() {
        return principalFactory;
    }
    public CacheManager getCacheManager() {
        return cacheManager;
    }
    public SecurityAuthHandler getSecurityAuthHandler() {
        return securityAuthHandler;
    }
    
    /**
     * 自定义核心资源构造器
     * @return
     */
    public static Builder custom(){
        return new Builder().charset(UNIFY_CHARSET)
                            .appClassPath(APP_CLASS_PATH);
    }
    
    /**
     * 核心资源构造器
     * @author yangyuan
     * @date 2018年3月31日
     */
    public static class Builder {
        /**
         * 全局编码
         */
        private String charset;
        /**
         * 应用class目录
         */
        private String appClassPath;
        /**
         * 是否使用客户端记录的subject登陆
         * <br>
         * 如果设为true，客户端登陆时如果携带有subject信息，那么复用此subject，不再创建新的subject。
         * <br>
         * 如果设为false，则登录时忽略客户端携带的subject信息，总是创建新的subject。
         */
        private boolean useClientSubjectLogin;
        /**
         * 安全管理器
         */
        private SecurityManager securityManager;
        /**
         * 安全唯一标识生成器
         */
        private PrincipalFactory principalFactory;
        /**
         * 缓存管理器
         */
        private CacheManager cacheManager;
        /**
         * 认证回调处理器
         */
        private SecurityAuthHandler securityAuthHandler;
        
        public Builder charset(String charset) {
            this.charset = charset;
            return this;
        }
        public Builder appClassPath(String appClassPath) {
            this.appClassPath = appClassPath;
            return this;
        }
        public Builder useClientSubjectLogin(boolean useClientSubjectLogin) {
            this.useClientSubjectLogin = useClientSubjectLogin;
            return this;
        }
        public Builder securityManager(SecurityManager securityManager) {
            this.securityManager = securityManager;
            return this;
        }
        public Builder principalFactory(PrincipalFactory principalFactory) {
            this.principalFactory = principalFactory;
            return this;
        }
        public Builder cacheManager(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
            return this;
        }
        public Builder securityAuthHandler(SecurityAuthHandler securityAuthHandler) {
            this.securityAuthHandler = securityAuthHandler;
            return this;
        }
        public CoreResource build(){
            return new CoreResource(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        
        builder.append("[charset](");
        builder.append(getCharset());
        builder.append(")\n");
        
        builder.append("[appClassPath](");
        builder.append(getAppClassPath());
        builder.append(")\n");
        
        builder.append("[useClientSubjectLogin](");
        builder.append(isUseClientSubjectLogin());
        builder.append(")\n");
        
        builder.append("[SecurityManager](");
        if(getSecurityManager() == null){
            builder.append("null");
        }else{
            builder.append(getSecurityManager().getClass().getName());
        }
        builder.append(")\n");
        
        builder.append("[PrincipalFactory](");
        if(getPrincipalFactory() == null){
            builder.append("null");
        }else{
            builder.append(getPrincipalFactory().getClass().getName());
        }
        builder.append(")\n");
        
        builder.append("[CacheManager](");
        if(getCacheManager() == null){
            builder.append("null");
        }else{
            builder.append(getCacheManager().getClass().getName());
        }
        builder.append(")\n");
        
        builder.append("[SecurityAuthHandler](");
        if(getSecurityAuthHandler() == null){
            builder.append("null");
        }else{
            builder.append(getSecurityAuthHandler().getClass().getName());
        }
        builder.append(")\n");
        
        return new String(builder);
    }
    
}

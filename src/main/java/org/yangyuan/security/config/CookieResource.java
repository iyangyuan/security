package org.yangyuan.security.config;

/**
 * cookie资源定义
 * @author yangyuan
 * @date 2018年3月30日
 */
public class CookieResource {
    /**
     * cookie名称
     */
    private final String name;
    /**
     * cookie域
     */
    private final String domain;
    /**
     * cookie路径
     */
    private final String path;
    /**
     * cookie可见性
     */
    private final boolean httpOnly;
    /**
     * cookie有效期
     */
    private final int maxAge;
    /**
     * cookie https适配
     */
    private final boolean secure;
    
    public CookieResource(Builder builder){
        this.name = builder.name;
        this.domain = builder.domain;
        this.path = builder.path;
        this.httpOnly = builder.httpOnly;
        this.maxAge = builder.maxAge;
        this.secure = builder.secure;
    }
    
    public String getName() {
        return name;
    }
    public String getDomain() {
        return domain;
    }
    public String getPath() {
        return path;
    }
    public boolean isHttpOnly() {
        return httpOnly;
    }
    public int getMaxAge() {
        return maxAge;
    }
    public boolean isSecure() {
        return secure;
    }
    
    /**
     * 自定义cookie资源构造器
     * @return
     */
    public static Builder custom(){
        return new Builder();
    }
    
    /**
     * cookie资源构造器
     * @author yangyuan
     * @date 2018年3月30日
     */
    public static class Builder {
        /**
         * cookie名称
         */
        private String name;
        /**
         * cookie域
         */
        private String domain;
        /**
         * cookie路径
         */
        private String path;
        /**
         * cookie可见性
         */
        private boolean httpOnly;
        /**
         * cookie有效期
         */
        private int maxAge;
        /**
         * cookie https适配
         */
        private boolean secure;
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }
        public Builder path(String path) {
            this.path = path;
            return this;
        }
        public Builder httpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }
        public Builder maxAge(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }
        public Builder secure(boolean secure) {
            this.secure = secure;
            return this;
        }
        
        /**
         * 创建cookie资源
         * @return
         */
        public CookieResource build(){
            return new CookieResource(this);
        }
    }
    
}

package org.yangyuan.security.config;

/**
 * session资源定义
 * @author yangyuan
 * @date 2018年3月31日
 */
public class SessionResource {
    /**
     * session有效期，单位毫秒
     */
    private final long expiresMilliseconds;
    /**
     * 是否启用垃圾回收
     */
    private final boolean gcOpen;
    /**
     * 垃圾回收lua脚本
     */
    private final String gcScript;
    /**
     * 垃圾回收执行周期
     */
    private final long gcDelaySecond;
    
    public SessionResource(Builder builder){
        this.expiresMilliseconds = builder.expiresMilliseconds;
        this.gcOpen = builder.gcOpen;
        this.gcScript = builder.gcScript;
        this.gcDelaySecond = builder.gcDelaySecond;
    }
    
    public long getExpiresMilliseconds() {
        return expiresMilliseconds;
    }
    public boolean isGcOpen() {
        return gcOpen;
    }
    public String getGcScript() {
        return gcScript;
    }
    public long getGcDelaySecond() {
        return gcDelaySecond;
    }
    
    /**
     * 自定义session资源构造器
     * @return
     */
    public static Builder custom(){
        return new Builder();
    }
    
    /**
     * session资源构造器
     * @author yangyuan
     * @date 2018年3月31日
     */
    public static class Builder {
        /**
         * session有效期，单位毫秒
         */
        private long expiresMilliseconds;
        /**
         * 是否启用垃圾回收
         */
        private boolean gcOpen;
        /**
         * 垃圾回收lua脚本
         */
        private String gcScript;
        /**
         * 垃圾回收执行周期
         */
        private long gcDelaySecond;
        
        public Builder expiresMilliseconds(long expiresMilliseconds) {
            this.expiresMilliseconds = expiresMilliseconds;
            return this;
        }
        public Builder gcOpen(boolean gcOpen) {
            this.gcOpen = gcOpen;
            return this;
        }
        public Builder gcScript(String gcScript) {
            this.gcScript = gcScript;
            return this;
        }
        public Builder gcDelaySecond(long gcDelaySecond) {
            this.gcDelaySecond = gcDelaySecond;
            return this;
        }
        public SessionResource build(){
            return new SessionResource(this);
        }
    }
    
}

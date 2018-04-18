package org.yangyuan.security.config;

/**
 * 缓存资源定义
 * @author yangyuan
 * @date 2018年4月18日
 */
public class CacheResource {
    /**
     * cache在内存中最多可以存放的元素的数量
     */
    private final int maxElementsInMemory;
    /**
     * 缓存是否永驻内存
     */
    private final boolean eternal;
    /**
     * 内存中的元素数量溢出是否写入磁盘
     */
    private final boolean overflowToDisk;
    /**
     * 是否持久化内存中的缓存到磁盘
     */
    private final boolean diskPersistent;
    /**
     * 访问cache中元素的最大间隔时间
     */
    private final int timeToIdleSeconds;
    /**
     * cache中元素的总生存时间，cache中的某个元素从创建到消亡的时间
     */
    private final int timeToLiveSeconds;
    /**
     * 内存存储与释放清理策略
     */
    private final String memoryStoreEvictionPolicy;
    
    private CacheResource(Builder builder){
        this.maxElementsInMemory = builder.maxElementsInMemory;
        this.eternal = builder.eternal;
        this.overflowToDisk = builder.overflowToDisk;
        this.diskPersistent = builder.diskPersistent;
        this.timeToIdleSeconds = builder.timeToIdleSeconds;
        this.timeToLiveSeconds = builder.timeToLiveSeconds;
        this.memoryStoreEvictionPolicy = builder.memoryStoreEvictionPolicy;
    }
    
    public int getMaxElementsInMemory() {
        return maxElementsInMemory;
    }
    public boolean isEternal() {
        return eternal;
    }
    public boolean isOverflowToDisk() {
        return overflowToDisk;
    }
    public boolean isDiskPersistent() {
        return diskPersistent;
    }
    public int getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }
    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }
    public String getMemoryStoreEvictionPolicy() {
        return memoryStoreEvictionPolicy;
    }
    
    /**
     * 自定义缓存资源构造器
     * @return
     */
    public static Builder custom(){
        return new Builder();
    }
    
    public static class Builder {
        /**
         * cache在内存中最多可以存放的元素的数量
         */
        private int maxElementsInMemory;
        /**
         * 缓存是否永驻内存
         */
        private boolean eternal;
        /**
         * 内存中的元素数量溢出是否写入磁盘
         */
        private boolean overflowToDisk;
        /**
         * 是否持久化内存中的缓存到磁盘
         */
        private boolean diskPersistent;
        /**
         * 访问cache中元素的最大间隔时间
         */
        private int timeToIdleSeconds;
        /**
         * cache中元素的总生存时间，cache中的某个元素从创建到消亡的时间
         */
        private int timeToLiveSeconds;
        /**
         * 内存存储与释放清理策略
         */
        private String memoryStoreEvictionPolicy;
        
        public Builder maxElementsInMemory(int maxElementsInMemory) {
            this.maxElementsInMemory = maxElementsInMemory;
            return this;
        }
        public Builder eternal(boolean eternal) {
            this.eternal = eternal;
            return this;
        }
        public Builder overflowToDisk(boolean overflowToDisk) {
            this.overflowToDisk = overflowToDisk;
            return this;
        }
        public Builder diskPersistent(boolean diskPersistent) {
            this.diskPersistent = diskPersistent;
            return this;
        }
        public Builder timeToIdleSeconds(int timeToIdleSeconds) {
            this.timeToIdleSeconds = timeToIdleSeconds;
            return this;
        }
        public Builder timeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
            return this;
        }
        public Builder memoryStoreEvictionPolicy(String memoryStoreEvictionPolicy) {
            this.memoryStoreEvictionPolicy = memoryStoreEvictionPolicy;
            return this;
        }
        
        public CacheResource build(){
            return new CacheResource(this);
        }
        
    }
    
}

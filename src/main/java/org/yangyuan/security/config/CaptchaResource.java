package org.yangyuan.security.config;

/**
 * 验证码资源定义
 * @author yangyuan
 * @date 2018年5月4日
 */
public class CaptchaResource {
    /**
     * 普通验证码有效期
     */
    private final int normalExpireSecond;
    /**
     * 普通验证码多次发送最短时间间隔
     */
    private final int normalMinIntervalSecond;
    /**
     * 图形验证码有效期
     */
    private final int imageExpireSecond;
    /**
     * 图形验证码错误统计周期
     */
    private final int imageWrongPeriodSecond;
    /**
     * 图形验证码统计周期内允许最大错误次数
     */
    private final int imagePeriodMaxWrongCount;
    
    private CaptchaResource(Builder builder){
        this.normalExpireSecond = builder.normalExpireSecond;
        this.normalMinIntervalSecond = builder.normalMinIntervalSecond;
        this.imageExpireSecond = builder.imageExpireSecond;
        this.imageWrongPeriodSecond = builder.imageWrongPeriodSecond;
        this.imagePeriodMaxWrongCount = builder.imagePeriodMaxWrongCount;
    }
    
    public int getNormalExpireSecond() {
        return normalExpireSecond;
    }

    public int getNormalMinIntervalSecond() {
        return normalMinIntervalSecond;
    }

    public int getImageExpireSecond() {
        return imageExpireSecond;
    }

    public int getImageWrongPeriodSecond() {
        return imageWrongPeriodSecond;
    }

    public int getImagePeriodMaxWrongCount() {
        return imagePeriodMaxWrongCount;
    }
    
    /**
     * 自定义验证码资源
     * @return 验证码资源构造器
     */
    public static Builder custom(){
        return new Builder();
    }
    
    public static class Builder {
        /**
         * 普通验证码有效期
         */
        private int normalExpireSecond;
        /**
         * 普通验证码多次发送最短时间间隔
         */
        private int normalMinIntervalSecond;
        /**
         * 图形验证码有效期
         */
        private int imageExpireSecond;
        /**
         * 图形验证码错误统计周期
         */
        private int imageWrongPeriodSecond;
        /**
         * 图形验证码统计周期内允许最大错误次数
         */
        private int imagePeriodMaxWrongCount;
        
        public Builder normalExpireSecond(int normalExpireSecond) {
            this.normalExpireSecond = normalExpireSecond;
            return this;
        }
        public Builder normalMinIntervalSecond(int normalMinIntervalSecond) {
            this.normalMinIntervalSecond = normalMinIntervalSecond;
            return this;
        }
        public Builder imageExpireSecond(int imageExpireSecond) {
            this.imageExpireSecond = imageExpireSecond;
            return this;
        }
        public Builder imageWrongPeriodSecond(int imageWrongPeriodSecond) {
            this.imageWrongPeriodSecond = imageWrongPeriodSecond;
            return this;
        }
        public Builder imagePeriodMaxWrongCount(int imagePeriodMaxWrongCount) {
            this.imagePeriodMaxWrongCount = imagePeriodMaxWrongCount;
            return this;
        }
        /**
         * 构造验证码资源
         * @return 验证码资源
         */
        public CaptchaResource build(){
            return new CaptchaResource(this);
        }
        
    }
    
}

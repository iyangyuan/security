package org.yangyuan.security.util;

/**
 * 排序工具
 * @author yangyuan
 * @date 2018年4月13日
 */
public class SecuritySort implements Comparable<SecuritySort>{
    /**
     * 升序排序
     */
    public static final int ORDER_ASC = 1;
    
    /**
     * 降序排序
     */
    public static final int ORDER_DESC = -1;
    
    /**
     * 分数
     */
    private final int score;
    
    /**
     * 关联对象
     */
    private final Object value;
    
    /**
     * 排序方式
     * <br>
     * 同一组数据，排序方式必须一致
     */
    private final int order;
    
    public SecuritySort(int score, Object value, int order){
        this.score = score;
        this.value = value;
        this.order = order;
    }
    
    public int getScore() {
        return score;
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) value;
    }
    
    @Override
    public int compareTo(SecuritySort securitySort) {
        if(this.getScore() > securitySort.getScore()){
            return this.order;
        }
        if(this.getScore() < securitySort.getScore()){
            return -this.order;
        }
        return 0;
    }
    
}

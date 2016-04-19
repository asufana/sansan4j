package com.github.asufana.sansanapi.model.request.params;

/**
 * 取得する一覧の開始位置（省略時：0）
 */
public class Offset {
    public static final Offset DEFAULT = new Offset(0);
    
    private final Integer value;
    
    public Offset(Integer value) {
        if (value == null || 0 > value) {
            this.value = 0;
        }
        else {
            this.value = value;
        }
    }
    
    public Integer value() {
        return value;
    }
    
    public String url() {
        return String.format("offset=%s", value);
    }
    
    /**
     * 次のoffset値
     */
    public Offset nextOffset() {
        return new Offset(value + 1);
    }
}

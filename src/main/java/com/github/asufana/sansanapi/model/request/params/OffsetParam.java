package com.github.asufana.sansanapi.model.request.params;

/**
 * 取得する一覧の開始位置（省略時：0）
 */
public class OffsetParam {
    public static final OffsetParam DEFAULT = new OffsetParam(0);
    
    private final Integer value;
    
    public OffsetParam(Integer value) {
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
    public OffsetParam nextOffset(LimitParam limit) {
        return new OffsetParam(value + limit.value());
    }
}

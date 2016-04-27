package com.github.asufana.sansanapi.model.request.params;

/**
 * 取得する一覧の上限（省略時：100）
 */
public class LimitParam {
    public static final LimitParam DEFAULT = new LimitParam(100);
    
    private final Integer value;
    
    public LimitParam(Integer value) {
        if (value == null || value > 100 || 0 >= value) {
            this.value = 100;
        }
        else {
            this.value = value;
        }
    }
    
    public String url() {
        return String.format("limit=%s", value);
    }
    
    public Integer value() {
        return value;
    }
}

package com.github.asufana.sansanapi.model.request.params;

/**
 * 対象とする所有者の範囲（省略時：All）
 */
public enum Range {
    Me,
    All;
    
    public static final Range DEFAULT = Range.All;
    
    public String url() {
        return String.format("range=%s", name().toLowerCase());
    }
}

package com.github.asufana.sansanapi.model.request.params;

/**
 * 対象とする名刺入力状態
 */
public enum Status {
    Processing,
    Completed,
    Unreadable;
    
    public String url() {
        return String.format("entryStatus=%s", name().toLowerCase());
    }
}

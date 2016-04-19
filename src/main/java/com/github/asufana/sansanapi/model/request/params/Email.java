package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 完全⼀致にて Sansan 内のメールアドレスを検索する
 */
public class Email {
    public static final Email DEFAULT = new Email(null);
    
    private final String value;
    
    public Email(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("email=%s", value);
    }
}

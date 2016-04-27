package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 完全⼀致にて Sansan 内のメールアドレスを検索する
 */
public class EmailParam {
    public static final EmailParam DEFAULT = new EmailParam(null);
    
    private final String value;
    
    public EmailParam(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("email=%s", value);
    }
}

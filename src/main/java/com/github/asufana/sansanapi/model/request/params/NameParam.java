package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 前⽅⼀致にて Sansan 内の⽒名を検索する
 */
public class NameParam {
    public static final NameParam DEFAULT = new NameParam(null);
    
    private final String value;
    
    public NameParam(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("name=%s", value);
    }
}

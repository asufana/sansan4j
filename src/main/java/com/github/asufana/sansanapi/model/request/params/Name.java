package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.apache.commons.lang3.StringUtils.trimToNull;

/**
 * 前⽅⼀致にて Sansan 内の⽒名を検索する
 */
public class Name {
    public static final Name DEFAULT = new Name(null);
    
    private final String value;
    
    public Name(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("name=%s", value);
    }
}

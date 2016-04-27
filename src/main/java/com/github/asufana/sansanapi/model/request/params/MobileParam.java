package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 部分⼀致にて Sansan 内の携帯番号を検索する
 */
public class MobileParam {
    public static final MobileParam DEFAULT = new MobileParam(null);
    
    private final String value;
    
    public MobileParam(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("mobile=%s", value);
    }
}

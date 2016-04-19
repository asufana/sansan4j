package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 部分一致にて Sansan 内の会社名を検索する
 */
public class Company {
    public static final Company DEFAULT = new Company(null);
    
    private final String value;
    
    public Company(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("companyName=%s", value);
    }
}

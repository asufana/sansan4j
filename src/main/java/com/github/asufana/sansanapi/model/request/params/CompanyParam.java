package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 部分一致にて Sansan 内の会社名を検索する
 */
public class CompanyParam {
    public static final CompanyParam DEFAULT = new CompanyParam(null);
    
    private final String value;
    
    public CompanyParam(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("companyName=%s", value);
    }
}

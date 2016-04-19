package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Sansan 内のタグID
 */
public class Tag {
    public static final Tag DEFAULT = new Tag(null);
    
    private final String value;
    
    public Tag(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("tagId=%s", value);
    }
}

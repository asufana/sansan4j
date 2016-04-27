package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Sansan 内のタグID
 */
public class TagIdParam {
    public static final TagIdParam DEFAULT = new TagIdParam(null);
    
    private final String value;
    
    public TagIdParam(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("tagId=%s", value);
    }
}

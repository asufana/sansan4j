package com.github.asufana.sansanapi.model.request.params;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Sansan 内のタグID
 */
public class TagId {
    public static final TagId DEFAULT = new TagId(null);
    
    private final String value;
    
    public TagId(String value) {
        this.value = trimToEmpty(value);
    }
    
    public String url() {
        return String.format("tagId=%s", value);
    }
}

package com.github.asufana.sansanapi.model.request.params;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Sansan 内のタグID
 */
public class TagIdsParam {
    
    public static final TagIdsParam DEFAULT = new TagIdsParam(null);
    
    private List<TagIdParam> values;
    
    public TagIdsParam(List<String> values) {
        if (values == null || values.isEmpty()) {
            this.values = Collections.emptyList();
        }
        else {
            this.values = values.stream()
                                .distinct()
                                .map(value -> new TagIdParam(value))
                                .collect(Collectors.toList());
        }
    }
    
    public String url() {
        return values.stream().map(TagIdParam::url).collect(joining("&"));
    }
}

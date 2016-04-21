package com.github.asufana.sansanapi.model.request.params;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Sansan 内のタグID
 */
public class TagIds {
    
    public static final TagIds DEFAULT = new TagIds(null);
    
    private List<TagId> values;
    
    public TagIds(List<String> values) {
        if (values == null || values.isEmpty()) {
            this.values = Collections.emptyList();
        }
        else {
            this.values = values.stream()
                                .distinct()
                                .map(value -> new TagId(value))
                                .collect(Collectors.toList());
        }
    }
    
    public String url() {
        return values.stream().map(TagId::url).collect(joining("&"));
    }
}

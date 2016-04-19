package com.github.asufana.sansanapi.model.request.params;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Sansan 内のタグID
 */
public class Tags {
    
    public static final Tags DEFAULT = new Tags(null);
    
    private List<Tag> values;
    
    public Tags(List<String> values) {
        if (values == null || values.isEmpty()) {
            this.values = Collections.emptyList();
        }
        else {
            this.values = values.stream()
                                .distinct()
                                .map(value -> new Tag(value))
                                .collect(Collectors.toList());
        }
    }
    
    public String url() {
        return values.stream().map(Tag::url).collect(joining("&"));
    }
}

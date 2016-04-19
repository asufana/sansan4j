package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.Tag;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
public class Tags extends ApiResponse {
    
    private final List<Tag> list;
    
    public Tags(@JsonProperty("hasMore") boolean hasMore,
            @JsonProperty("data") List<Tag> list) {
        super(hasMore);
        this.list = list != null && !list.isEmpty()
                ? list
                : Collections.emptyList();
    }
    
    public Integer size() {
        return list.size();
    }
    
}

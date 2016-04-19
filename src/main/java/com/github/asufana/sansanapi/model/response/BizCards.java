package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.BizCard;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
public class BizCards extends ApiResponse {
    
    private final List<BizCard> list;
    
    public BizCards(@JsonProperty("hasMore") boolean hasMore,
            @JsonProperty("data") List<BizCard> list) {
        super(hasMore);
        this.list = list != null && !list.isEmpty()
                ? list
                : Collections.emptyList();
    }
    
    public Integer size() {
        return list.size();
    }
    
}

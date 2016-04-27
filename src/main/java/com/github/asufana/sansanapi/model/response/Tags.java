package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.Tag;

import java.util.List;

/** タグ検索結果 */
public class Tags extends ApiResponse<Tag> {
    
    public Tags(@JsonProperty("hasMore") boolean hasMore,
            @JsonProperty("data") List<Tag> list) {
        super(hasMore, list);
    }
    
}

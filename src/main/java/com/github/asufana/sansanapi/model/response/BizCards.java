package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.BizCard;

import java.util.List;

/** 名刺検索結果 */
public class BizCards extends ApiResponse<BizCard> {
    
    public BizCards(@JsonProperty("hasMore") boolean hasMore,
            @JsonProperty("data") List<BizCard> list) {
        super(hasMore, list);
    }
    
}

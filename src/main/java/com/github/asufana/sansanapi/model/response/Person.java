package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.BizCard;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@ToString
public class Person extends ApiResponse {
    
    private final String id;
    private final BizCard card;
    
    public Person(@JsonProperty("item") InnerPerson innerPerson) {
        super(false);
        this.id = innerPerson.id;
        this.card = innerPerson.bizCard;
    }
    
    @Value
    @Accessors(fluent = true)
    private static class InnerPerson {
        private final String id;
        @JsonProperty("headBizCard")
        private final BizCard bizCard;
    }
    
}

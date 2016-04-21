package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.BizCard;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@Accessors(fluent = true)
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
    
    public Stream<BizCard> stream() {
        return list.stream();
    }
    
    public Stream<BizCard> filter(Predicate<BizCard> predicate) {
        return stream().filter(predicate);
    }
    
    public <T> Stream<T> map(Function<BizCard, T> function) {
        return stream().map(function);
    }
    
    public <T> Stream<T> flatMap(Function<BizCard, Stream<T>> function) {
        return stream().flatMap(function);
    }
    
    public Integer size() {
        return list.size();
    }
    
}

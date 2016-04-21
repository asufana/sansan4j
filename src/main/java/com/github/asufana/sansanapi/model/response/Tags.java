package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.Tag;
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
public class Tags extends ApiResponse {
    
    private final List<Tag> list;
    
    public Tags(@JsonProperty("hasMore") boolean hasMore,
            @JsonProperty("data") List<Tag> list) {
        super(hasMore);
        this.list = list != null && !list.isEmpty()
                ? list
                : Collections.emptyList();
    }
    
    public Stream<Tag> stream() {
        return list.stream();
    }
    
    public Stream<Tag> filter(Predicate<Tag> predicate) {
        return stream().filter(predicate);
    }
    
    public <T> Stream<T> map(Function<Tag, T> function) {
        return stream().map(function);
    }
    
    public <T> Stream<T> flatMap(Function<Tag, Stream<T>> function) {
        return stream().flatMap(function);
    }
    
    public Integer size() {
        return list.size();
    }
    
}

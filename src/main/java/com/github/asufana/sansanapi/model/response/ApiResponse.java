package com.github.asufana.sansanapi.model.response;

import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.joining;

@AllArgsConstructor
public abstract class ApiResponse<U> {
    
    private final boolean hasMore;
    
    private final List<U> records;
    
    public boolean hasNext() {
        return hasMore;
    }
    
    List<U> records() {
        return records;
    }
    
    public Integer size() {
        return records.size();
    }
    
    public String toString() {
        return records.stream()
                      .map(record -> record.toString())
                      .collect(joining("\n"));
    }
    
}

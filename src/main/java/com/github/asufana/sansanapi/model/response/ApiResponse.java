package com.github.asufana.sansanapi.model.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ApiResponse {
    
    private final boolean hasMore;
    
    public boolean hasNext() {
        return hasMore;
    }
    
}

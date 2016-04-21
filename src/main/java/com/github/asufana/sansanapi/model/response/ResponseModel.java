package com.github.asufana.sansanapi.model.response;

import com.github.asufana.sansanapi.client.ApiKey;
import com.github.asufana.sansanapi.client.HttpClient;
import com.github.asufana.sansanapi.model.request.RequestModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class ResponseModel<T extends ApiResponse> {
    
    private final ApiKey apiKey;
    
    private final RequestModel request;
    
    private final T result;
    
    public boolean hasNext() {
        return result.hasNext();
    }
    
    public ResponseModel<T> getNext() {
        return HttpClient.request(apiKey, request.getNextOffset());
    }
    
    public String toString() {
        return result.toString();
    }
    
}

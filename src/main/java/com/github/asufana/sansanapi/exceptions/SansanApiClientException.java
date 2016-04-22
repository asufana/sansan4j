package com.github.asufana.sansanapi.exceptions;

import com.github.asufana.sansanapi.model.response.Error;

public class SansanApiClientException extends RuntimeException {
    
    private Error error;
    
    public SansanApiClientException(String message) {
        super(message);
    }
    
    public SansanApiClientException(Exception e) {
        super(e);
    }
    
    public SansanApiClientException(Error error) {
        super();
        this.error = error;
    }
    
    public String toString() {
        return error != null
                ? error.toString()
                : getMessage();
    }
    
}

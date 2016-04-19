package com.github.asufana.sansanapi.exceptions;

public class SansanApiClientException extends RuntimeException {
    
    public SansanApiClientException(String message) {
        super(message);
    }
    
    public SansanApiClientException(Exception e) {
        super(e);
    }
    
}

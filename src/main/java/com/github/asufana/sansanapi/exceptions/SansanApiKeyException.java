package com.github.asufana.sansanapi.exceptions;

public class SansanApiKeyException extends RuntimeException {
    
    public static SansanApiKeyException missingApiKey() {
        return new SansanApiKeyException("API Keyが未設定です");
    }
    
    public SansanApiKeyException(String message) {
        super(message);
    }
}

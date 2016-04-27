package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

/** エラー結果 */
@Getter
@Accessors(fluent = true)
@ToString
public class Error {
    
    private final Integer statusCode;
    private final String message;
    private final List<ErrorCode> errorCodes;
    
    public Error(@JsonProperty("statusCode") Integer statusCode,
            @JsonProperty("message") String message,
            @JsonProperty("error") List<ErrorCode> errorCodes) {
        this.statusCode = statusCode;
        this.message = message;
        this.errorCodes = errorCodes;
    }
    
    public String toString() {
        return String.format("Error(statusCode=%s, message=%s, errorCodes=%s)",
                             statusCode,
                             message,
                             errorCodes().stream()
                                         .map(ErrorCode::code)
                                         .collect(Collectors.joining(", ")));
    }
    
    @Value
    @Accessors(fluent = true)
    private static class ErrorCode {
        private final String code;
    }
}

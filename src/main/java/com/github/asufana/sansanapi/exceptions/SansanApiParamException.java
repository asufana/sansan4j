package com.github.asufana.sansanapi.exceptions;

public class SansanApiParamException extends SansanApiClientException {
    
    public static SansanApiParamException invalidParam(String paramName,
                                                       String value,
                                                       String regex) {
        return new SansanApiParamException(String.format("パラメータ値が不正です。Param: %s, Value: %s, Validator: %s",
                                                         paramName,
                                                         value,
                                                         regex));
    }
    
    public SansanApiParamException(String message) {
        super(message);
    }
}

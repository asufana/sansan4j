package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.model.request.RequestModel;
import com.github.asufana.sansanapi.model.response.ApiResponse;
import com.github.asufana.sansanapi.model.response.ResponseModel;
import javaslang.control.Either;
import javaslang.control.Try;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Accessors(fluent = true)
public class ApiClient {
    
    private final ApiKey apiKey;
    
    //コンストラクタ
    public ApiClient() {
        this(ApiKey.get());
    }
    
    //コンストラクタ
    public ApiClient(@NonNull ApiKey apiKey) {
        this.apiKey = apiKey;
    }
    
    /** APIリクエスト */
    public <T extends ApiResponse> ResponseModel<T> request(@NonNull RequestModel<T> request) {
        return HttpClient.request(apiKey, request);
    }
    
    /** APIリクエスト（Tryラップ） */
    public <T extends ApiResponse> Try<ResponseModel<T>> requestWrappedTry(@NonNull RequestModel<T> request) {
        return Try.of(() -> request(request));
    }
    
    /** APIリクエスト（Eitherラップ） */
    public <T extends ApiResponse> Either<Throwable, ResponseModel<T>> requestWrappedEither(@NonNull RequestModel<T> request) {
        return requestWrappedTry(request).toEither();
    }
    
}

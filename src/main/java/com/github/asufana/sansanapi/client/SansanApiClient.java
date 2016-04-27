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
public class SansanApiClient {
    
    private final ApiKey apiKey;
    
    /** コンストラクタ */
    public SansanApiClient() {
        this(ApiKey.get());
    }
    
    /**
     * コンストラクタ
     * @param apiKey SansanAPIキー文字列
     */
    public SansanApiClient(@NonNull String apiKey) {
        this(new ApiKey(apiKey));
    }
    
    /**
     * コンストラクタ
     * @param apiKey SansanAPIキーオブジェクト
     */
    public SansanApiClient(@NonNull ApiKey apiKey) {
        this.apiKey = apiKey;
    }
    
    /**
     * APIリクエスト
     * @param request リクエストオブジェクト
     */
    public <T extends ApiResponse<U>, U> ResponseModel<T, U> request(@NonNull RequestModel<T, U> request) {
        return HttpClient.request(apiKey, request);
    }
    
    /**
     * APIリクエスト
     * @param request リクエストオブジェクト
     * @param limit 取得する一覧の上限数
     */
    public <T extends ApiResponse<U>, U> ResponseModel<T, U> request(@NonNull RequestModel<T, U> request,
                                                                     Integer limit) {
        return HttpClient.request(apiKey, request, limit);
    }
    
    /**
     * APIリクエスト（Tryラップ）
     * @param request リクエストオブジェクト
     */
    public <T extends ApiResponse<U>, U> Try<ResponseModel<T, U>> requestWrappedTry(@NonNull RequestModel<T, U> request) {
        return Try.of(() -> request(request));
    }
    
    /**
     * APIリクエスト（Tryラップ）
     * @param request リクエストオブジェクト
     * @param limit 取得する一覧の上限数
     */
    public <T extends ApiResponse<U>, U> Try<ResponseModel<T, U>> requestWrappedTry(@NonNull RequestModel<T, U> request,
                                                                                    Integer limit) {
        return Try.of(() -> request(request, limit));
    }
    
    /**
     * APIリクエスト（Eitherラップ）
     * @param request リクエストオブジェクト
     */
    public <T extends ApiResponse<U>, U> Either<Throwable, ResponseModel<T, U>> requestWrappedEither(@NonNull RequestModel<T, U> request) {
        return requestWrappedTry(request).toEither();
    }
    
    /**
     * APIリクエスト（Eitherラップ）
     * @param request リクエストオブジェクト
     * @param limit 取得する一覧の上限数
     */
    public <T extends ApiResponse<U>, U> Either<Throwable, ResponseModel<T, U>> requestWrappedEither(@NonNull RequestModel<T, U> request,
                                                                                                     Integer limit) {
        return requestWrappedTry(request, limit).toEither();
    }
    
}

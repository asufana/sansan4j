package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.model.request.RequestModel;
import com.github.asufana.sansanapi.model.response.ApiResponse;
import com.github.asufana.sansanapi.model.response.ResponseModel;
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
    public ApiClient(ApiKey apiKey) {
        this.apiKey = apiKey != null
                ? apiKey
                : ApiKey.get();
    }
    
    //APIリクエスト送信
    public <T extends ApiResponse> ResponseModel<T> request(@NonNull RequestModel<T> request) {
        return HttpClient.request(apiKey, request);
    }
    
}

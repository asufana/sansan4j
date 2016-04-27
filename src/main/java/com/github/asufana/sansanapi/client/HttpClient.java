package com.github.asufana.sansanapi.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.asufana.sansanapi.exceptions.SansanApiClientException;
import com.github.asufana.sansanapi.model.request.RequestModel;
import com.github.asufana.sansanapi.model.response.ApiResponse;
import com.github.asufana.sansanapi.model.response.Error;
import com.github.asufana.sansanapi.model.response.ResponseModel;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/** HTTPリクエストクライアント */
@Slf4j
public class HttpClient {
    
    //APIリクエスト時のUA名
    private static final String USER_AGENT_NAME = "sansan4j (org.apache.httpcomponents -> httpclient 4.5.2)";
    
    /**
     * APIリクエスト送信
     * @param apiKey SansanAPIキー
     * @param request リクエストオブジェクト
     */
    public static <T extends ApiResponse<U>, U> ResponseModel<T, U> request(@NonNull ApiKey apiKey,
                                                                            @NonNull RequestModel<T, U> request) {
        T apiResponse = request(apiKey,
                                request.requestUrl(),
                                request.responseClass());
        return new ResponseModel(apiResponse);
    }
    
    /**
     * APIリクエスト送信
     * @param apiKey SansanAPIキー
     * @param request リクエストオブジェクト
     * @param limit 取得する一覧の上限数
     */
    public static <T extends ApiResponse<U>, U> ResponseModel<T, U> request(@NonNull ApiKey apiKey,
                                                                            @NonNull RequestModel<T, U> request,
                                                                            @NonNull Integer limit) {
        ResponseModel<T, U> result = request(apiKey, request);
        while (result.hasNext() && result.size() < limit) {
            request = request.getNextOffset();
            result = result.add(request(apiKey, request));
        }
        return result;
    }
    
    /**
     * APIリクエスト送信
     * @param apiKey SansanAPIキー
     * @param request リクエストオブジェクト
     * @param responseClass 返却型
     */
    static <T extends ApiResponse> T request(@NonNull ApiKey apiKey,
                                             @NonNull HttpUriRequest request,
                                             @NonNull Class<T> responseClass) {
        //HTTPヘッダ付加
        apiKey.setHeaderTo(request);
        
        //リクエスト送信
        try (CloseableHttpClient httpClient = httpClientBuilder().build()) {
            log.info(request.toString());
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    Error error = objectMapper().readValue(response.getEntity()
                                                                   .getContent(),
                                                           Error.class);
                    throw new SansanApiClientException(error);
                }
                
                //JSON文字列のオブジェクトマッピング
                return objectMapper().readValue(response.getEntity()
                                                        .getContent(),
                                                responseClass);
            }
        }
        catch (IOException e) {
            throw new SansanApiClientException(e);
        }
    }
    
    private static HttpClientBuilder httpClientBuilder() {
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setConnectTimeout(30000)
                                                   .setConnectionRequestTimeout(30000)
                                                   .setSocketTimeout(30000)
                                                   .build();
        
        return HttpClientBuilder.create()
                                .disableAutomaticRetries()
                                .setDefaultRequestConfig(requestConfig)
                                .setUserAgent(USER_AGENT_NAME);
    }
    
    private static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                               false);
        return objectMapper;
    }
}

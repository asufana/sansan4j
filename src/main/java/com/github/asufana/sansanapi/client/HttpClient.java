package com.github.asufana.sansanapi.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.asufana.sansanapi.exceptions.SansanApiClientException;
import com.github.asufana.sansanapi.model.request.RequestModel;
import com.github.asufana.sansanapi.model.response.ApiResponse;
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

@Slf4j
public class HttpClient {
    
    private static final String USER_AGENT_NAME = "sansan4j";
    
    public static <T extends ApiResponse> ResponseModel<T> request(@NonNull ApiKey apiKey,
                                                                   @NonNull RequestModel<T> request) {
        T apiResponse = request(apiKey,
                                request.requestUrl(),
                                request.responseClass());
        return new ResponseModel(apiKey, request, apiResponse);
    }
    
    static <T extends ApiResponse> T request(@NonNull ApiKey apiKey,
                                             @NonNull HttpUriRequest request,
                                             @NonNull Class<T> responseClass) {
        apiKey.setHeaderTo(request);
        
        try (CloseableHttpClient httpClient = httpClientBuilder().build()) {
            log.info("Api Request Url: {}", request.toString());
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    throw new RuntimeException();
                }
                //log.debug(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
                
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
                                                   .setConnectTimeout(3000)
                                                   .setConnectionRequestTimeout(3000)
                                                   .setSocketTimeout(3000)
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

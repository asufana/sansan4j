package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.model.response.ApiResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface RequestModel<T extends ApiResponse> {
    
    String baseUrl = "https://api.sansan.com/v1";
    
    /** APIリクエスト生成 */
    HttpUriRequest requestUrl();
    
    /** APIレスポンスクラス */
    Class<T> responseClass();
    
    /** 次オフセットのリクエストを生成 */
    <U extends RequestModel<T>> U getNextOffset();
    
}

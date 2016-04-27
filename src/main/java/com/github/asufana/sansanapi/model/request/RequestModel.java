package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.model.response.ApiResponse;
import org.apache.http.client.methods.HttpUriRequest;

/** 検索条件オブジェクト */
public interface RequestModel<T extends ApiResponse<U>, U> {
    
    String baseUrl = "https://api.sansan.com/v1";
    
    /** APIリクエスト生成 */
    HttpUriRequest requestUrl();
    
    /** APIレスポンスクラス */
    Class<T> responseClass();
    
    /** 次オフセットのリクエストを生成 */
    <S extends RequestModel<T, U>> S getNextOffset();
    
}

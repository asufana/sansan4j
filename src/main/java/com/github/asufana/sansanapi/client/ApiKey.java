package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.exceptions.SansanApiKeyException;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.apache.http.client.methods.HttpUriRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/** APIキーオブジェクト */
@Getter
@Accessors(fluent = true)
public class ApiKey {
    private static final String API_KEY_ENV_NAME = "SANSAN_API_KEY";
    
    //SansanAPIキー文字列
    private final String apiKeyString;
    
    /**
     * コンストラクタ
     * @param apiKeyString SansanAPIキー文字列
     */
    public ApiKey(String apiKeyString) {
        if (isEmpty(apiKeyString)) {
            throw SansanApiKeyException.missingApiKey();
        }
        this.apiKeyString = apiKeyString;
    }
    
    /**
     * APIKeyの取得
     * @return APIキーオブジェクト
     */
    public static ApiKey get() {
        //環境変数から取得する
        String value = System.getenv(API_KEY_ENV_NAME);
        if (isEmpty(value)) {
            throw new SansanApiKeyException(String.format("環境変数 %s を設定してください。",
                                                          API_KEY_ENV_NAME));
        }
        return new ApiKey(value);
    }
    
    /**
     * HTTPヘッダへのAPIキー文字列登録
     * @param request HTTPリクエスト用HttpUriRequestオブジェクト
     */
    void setHeaderTo(@NonNull HttpUriRequest request) {
        request.setHeader("X-Sansan-Api-Key", apiKeyString);
    }
    
}

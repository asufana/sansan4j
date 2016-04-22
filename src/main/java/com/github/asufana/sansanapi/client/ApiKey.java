package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.exceptions.SansanApiKeyException;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.apache.http.client.methods.HttpUriRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Getter
@Accessors(fluent = true)
public class ApiKey {
    private static final String API_KEY_ENV_NAME = "SANSAN_API_KEY";
    
    private final String value;
    
    //コンストラクタ
    public ApiKey(String value) {
        if (isEmpty(value)) {
            throw SansanApiKeyException.missingApiKey();
        }
        this.value = value;
    }
    
    /**
     * APIKeyの取得
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
     * HTTPヘッダへの登録
     */
    void setHeaderTo(@NonNull HttpUriRequest request) {
        request.setHeader("X-Sansan-Api-Key", value);
    }
    
}

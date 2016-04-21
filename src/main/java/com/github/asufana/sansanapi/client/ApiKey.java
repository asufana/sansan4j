package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.exceptions.SansanApiKeyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpUriRequest;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class ApiKey {
    private static final String API_KEY_ENV_NAME = "SANSAN_API_KEY";
    
    private final String value;
    
    /**
     * APIKeyの取得
     */
    public static ApiKey get() {
        //環境変数から取得する
        String value = System.getenv(API_KEY_ENV_NAME);
        if (StringUtils.isEmpty(value)) {
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

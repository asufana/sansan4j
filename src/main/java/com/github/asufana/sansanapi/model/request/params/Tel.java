package com.github.asufana.sansanapi.model.request.params;

import com.github.asufana.sansanapi.exceptions.SansanApiParamException;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 部分⼀致にて Sansan 内の電話番号を検索する
 */
public class Tel {
    public static final Tel DEFAULT = new Tel(null);
    private static final String VALIDATOR = "^[\\d\\-]+$";
    
    private final String value;
    
    public Tel(String value) {
        this.value = trimToEmpty(value);
        
        isSatisfied();
    }
    
    private void isSatisfied() {
        if (isEmpty(this.value)) {
            return;
        }
        
        if (this.value.matches(VALIDATOR) == false) {
            throw SansanApiParamException.invalidParam("Tel",
                                                       this.value,
                                                       VALIDATOR);
        }
    }
    
    public String url() {
        return String.format("tel=%s", value);
    }
}

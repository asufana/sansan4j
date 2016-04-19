package com.github.asufana.sansanapi.model.request.params;

import com.github.asufana.sansanapi.exceptions.SansanApiParamException;

import static com.github.asufana.sansanapi.model.request.params.RegisteredTo.VALIDATOR;
import static com.github.asufana.sansanapi.model.request.params.RegisteredTo.guessYYYYMMDD;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 名刺登録⽇時
 */
public class RegisteredFrom {
    public static final RegisteredFrom DEFAULT = new RegisteredFrom(null);
    
    private final String value;
    
    public RegisteredFrom(String value) {
        this.value = guessYYYYMMDD(trimToEmpty(value));
        
        isSatisfied();
    }
    
    private void isSatisfied() {
        if (isEmpty(this.value)) {
            return;
        }
        
        if (this.value.matches(RegisteredTo.VALIDATOR) == false) {
            throw SansanApiParamException.invalidParam("RegisteredFrom",
                                                       this.value,
                                                       VALIDATOR);
        }
    }
    
    public boolean hasValue() {
        return isNotEmpty(value);
    }
    
    public String url() {
        return String.format("registeredFrom=%s", value);
    }
}

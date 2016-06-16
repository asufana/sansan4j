package com.github.asufana.sansanapi.model.request.params;

import com.github.asufana.sansanapi.exceptions.SansanApiParamException;
import org.joda.time.DateMidnight;

import static com.github.asufana.sansanapi.model.request.params.RegisteredToParam.VALIDATOR;
import static com.github.asufana.sansanapi.model.request.params.RegisteredToParam.guessYYYYMMDD;
import static com.github.asufana.sansanapi.model.request.params.RegisteredToParam.toDateFormatString;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 名刺登録⽇時
 */
public class RegisteredFromParam {
    public static final String LONGAGO = new RegisteredFromParam(toDateFormatString(new DateMidnight(2000,
                                                                                                     01,
                                                                                                     01))).toString();
    public static final String LASTWEEK = new RegisteredFromParam(toDateFormatString(new DateMidnight().minusDays(7))).toString();
    public static final String YESTERDAY = new RegisteredFromParam(toDateFormatString(new DateMidnight().minusDays(1))).toString();
    public static final String TODAY = new RegisteredFromParam(toDateFormatString(new DateMidnight())).toString();
    public static final String TOMORROW = new RegisteredFromParam(toDateFormatString(new DateMidnight().plusDays(1))).toString();
    
    private final String value;
    
    public RegisteredFromParam(String value) {
        this.value = guessYYYYMMDD(trimToEmpty(value));
        
        isSatisfied();
    }
    
    private void isSatisfied() {
        if (isEmpty(this.value)) {
            return;
        }
        
        if (this.value.matches(RegisteredToParam.VALIDATOR) == false) {
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
    
    public String toString() {
        return value;
    }
}

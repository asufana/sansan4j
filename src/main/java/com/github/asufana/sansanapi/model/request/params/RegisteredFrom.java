package com.github.asufana.sansanapi.model.request.params;

import com.github.asufana.sansanapi.exceptions.SansanApiParamException;
import org.joda.time.DateMidnight;

import static com.github.asufana.sansanapi.model.request.params.RegisteredTo.VALIDATOR;
import static com.github.asufana.sansanapi.model.request.params.RegisteredTo.guessYYYYMMDD;
import static com.github.asufana.sansanapi.model.request.params.RegisteredTo.toDateFormatString;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 名刺登録⽇時
 */
public class RegisteredFrom {
    public static final RegisteredFrom LASTWEEK = new RegisteredFrom(toDateFormatString(new DateMidnight().minusDays(7)));
    public static final RegisteredFrom YESTERDAY = new RegisteredFrom(toDateFormatString(new DateMidnight().minusDays(1)));
    public static final RegisteredFrom TODAY = new RegisteredFrom(toDateFormatString(new DateMidnight()));
    public static final RegisteredFrom TOMMOROW = new RegisteredFrom(toDateFormatString(new DateMidnight().plusDays(1)));
    
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
    
    public String toString() {
        return value;
    }
}

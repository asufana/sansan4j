package com.github.asufana.sansanapi.model.request.params;

import com.github.asufana.sansanapi.exceptions.SansanApiParamException;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * 名刺登録⽇時（省略時：現在時刻）
 */
public class RegisteredTo {
    public static final RegisteredTo TODAY = new RegisteredTo(toDateFormatString(new DateMidnight()));
    public static final RegisteredTo TOMMOROW = new RegisteredTo(toDateFormatString(new DateMidnight().plusDays(1)));
    static final String VALIDATOR_YYYYMMDD = "2[0-9]{3}\\-[0-1][0-9]\\-[0-3][0-9]";
    static final String VALIDATOR_HHMMSS = "[0-2][0-9]:[0-5][0-9]:[0-5][0-9]";
    static final String VALIDATOR = "^"
            + VALIDATOR_YYYYMMDD
            + "T"
            + VALIDATOR_HHMMSS
            + "Z$";
    
    private final String value;
    
    public RegisteredTo(String value) {
        this.value = guessYYYYMMDD(trimToEmpty(value));
        
        isSatisfied();
    }
    
    static String guessYYYYMMDD(String yyyyMMdd) {
        if (isEmpty(yyyyMMdd)) {
            return yyyyMMdd;
        }
        
        String sanitized = yyyyMMdd.replaceAll("[\\-/]", "");
        if (sanitized.matches("^2[0-9]{3}[0-1][0-9][0-3][0-9]$")) {
            DateTime dateTime = DateTimeFormat.forPattern("yyyyMMdd")
                                              .parseDateTime(sanitized);
            return toDateFormatString(dateTime);
        }
        return yyyyMMdd;
    }
    
    static String toDateFormatString(DateTime dateTime) {
        return dateTime.toString(ISODateTimeFormat.dateHourMinuteSecond())
                + "Z";
    }
    
    static String toDateFormatString(DateMidnight dateMidnight) {
        return toDateFormatString(dateMidnight.toDateTime());
    }
    
    private void isSatisfied() {
        if (isEmpty(this.value)) {
            return;
        }
        
        if (this.value.matches(VALIDATOR) == false) {
            throw SansanApiParamException.invalidParam("RegisteredTo",
                                                       this.value,
                                                       VALIDATOR);
        }
    }
    
    public String url() {
        return String.format("registeredTo=%s", value);
    }
}

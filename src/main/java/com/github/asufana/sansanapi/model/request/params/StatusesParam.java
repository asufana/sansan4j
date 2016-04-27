package com.github.asufana.sansanapi.model.request.params;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * 対象とする名刺入力状態（省略時：Processing+Completed+Unreadable）
 */
public class StatusesParam {
    
    public static final StatusesParam DEFAULT = new StatusesParam(Arrays.asList(StatusParam.Processing,
                                                                                StatusParam.Completed,
                                                                                StatusParam.Unreadable));
    
    private List<StatusParam> values;
    
    public StatusesParam(List<StatusParam> values) {
        if (values == null || values.isEmpty()) {
            this.values = DEFAULT.values;
        }
        else {
            this.values = values.stream().distinct().collect(toList());
        }
    }
    
    public String url() {
        return values.stream().map(StatusParam::url).collect(joining("&"));
    }
}

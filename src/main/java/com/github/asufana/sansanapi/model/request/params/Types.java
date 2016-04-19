package com.github.asufana.sansanapi.model.request.params;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * 対象とするタグの種別リスト（省略時：Private+Public+Shared）
 */
public class Types {
    
    public static final Types DEFAULT = new Types(Arrays.asList(Type.Shared,
                                                                Type.Public,
                                                                Type.Private));
    
    private List<Type> values;
    
    public Types(List<Type> values) {
        if (values == null || values.isEmpty()) {
            this.values = DEFAULT.values;
        }
        else {
            this.values = values.stream().distinct().collect(toList());
        }
    }
    
    public String url() {
        return values.stream().map(Type::url).collect(joining("&"));
    }
}

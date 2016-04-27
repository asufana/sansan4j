package com.github.asufana.sansanapi.model.request.params;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * 対象とするタグの種別リスト（省略時：Private+Public+Shared）
 */
public class TypesParam {
    
    public static final TypesParam DEFAULT = new TypesParam(Arrays.asList(TypeParam.Shared,
                                                                          TypeParam.Public,
                                                                          TypeParam.Private));
    
    private List<TypeParam> values;
    
    public TypesParam(List<TypeParam> values) {
        if (values == null || values.isEmpty()) {
            this.values = DEFAULT.values;
        }
        else {
            this.values = values.stream().distinct().collect(toList());
        }
    }
    
    public String url() {
        return values.stream().map(TypeParam::url).collect(joining("&"));
    }
}

package com.github.asufana.sansanapi.model.response.models;

import lombok.Value;
import lombok.experimental.Accessors;

/** 人物データ */
@Value
@Accessors(fluent = true)
public class Person {
    private final String id;
    private final BizCard headBizCard;
}

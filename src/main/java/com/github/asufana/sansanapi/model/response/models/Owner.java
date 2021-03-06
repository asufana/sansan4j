package com.github.asufana.sansanapi.model.response.models;

import lombok.Value;
import lombok.experimental.Accessors;

/** 所有者データ */
@Value
@Accessors(fluent = true)
public class Owner {
    private final String id;
    private final String name;
}

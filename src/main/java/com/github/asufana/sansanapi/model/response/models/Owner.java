package com.github.asufana.sansanapi.model.response.models;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class Owner {
    private final String id;
    private final String name;
}

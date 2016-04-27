package com.github.asufana.sansanapi.model.response.models;

import lombok.Value;
import lombok.experimental.Accessors;

/** タグデータ */
@Value
@Accessors(fluent = true)
public class Tag {
    private final String id;
    private final String name;
    private final String type;
    private final Owner owner;
}

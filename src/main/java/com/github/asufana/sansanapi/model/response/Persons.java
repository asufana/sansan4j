package com.github.asufana.sansanapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.asufana.sansanapi.model.response.models.Person;

import java.util.Arrays;

/** 人物検索結果 */
public class Persons extends ApiResponse<Person> {
    
    public Persons(@JsonProperty("item") Person person) {
        super(false, Arrays.asList(person));
    }
    
}

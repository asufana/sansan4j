package com.github.asufana.sansanapi.model.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.github.asufana.sansanapi.exceptions.SansanApiClientException;
import com.github.asufana.sansanapi.model.response.Persons;
import com.github.asufana.sansanapi.model.response.models.Person;

/** 人物検索条件 */
public class PersonRequest implements RequestModel<Persons, Person> {
    private static final String apiUrl = "/persons";
    private static final Class responseClass = Persons.class;
    
    private final String personId;
    
    //コンストラクタ
    public PersonRequest(String personId) {
        this.personId = personId;
    }
    
    /**
     * ${inheritDoc}
     */
    @Override
    public HttpUriRequest requestUrl() {
        String url = String.format("%s%s/%s", baseUrl, apiUrl, personId);
        return new HttpGet(url);
    }
    
    /**
     * ${inheritDoc}
     */
    @Override
    public Class responseClass() {
        return responseClass;
    }
    
    /**
     * ${inheritDoc}
     */
    @Override
    public PersonRequest getNextOffset() {
        throw new SansanApiClientException("Invalid request.");
    }
}

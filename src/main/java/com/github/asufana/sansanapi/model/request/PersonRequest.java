package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.exceptions.SansanApiClientException;
import com.github.asufana.sansanapi.model.response.Person;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class PersonRequest implements RequestModel<Person> {
    
    private static final String apiUrl = "/persons";
    private static final Class responseClass = Person.class;
    
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

package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.model.request.params.*;
import com.github.asufana.sansanapi.model.response.Tags;
import lombok.Builder;
import lombok.Singular;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

import static java.util.Optional.ofNullable;

@Builder
public class TagsRequest implements RequestModel<Tags> {
    public static final TagsRequest ALL = TagsRequest.builder().build();
    
    private static final String apiUrl = "/tags";
    private static final Class responseClass = Tags.class;
    
    private final Range range;
    @Singular
    private final List<Type> types;
    private final Integer limit;
    private final Integer offset;
    
    /**
     * ${inheritDoc}
     */
    @Override
    public HttpUriRequest requestUrl() {
        return new HttpGet(generateUrl());
    }
    
    String generateUrl() {
        return String.format("%s%s?%s&%s&%s&%s",
                             baseUrl,
                             apiUrl,
                             range().url(),
                             types().url(),
                             limit().url(),
                             offset().url());
    }
    
    private Range range() {
        return ofNullable(range).orElse(Range.DEFAULT);
    }
    
    private Types types() {
        return ofNullable(types).map(Types::new).orElse(Types.DEFAULT);
    }
    
    private Limit limit() {
        return ofNullable(limit).map(Limit::new).orElse(Limit.DEFAULT);
    }
    
    private Offset offset() {
        return ofNullable(offset).map(Offset::new).orElse(Offset.DEFAULT);
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
    public TagsRequest getNextOffset() {
        Integer nextOffset = offset().nextOffset().value();
        return new TagsRequest(this.range, this.types, this.limit, nextOffset);
    }
    
}

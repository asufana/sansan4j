package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.model.request.params.*;
import com.github.asufana.sansanapi.model.response.Tags;
import com.github.asufana.sansanapi.model.response.models.Tag;
import lombok.Builder;
import lombok.Singular;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

import static java.util.Optional.ofNullable;

/** タグ検索条件 */
@Builder
public class TagsRequest implements RequestModel<Tags, Tag> {
    
    /** すべてのタグ一覧を検索する */
    public static final TagsRequest ALL = TagsRequest.builder().build();
    
    private static final String apiUrl = "/tags";
    private static final Class responseClass = Tags.class;
    
    private final RangeParam range;
    @Singular
    private final List<TypeParam> types;
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
    
    private RangeParam range() {
        return ofNullable(range).orElse(RangeParam.DEFAULT);
    }
    
    private TypesParam types() {
        return ofNullable(types).map(TypesParam::new)
                                .orElse(TypesParam.DEFAULT);
    }
    
    private LimitParam limit() {
        return ofNullable(limit).map(LimitParam::new)
                                .orElse(LimitParam.DEFAULT);
    }
    
    private OffsetParam offset() {
        return ofNullable(offset).map(OffsetParam::new)
                                 .orElse(OffsetParam.DEFAULT);
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
        Integer nextOffset = offset().nextOffset(limit()).value();
        return new TagsRequest(this.range, this.types, this.limit, nextOffset);
    }
    
}

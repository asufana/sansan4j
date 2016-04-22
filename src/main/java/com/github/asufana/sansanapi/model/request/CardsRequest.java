package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.model.request.params.Company;
import com.github.asufana.sansanapi.model.request.params.Email;
import com.github.asufana.sansanapi.model.request.params.Limit;
import com.github.asufana.sansanapi.model.request.params.Mobile;
import com.github.asufana.sansanapi.model.request.params.Name;
import com.github.asufana.sansanapi.model.request.params.Offset;
import com.github.asufana.sansanapi.model.request.params.Range;
import com.github.asufana.sansanapi.model.request.params.RegisteredFrom;
import com.github.asufana.sansanapi.model.request.params.RegisteredTo;
import com.github.asufana.sansanapi.model.request.params.Status;
import com.github.asufana.sansanapi.model.request.params.Statuses;
import com.github.asufana.sansanapi.model.request.params.TagIds;
import com.github.asufana.sansanapi.model.request.params.Tel;
import com.github.asufana.sansanapi.model.response.BizCards;
import lombok.Builder;
import lombok.Singular;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

import static java.util.Optional.ofNullable;

@Builder
public class CardsRequest implements RequestModel<BizCards> {
    public static final CardsRequest TODAY = CardsRequest.builder()
                                                         .registeredFrom(RegisteredFrom.TODAY.toString())
                                                         .registeredTo(RegisteredFrom.TOMMOROW.toString())
                                                         .build();
    public static final CardsRequest YESTERDAY = CardsRequest.builder()
                                                             .registeredFrom(RegisteredFrom.YESTERDAY.toString())
                                                             .registeredTo(RegisteredFrom.TODAY.toString())
                                                             .build();
    public static final CardsRequest SINCE_YESTERDAY = CardsRequest.builder()
                                                                   .registeredFrom(RegisteredFrom.YESTERDAY.toString())
                                                                   .registeredTo(RegisteredFrom.TOMMOROW.toString())
                                                                   .build();
    public static final CardsRequest SINCE_LASTWEEK = CardsRequest.builder()
                                                                  .registeredFrom(RegisteredFrom.LASTWEEK.toString())
                                                                  .registeredTo(RegisteredFrom.TOMMOROW.toString())
                                                                  .build();
    
    private static final String apiUrl = "/bizCards/search";
    private static final Class responseClass = BizCards.class;
    
    private final String registeredFrom;
    private final String registeredTo;
    
    private final String company;
    private final String name;
    private final String email;
    private final String tel;
    private final String mobile;
    @Singular
    private final List<String> tags;
    
    private final Range range;
    private final Integer limit;
    private final Integer offset;
    @Singular
    private final List<Status> statuses;
    
    /**
     * ${inheritDoc}
     */
    @Override
    public HttpUriRequest requestUrl() {
        return new HttpGet(generateUrl());
    }
    
    String generateUrl() {
        //期間検索
        if (registeredFrom().hasValue()) {
            return String.format("%s%s?%s&%s&%s&%s&%s&%s",
                                 baseUrl,
                                 apiUrl,
                                 registeredFrom().url(),
                                 registeredTo().url(),
                                 range().url(),
                                 limit().url(),
                                 offset().url(),
                                 statuses().url());
        }
        
        //一般検索
        return String.format("%s%s?%s&%s&%s&%s&%s&%s&%s&%s&%s&%s",
                             baseUrl,
                             apiUrl,
                             company().url(),
                             name().url(),
                             email().url(),
                             tel().url(),
                             mobile().url(),
                             tags().url(),
                             range().url(),
                             limit().url(),
                             offset().url(),
                             statuses().url());
    }
    
    private RegisteredFrom registeredFrom() {
        return ofNullable(registeredFrom).map(RegisteredFrom::new)
                                         .orElse(RegisteredFrom.TODAY);
    }
    
    private RegisteredTo registeredTo() {
        return ofNullable(registeredTo).map(RegisteredTo::new)
                                       .orElse(RegisteredTo.TOMMOROW);
    }
    
    private Company company() {
        return ofNullable(company).map(Company::new).orElse(Company.DEFAULT);
    }
    
    private Name name() {
        return ofNullable(name).map(Name::new).orElse(Name.DEFAULT);
    }
    
    private Email email() {
        return ofNullable(email).map(Email::new).orElse(Email.DEFAULT);
    }
    
    private Tel tel() {
        return ofNullable(tel).map(Tel::new).orElse(Tel.DEFAULT);
    }
    
    private Mobile mobile() {
        return ofNullable(mobile).map(Mobile::new).orElse(Mobile.DEFAULT);
    }
    
    private TagIds tags() {
        return ofNullable(tags).map(TagIds::new).orElse(TagIds.DEFAULT);
    }
    
    private Range range() {
        return ofNullable(range).orElse(Range.DEFAULT);
    }
    
    private Limit limit() {
        return ofNullable(limit).map(Limit::new).orElse(Limit.DEFAULT);
    }
    
    private Offset offset() {
        return ofNullable(offset).map(Offset::new).orElse(Offset.DEFAULT);
    }
    
    private Statuses statuses() {
        return ofNullable(statuses).map(Statuses::new).orElse(Statuses.DEFAULT);
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
    public CardsRequest getNextOffset() {
        Integer nextOffset = offset().nextOffset().value();
        return new CardsRequest(this.registeredFrom,
                                this.registeredTo,
                                this.company,
                                this.name,
                                this.email,
                                this.tel,
                                this.mobile,
                                this.tags,
                                this.range,
                                this.limit,
                                nextOffset,
                                this.statuses);
    }
    
}

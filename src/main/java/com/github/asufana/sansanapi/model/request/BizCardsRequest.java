package com.github.asufana.sansanapi.model.request;

import com.github.asufana.sansanapi.model.request.params.CompanyParam;
import com.github.asufana.sansanapi.model.request.params.EmailParam;
import com.github.asufana.sansanapi.model.request.params.LimitParam;
import com.github.asufana.sansanapi.model.request.params.MobileParam;
import com.github.asufana.sansanapi.model.request.params.NameParam;
import com.github.asufana.sansanapi.model.request.params.OffsetParam;
import com.github.asufana.sansanapi.model.request.params.RangeParam;
import com.github.asufana.sansanapi.model.request.params.RegisteredFromParam;
import com.github.asufana.sansanapi.model.request.params.RegisteredToParam;
import com.github.asufana.sansanapi.model.request.params.StatusParam;
import com.github.asufana.sansanapi.model.request.params.StatusesParam;
import com.github.asufana.sansanapi.model.request.params.TagIdsParam;
import com.github.asufana.sansanapi.model.request.params.TelParam;
import com.github.asufana.sansanapi.model.response.BizCards;
import com.github.asufana.sansanapi.model.response.models.BizCard;
import lombok.Builder;
import lombok.Singular;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

import static java.util.Optional.ofNullable;

/** 名刺検索条件 */
@Builder
public class BizCardsRequest implements RequestModel<BizCards, BizCard> {
    
    /** 今日登録された名刺一覧を検索する */
    public static final BizCardsRequest TODAY = BizCardsRequest.builder()
                                                               .registeredFrom(RegisteredFromParam.TODAY.toString())
                                                               .registeredTo(RegisteredFromParam.TOMMOROW.toString())
                                                               .build();
    
    /** 昨日登録された名刺一覧を検索する */
    public static final BizCardsRequest YESTERDAY = BizCardsRequest.builder()
                                                                   .registeredFrom(RegisteredFromParam.YESTERDAY)
                                                                   .registeredTo(RegisteredFromParam.TODAY)
                                                                   .build();
    
    /** 昨日から今日までに登録された名刺一覧を検索する */
    public static final BizCardsRequest SINCE_YESTERDAY = BizCardsRequest.builder()
                                                                         .registeredFrom(RegisteredFromParam.YESTERDAY)
                                                                         .registeredTo(RegisteredFromParam.TOMMOROW)
                                                                         .build();
    
    /** 先週から今日までに登録された名刺一覧を検索する */
    public static final BizCardsRequest SINCE_LASTWEEK = BizCardsRequest.builder()
                                                                        .registeredFrom(RegisteredFromParam.LASTWEEK)
                                                                        .registeredTo(RegisteredFromParam.TOMMOROW)
                                                                        .build();
    
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
    
    private final RangeParam range;
    private final Integer limit;
    private final Integer offset;
    @Singular
    private final List<StatusParam> statuses;
    
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
            return String.format("%s/bizCards?%s&%s&%s&%s&%s&%s",
                                 baseUrl,
                                 registeredFrom().url(),
                                 registeredTo().url(),
                                 range().url(),
                                 limit().url(),
                                 offset().url(),
                                 statuses().url());
        }
        
        //一般検索
        return String.format("%s/bizCards/search?%s&%s&%s&%s&%s&%s&%s&%s&%s&%s",
                             baseUrl,
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
    
    private RegisteredFromParam registeredFrom() {
        return ofNullable(registeredFrom).map(RegisteredFromParam::new)
                                         .orElse(new RegisteredFromParam(RegisteredFromParam.TODAY));
    }
    
    private RegisteredToParam registeredTo() {
        return ofNullable(registeredTo).map(RegisteredToParam::new)
                                       .orElse(new RegisteredToParam(RegisteredToParam.TOMMOROW));
    }
    
    private CompanyParam company() {
        return ofNullable(company).map(CompanyParam::new)
                                  .orElse(CompanyParam.DEFAULT);
    }
    
    private NameParam name() {
        return ofNullable(name).map(NameParam::new).orElse(NameParam.DEFAULT);
    }
    
    private EmailParam email() {
        return ofNullable(email).map(EmailParam::new)
                                .orElse(EmailParam.DEFAULT);
    }
    
    private TelParam tel() {
        return ofNullable(tel).map(TelParam::new).orElse(TelParam.DEFAULT);
    }
    
    private MobileParam mobile() {
        return ofNullable(mobile).map(MobileParam::new)
                                 .orElse(MobileParam.DEFAULT);
    }
    
    private TagIdsParam tags() {
        return ofNullable(tags).map(TagIdsParam::new)
                               .orElse(TagIdsParam.DEFAULT);
    }
    
    private RangeParam range() {
        return ofNullable(range).orElse(RangeParam.DEFAULT);
    }
    
    private LimitParam limit() {
        return ofNullable(limit).map(LimitParam::new)
                                .orElse(LimitParam.DEFAULT);
    }
    
    private OffsetParam offset() {
        return ofNullable(offset).map(OffsetParam::new)
                                 .orElse(OffsetParam.DEFAULT);
    }
    
    private StatusesParam statuses() {
        return ofNullable(statuses).map(StatusesParam::new)
                                   .orElse(StatusesParam.DEFAULT);
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
    public BizCardsRequest getNextOffset() {
        Integer nextOffset = offset().nextOffset(limit()).value();
        return new BizCardsRequest(this.registeredFrom,
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

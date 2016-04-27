package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.exceptions.SansanApiClientException;
import com.github.asufana.sansanapi.model.request.BizCardsRequest;
import com.github.asufana.sansanapi.model.request.TagsRequest;
import com.github.asufana.sansanapi.model.request.params.RangeParam;
import com.github.asufana.sansanapi.model.request.params.TypeParam;
import com.github.asufana.sansanapi.model.response.BizCards;
import com.github.asufana.sansanapi.model.response.ResponseModel;
import com.github.asufana.sansanapi.model.response.Tags;
import com.github.asufana.sansanapi.model.response.models.BizCard;
import com.github.asufana.sansanapi.model.response.models.Tag;
import javaslang.control.Try;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SansanApiClientTest {
    
    @Test
    //取得テスト
    public void testRequest() {
        
        //APIクライアント
        SansanApiClient api = new SansanApiClient(ApiKey.get());
        
        //リクエスト生成
        TagsRequest tagRequest = TagsRequest.builder()
                                            .range(RangeParam.All)
                                            .type(TypeParam.Shared)
                                            .build();
        //リクエスト送信
        ResponseModel<Tags, Tag> tags = api.request(tagRequest);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        assertThat(tags.results(), is(notNullValue()));
        assertThat(tags.size(), is(not(0)));
    }
    
    @Test
    //取得数を指定して複数回リクエスト取得テスト
    public void testRequestWithLimit() {
        
        //Limit値を設定してリクエスト送信
        SansanApiClient api = new SansanApiClient(ApiKey.get());
        ResponseModel<BizCards, BizCard> cards = api.request(BizCardsRequest.SINCE_LASTWEEK,
                                                             200);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.results(), is(notNullValue()));
        assertThat(cards.size(), is(200));
    }
    
    @Test
    //次オフセット取得テスト
    public void testGetNext() {
        
        //APIクライアント
        SansanApiClient api = new SansanApiClient(ApiKey.get());
        
        //リクエスト送信
        TagsRequest request = TagsRequest.builder().limit(1).build();
        ResponseModel<Tags, Tag> response = api.request(request);
        
        //取得できること
        assertThat(response, is(notNullValue()));
        assertThat(response.hasNext(), is(true));
        
        //---
        
        //同一条件で次オフセットリクエスト送信
        ResponseModel<Tags, Tag> response2 = api.request(request.getNextOffset());
        
        //取得できること
        assertThat(response2, is(notNullValue()));
    }
    
    @Test
    //APIの多段呼び出し時の一元エラーハンドリング
    public void testRequestWrappedTry() {
        
        //APIクライアント
        SansanApiClient api = new SansanApiClient(ApiKey.get());
        
        //タグ抽出API
        String searchTagName = "Import";
        TagsRequest tagsRequest = TagsRequest.ALL;
        Try<ResponseModel<Tags, Tag>> tagsResponse = api.requestWrappedTry(tagsRequest);
        Try<Tag> tagResult = tagsResponse.mapTry(res -> res.filter(tag -> tag.name()
                                                                             .startsWith(searchTagName))
                                                           .findAny()
                                                           .orElseThrow(() -> new SansanApiClientException("指定タグが見つかりません："
                                                                   + searchTagName)));
        
        //名刺抽出API
        Try<List<String>> companies = tagResult.flatMap(tag -> {
            BizCardsRequest cardsRequest = BizCardsRequest.builder()
                                                          .tag(tag.id())
                                                          .build();
            Try<ResponseModel<BizCards, BizCard>> cardsResponse = api.requestWrappedTry(cardsRequest);
            return cardsResponse.mapTry(res -> res.filter(card -> isNotEmpty(card.prefecture()))
                                                  .filter(card -> card.prefecture()
                                                                      .equals("東京都"))
                                                  .map(BizCard::companyName)
                                                  .distinct()
                                                  .sorted()
                                                  .collect(Collectors.toList()));
        });
        
        //取得できたら
        if (companies.toEither().isRight()) {
            List<String> tokyoCompanies = companies.toEither().right().get();
            System.out.println(tokyoCompanies);
        }
        //途中で例外が発生していたら
        else {
            System.out.println(companies.toEither().left().get());
        }
        
    }
}

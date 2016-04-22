package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.exceptions.SansanApiClientException;
import com.github.asufana.sansanapi.model.request.CardsRequest;
import com.github.asufana.sansanapi.model.request.TagsRequest;
import com.github.asufana.sansanapi.model.request.params.Range;
import com.github.asufana.sansanapi.model.request.params.Type;
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

public class ApiClientTest {
    
    @Test
    public void testRequest() {
        
        //APIクライアント
        ApiClient api = new ApiClient(ApiKey.get());
        
        //リクエスト生成
        TagsRequest tagRequest = TagsRequest.builder()
                                            .range(Range.All)
                                            .type(Type.Shared)
                                            .build();
        //リクエスト送信
        ResponseModel<Tags> tags = api.request(tagRequest);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        assertThat(tags.result(), is(notNullValue()));
        assertThat(tags.result().size(), is(not(0)));
        
        //取得結果
        tags.result().forEach(System.out::println);
    }
    
    @Test
    public void testGetNext() {
        
        //APIクライアント
        ApiClient api = new ApiClient(ApiKey.get());
        
        //リクエスト送信
        TagsRequest request = TagsRequest.builder().limit(1).build();
        ResponseModel<Tags> response = api.request(request);
        
        //取得できること
        assertThat(response, is(notNullValue()));
        assertThat(response.hasNext(), is(true));
        
        //---
        
        //同一条件で次オフセットリクエスト送信
        ResponseModel<Tags> response2 = response.getNext();
        
        //取得できること
        assertThat(response, is(notNullValue()));
    }
    
    @Test
    //APIの多段呼び出し時の一元エラーハンドリング
    public void testRequestWrappedTry() {
        
        //APIクライアント
        ApiClient api = new ApiClient(ApiKey.get());
        
        //タグ抽出API
        String searchTagName = "Import";
        TagsRequest tagsRequest = TagsRequest.ALL;
        Try<ResponseModel<Tags>> tagsResponse = api.requestWrappedTry(tagsRequest);
        Try<Tag> tagResult = tagsResponse.mapTry(res -> res.result()
                                                           .filter(tag -> tag.name()
                                                                             .startsWith(searchTagName))
                                                           .findAny()
                                                           .orElseThrow(() -> new SansanApiClientException("指定タグが見つかりません："
                                                                   + searchTagName)));
        
        //名刺抽出API
        Try<List<String>> companies = tagResult.flatMap(tag -> {
            CardsRequest cardsRequest = CardsRequest.builder()
                                                    .tag(tag.id())
                                                    .build();
            Try<ResponseModel<BizCards>> cardsResponse = api.requestWrappedTry(cardsRequest);
            return cardsResponse.mapTry(res -> res.result()
                                                  .filter(card -> isNotEmpty(card.prefecture()))
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

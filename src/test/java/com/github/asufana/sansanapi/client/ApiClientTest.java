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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ApiClientTest {
    
    @Test
    public void testRequest() {
        
        //リクエスト生成
        TagsRequest request = TagsRequest.builder()
                                         .range(Range.All)
                                         .type(Type.Shared)
                                         .build();
        //取得
        ResponseModel<Tags> tags = new ApiClient().request(request);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        System.out.println(tags);
    }
    
    @Test
    public void testGetNext() {
        
        //リクエスト生成
        TagsRequest request = TagsRequest.builder()
                                         .range(Range.All)
                                         .type(Type.Shared)
                                         .limit(1)
                                         .build();
        //リクエスト送信
        ResponseModel<Tags> response = new ApiClient().request(request);
        
        //取得できること
        assertThat(response, is(notNullValue()));
        //続きがあること
        assertThat(response.hasNext(), is(true));
        System.out.println(response);
        
        //---
        
        //続きを取得するリクエスト送信
        ResponseModel<Tags> tags2 = response.getNext();
        
        //取得できること
        assertThat(response, is(notNullValue()));
        System.out.println(tags2);
    }
    
    @Test
    //APIの多段呼び出し時の一元エラーハンドリング
    public void testRequestWithTryAndEither() {
        ApiClient api = new ApiClient();
        
        //タグ抽出API
        String searchTagName = "Import";
        TagsRequest tagsRequest = TagsRequest.builder().build();
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
            System.out.println(companies.toEither().right().get());
        }
        //途中で例外が発生していたら
        else {
            System.out.println(companies.toEither().left().get());
        }
    }
    
}

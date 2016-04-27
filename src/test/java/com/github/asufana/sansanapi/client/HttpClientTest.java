package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.model.request.BizCardsRequest;
import com.github.asufana.sansanapi.model.request.PersonRequest;
import com.github.asufana.sansanapi.model.request.TagsRequest;
import com.github.asufana.sansanapi.model.request.params.RangeParam;
import com.github.asufana.sansanapi.model.request.params.TypeParam;
import com.github.asufana.sansanapi.model.response.BizCards;
import com.github.asufana.sansanapi.model.response.Persons;
import com.github.asufana.sansanapi.model.response.ResponseModel;
import com.github.asufana.sansanapi.model.response.Tags;
import com.github.asufana.sansanapi.model.response.models.BizCard;
import com.github.asufana.sansanapi.model.response.models.Person;
import com.github.asufana.sansanapi.model.response.models.Tag;
import javaslang.control.Either;
import javaslang.control.Try;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HttpClientTest {
    
    @Test
    //名刺期間検索
    public void testCardPeriodSearchRequest() {
        //リクエスト生成
        HttpGet request = new HttpGet("https://api.sansan.com/v1/bizCards?registeredFrom=2016-04-01T00:00:00Z&registeredTo=2026-03-31T00:00:00Z&range=all&limit=1");
        //リクエスト送信
        BizCards cards = HttpClient.request(ApiKey.get(),
                                            request,
                                            BizCards.class);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.size(), is(1));
        
        System.out.println(cards);
    }
    
    @Test
    //名刺期間検索
    public void testCardSearchPeriodRequestWithRequestModel() {
        //リクエスト生成
        BizCardsRequest request = BizCardsRequest.builder()
                                                 .registeredFrom("20160401")
                                                 .registeredTo("20260331")
                                                 .range(RangeParam.All)
                                                 .limit(1)
                                                 .build();
        //リクエスト送信
        ResponseModel<BizCards, BizCard> cards = HttpClient.request(ApiKey.get(),
                                                                    request);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.results(), is(notNullValue()));
        assertThat(cards.size(), is(1));
        
        System.out.println(cards);
    }
    
    @Test
    //名刺期間検索＋Limit設定
    public void testCardSearchPeriodRequestWithRequestModelWithLimit() {
        //リミット設定
        Integer limit = 3;
        //リクエスト生成
        BizCardsRequest request = BizCardsRequest.builder()
                                                 .registeredFrom("20160401")
                                                 .registeredTo("20260331")
                                                 .range(RangeParam.All)
                                                 .limit(1)
                                                 .build();
        //リミット設定付きでリクエスト送信
        ResponseModel<BizCards, BizCard> cards = HttpClient.request(ApiKey.get(),
                                                                    request,
                                                                    limit);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.results(), is(notNullValue()));
        assertThat(cards.size(), is(3));
        
        System.out.println(cards);
    }
    
    @Test
    //名刺一般検索
    public void testCardSearchRequest() {
        //リクエスト生成
        HttpGet request = new HttpGet("https://api.sansan.com/v1/bizCards/search?range=all&tel=03&limit=1");
        //リクエスト送信
        BizCards cards = HttpClient.request(ApiKey.get(),
                                            request,
                                            BizCards.class);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.size(), is(1));
        
        System.out.println(cards);
    }
    
    @Test
    //名刺一般検索
    public void testCardSearchRequestWithRequestModel() {
        //リクエスト生成
        BizCardsRequest request = BizCardsRequest.builder()
                                                 .range(RangeParam.All)
                                                 .limit(1)
                                                 .build();
        //リクエスト送信
        ResponseModel<BizCards, BizCard> cards = HttpClient.request(ApiKey.get(),
                                                                    request);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.results(), is(notNullValue()));
        assertThat(cards.size(), is(1));
        
        System.out.println(cards);
    }
    
    @Test
    //人物取得
    public void testPersonRequest() {
        //リクエスト生成
        HttpGet request = new HttpGet("https://api.sansan.com/v1/persons/FB24CB2824CA3AEEA2803636E0D186E7");
        //リクエスト送信
        Persons persons = HttpClient.request(ApiKey.get(),
                                             request,
                                             Persons.class);
        
        //取得できること
        assertThat(persons, is(notNullValue()));
        assertThat(persons.size(), is(1));
        
        System.out.println(persons);
    }
    
    @Test
    //人物取得
    public void testPersonRequestWithRequestModel() {
        //リクエスト生成
        PersonRequest request = new PersonRequest("FB24CB2824CA3AEEA2803636E0D186E7");
        //リクエスト送信
        ResponseModel<Persons, Person> persons = HttpClient.request(ApiKey.get(),
                                                                    request);
        
        //取得できること
        assertThat(persons, is(notNullValue()));
        assertThat(persons.size(), is(1));
        
        System.out.println(persons);
    }
    
    @Test
    //タグ取得
    public void testTagsRequest() {
        //リクエスト生成
        HttpGet request = new HttpGet("https://api.sansan.com/v1/tags?range=all&type=shared&limit=1");
        //リクエスト送信
        Tags tags = HttpClient.request(ApiKey.get(), request, Tags.class);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        assertThat(tags.size(), is(1));
        
        System.out.println(tags);
    }
    
    @Test
    //タグ取得
    public void testTagsRequestWithRequestModel() {
        //リクエスト生成
        TagsRequest request = TagsRequest.builder()
                                         .range(RangeParam.All)
                                         .type(TypeParam.Shared)
                                         .limit(1)
                                         .build();
        //リクエスト送信
        ResponseModel<Tags, Tag> tags = HttpClient.request(ApiKey.get(),
                                                           request);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        assertThat(tags.results(), is(notNullValue()));
        assertThat(tags.size(), is(1));
        
        System.out.println(tags);
    }
    
    //不正APIキー送信時
    @Test
    public void testInvalidApiKey() {
        SansanApiClient api = new SansanApiClient("INVALID_API_KEY_STRING");
        Either<Throwable, ResponseModel<Tags, Tag>> response = api.requestWrappedEither(TagsRequest.ALL);
        
        //エラーとなること
        assertThat(response.isLeft(), is(true));
        assertThat(response.left().get().toString(),
                   is("Error(statusCode=401, message=null, errorCodes=invalid_api_key)"));
    }
    
    //必須パラメータ不足
    @Test
    public void testInvalidParam() {
        HttpGet request = new HttpGet("https://api.sansan.com/v1/bizCards");
        Either<Throwable, BizCards> response = Try.of(() -> HttpClient.request(ApiKey.get(),
                                                                               request,
                                                                               BizCards.class))
                                                  .toEither();
        
        //エラーとなること
        assertThat(response.isLeft(), is(true));
        assertThat(response.left().get().toString(),
                   is("Error(statusCode=400, message=null, errorCodes=required, required)"));
    }
    
}

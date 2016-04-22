package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.model.request.CardsRequest;
import com.github.asufana.sansanapi.model.request.PersonRequest;
import com.github.asufana.sansanapi.model.request.TagsRequest;
import com.github.asufana.sansanapi.model.request.params.Range;
import com.github.asufana.sansanapi.model.request.params.Status;
import com.github.asufana.sansanapi.model.request.params.Type;
import com.github.asufana.sansanapi.model.response.BizCards;
import com.github.asufana.sansanapi.model.response.Person;
import com.github.asufana.sansanapi.model.response.ResponseModel;
import com.github.asufana.sansanapi.model.response.Tags;
import javaslang.control.Either;
import javaslang.control.Try;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
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
        assertThat(cards.size(), is(not(0)));
        System.out.println(cards);
    }
    
    @Test
    //名刺期間検索
    public void testCardSearchPeriodRequestWithRequestModel() {
        
        //リクエスト生成
        CardsRequest request = CardsRequest.builder()
                                           .registeredFrom("20160401")
                                           .registeredTo("20260331")
                                           .range(Range.All)
                                           .limit(1)
                                           .offset(0)
                                           .status(Status.Completed)
                                           .build();
        //リクエスト送信
        ResponseModel<BizCards> cards = HttpClient.request(ApiKey.get(),
                                                           request);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.result(), is(notNullValue()));
        assertThat(cards.result().size(), is(1));
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
        assertThat(cards.size(), is(not(0)));
        System.out.println(cards);
    }
    
    @Test
    //名刺一般検索
    public void testCardSearchRequestWithRequestModel() {
        
        //リクエスト生成
        CardsRequest request = CardsRequest.builder()
        //.company("xxx")
        //.name("xxx")
        //.email("xxx")
        //.tel("xxx")
        //.mobile("xxx")
        //.tags("xxx")
        //.tags(Arrays.asList("xxx","xxx"))
                                           .range(Range.All)
                                           .limit(1)
                                           .offset(0)
                                           .status(Status.Completed)
                                           .build();
        //リクエスト送信
        ResponseModel<BizCards> cards = HttpClient.request(ApiKey.get(),
                                                           request);
        
        //取得できること
        assertThat(cards, is(notNullValue()));
        assertThat(cards.result(), is(notNullValue()));
        assertThat(cards.result().size(), is(1));
        System.out.println(cards);
    }
    
    @Test
    //人物取得
    public void testPersonRequest() {
        
        //リクエスト生成
        HttpGet request = new HttpGet("https://api.sansan.com/v1/persons/FB24CB2824CA3AEEA2803636E0D186E7");
        //リクエスト送信
        Person person = HttpClient.request(ApiKey.get(), request, Person.class);
        
        //取得できること
        assertThat(person, is(notNullValue()));
        System.out.println(person);
    }
    
    @Test
    //人物取得
    public void testPersonRequestWithRequestModel() {
        
        //リクエスト生成
        PersonRequest request = new PersonRequest("FB24CB2824CA3AEEA2803636E0D186E7");
        //リクエスト送信
        ResponseModel<Person> person = HttpClient.request(ApiKey.get(), request);
        
        //取得できること
        assertThat(person, is(notNullValue()));
        System.out.println(person);
    }
    
    @Test
    //タグ取得
    public void testTagsRequest() {
        
        //リクエスト生成
        HttpGet request = new HttpGet("https://api.sansan.com/v1/tags?range=all&type=shared");
        //リクエスト送信
        Tags tags = HttpClient.request(ApiKey.get(), request, Tags.class);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        assertThat(tags.size(), is(not(0)));
        System.out.println(tags);
    }
    
    @Test
    //タグ取得
    public void testTagsRequestWithRequestModel() {
        
        //リクエスト生成
        TagsRequest request = TagsRequest.builder()
                                         .range(Range.All)
                                         .type(Type.Shared)
                                         //.types(Arrays.asList(Type.Shared, Type.Private))
                                         .limit(1)
                                         .offset(1)
                                         .build();
        //リクエスト送信
        ResponseModel<Tags> tags = HttpClient.request(ApiKey.get(), request);
        
        //取得できること
        assertThat(tags, is(notNullValue()));
        assertThat(tags.result(), is(notNullValue()));
        assertThat(tags.result().size(), is(1));
        System.out.println(tags);
    }
    
    //不正APIキー送信時
    @Test
    public void testInvalidApiKey() {
        ApiClient api = new ApiClient("INVALID_API_KEY_STRING");
        Either<Throwable, ResponseModel<Tags>> response = api.requestWrappedEither(TagsRequest.ALL);
        
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

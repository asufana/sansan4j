package com.github.asufana.sansanapi.client;

import com.github.asufana.sansanapi.model.request.TagsRequest;
import com.github.asufana.sansanapi.model.request.params.Range;
import com.github.asufana.sansanapi.model.request.params.Type;
import com.github.asufana.sansanapi.model.response.ResponseModel;
import com.github.asufana.sansanapi.model.response.Tags;
import org.junit.Test;

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
    
}

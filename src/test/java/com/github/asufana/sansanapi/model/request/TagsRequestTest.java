package com.github.asufana.sansanapi.model.request;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class TagsRequestTest {
    
    @Test
    public void testStaticRequests() {
        Assert.assertThat(TagsRequest.ALL.generateUrl(),
                          CoreMatchers.is(String.format("https://api.sansan.com/v1/tags"
                                  + "?range=all"
                                  + "&type=shared"
                                  + "&type=public"
                                  + "&type=private"
                                  + "&limit=100"
                                  + "&offset=0")));
    }
}

package com.github.asufana.sansanapi.model.request;

import org.hamcrest.CoreMatchers;
import org.joda.time.DateMidnight;
import org.junit.Assert;
import org.junit.Test;

public class CardsRequestTest {
    
    @Test
    public void testStaticRequests() {
        Assert.assertThat(CardsRequest.TODAY.generateUrl(),
                          CoreMatchers.is(String.format("https://api.sansan.com/v1/bizCards/search"
                                                                + "?registeredFrom=%sT00:00:00Z"
                                                                + "&registeredTo=%sT00:00:00Z"
                                                                + "&range=all"
                                                                + "&limit=100"
                                                                + "&offset=0"
                                                                + "&entryStatus=processing"
                                                                + "&entryStatus=completed"
                                                                + "&entryStatus=unreadable",
                                                        new DateMidnight().toString("yyyy-MM-dd"),
                                                        new DateMidnight().plusDays(1)
                                                                          .toString("yyyy-MM-dd"))));
        
        Assert.assertThat(CardsRequest.YESTERDAY.generateUrl(),
                          CoreMatchers.is(String.format("https://api.sansan.com/v1/bizCards/search"
                                                                + "?registeredFrom=%sT00:00:00Z"
                                                                + "&registeredTo=%sT00:00:00Z"
                                                                + "&range=all"
                                                                + "&limit=100"
                                                                + "&offset=0"
                                                                + "&entryStatus=processing"
                                                                + "&entryStatus=completed"
                                                                + "&entryStatus=unreadable",
                                                        new DateMidnight().minusDays(1)
                                                                          .toString("yyyy-MM-dd"),
                                                        new DateMidnight().toString("yyyy-MM-dd"))));
        
        Assert.assertThat(CardsRequest.SINCE_YESTERDAY.generateUrl(),
                          CoreMatchers.is(String.format("https://api.sansan.com/v1/bizCards/search"
                                                                + "?registeredFrom=%sT00:00:00Z"
                                                                + "&registeredTo=%sT00:00:00Z"
                                                                + "&range=all"
                                                                + "&limit=100"
                                                                + "&offset=0"
                                                                + "&entryStatus=processing"
                                                                + "&entryStatus=completed"
                                                                + "&entryStatus=unreadable",
                                                        new DateMidnight().minusDays(1)
                                                                          .toString("yyyy-MM-dd"),
                                                        new DateMidnight().plusDays(1)
                                                                          .toString("yyyy-MM-dd"))));
        
        Assert.assertThat(CardsRequest.SINCE_LASTWEEK.generateUrl(),
                          CoreMatchers.is(String.format("https://api.sansan.com/v1/bizCards/search"
                                                                + "?registeredFrom=%sT00:00:00Z"
                                                                + "&registeredTo=%sT00:00:00Z"
                                                                + "&range=all"
                                                                + "&limit=100"
                                                                + "&offset=0"
                                                                + "&entryStatus=processing"
                                                                + "&entryStatus=completed"
                                                                + "&entryStatus=unreadable",
                                                        new DateMidnight().minusDays(7)
                                                                          .toString("yyyy-MM-dd"),
                                                        new DateMidnight().plusDays(1)
                                                                          .toString("yyyy-MM-dd"))));
    }
}

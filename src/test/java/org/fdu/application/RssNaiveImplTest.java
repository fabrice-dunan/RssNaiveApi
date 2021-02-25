package org.fdu.application;

import com.rometools.rome.io.FeedException;
import lombok.extern.slf4j.Slf4j;
import org.fdu.application.obj.FeedEntryMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.fdu.application.RssNaiveImplMain.FEEDS_URL_1;
import static org.fdu.application.RssNaiveImplMain.FEEDS_URL_2;

@Slf4j
public class RssNaiveImplTest {

    IRssNaive rss;

    @BeforeEach
    void setUp() throws IOException, FeedException {
        rss = new RssNaiveImpl();
        String[] urls = {FEEDS_URL_1, FEEDS_URL_2};
        for (String url : urls) {
            rss.addFeed(url, Duration.ofDays(1));
        }
    }

    //TODO test refresh feed: log presence ?
    @Test
    @DisplayName("Add feed source should work")
    void testAddSource() {
        Assertions.assertEquals(rss.getSubscriptions().size(), 2);
        Assertions.assertEquals(rss.getSubscriptions().get(FEEDS_URL_1).getFeedUrl(), FEEDS_URL_1);
        Assertions.assertEquals(rss.getSubscriptions().get(FEEDS_URL_2).getFeedUrl(), FEEDS_URL_2);
        //feed URL changed : no need to test Feed inner link
        Assertions.assertEquals(rss.getSubscriptions().get(FEEDS_URL_1).getRefreshPeriod(), Duration.ofDays(1));
        Assertions.assertEquals(rss.getSubscriptions().get(FEEDS_URL_2).getRefreshPeriod(), Duration.ofDays(1));
    }

    @Test
    @DisplayName("Get feed data should work")
    void testGetData() {
        for (FeedEntryMetadata feedEntryMetadata : rss.getTitlesAndUrls()) {
            Assertions.assertNotNull(feedEntryMetadata.getEntryUrl());
            Assertions.assertNotNull(feedEntryMetadata.getEntryTitle());
        }

    }
}

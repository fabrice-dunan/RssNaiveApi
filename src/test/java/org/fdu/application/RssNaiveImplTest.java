package org.fdu.application;

import com.rometools.rome.io.FeedException;
import lombok.extern.slf4j.Slf4j;
import org.fdu.application.obj.FeedEntryMetadata;
import org.fdu.application.obj.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import static org.fdu.application.RssNaiveImplMain.FEEDS_URL_1;
import static org.fdu.application.RssNaiveImplMain.FEEDS_URL_2;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class RssNaiveImplTest {

    IRssNaive rss;

    static final String[] urls = {FEEDS_URL_1, FEEDS_URL_2};

    @BeforeEach
    void setUp() throws IOException, FeedException {
        rss = new RssNaiveImpl();
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
    @DisplayName("Add feed source should give me back the correct object type")
    void testAddReturnType() throws IOException, FeedException {
        assertTrue(rss.addFeed(urls[1], Duration.ofDays(1)) instanceof Subscription);
    }

    @Test
    @DisplayName("Add feed source w/ wrong URL should fail with an exception")
    void testAddBadSource() {
        Assertions.assertThrows(Exception.class, ()-> rss.addFeed("http://bad.com/rss", Duration.ofDays(1)));
    }

    @Test
    @DisplayName("Add feed source w/ incorrect duration format should fail with an exception")
    void testAddBadDuration() {
        Assertions.assertThrows(Exception.class, ()-> rss.addFeed(urls[1], Duration.parse("PPPPTTTT111111")));
    }

    @Test
    @DisplayName("Get feed data should work")
    void testGetData() {
        for (FeedEntryMetadata feedEntryMetadata : rss.getTitlesAndUrls()) {
            Assertions.assertNotNull(feedEntryMetadata.getEntryUrl());
            Assertions.assertNotNull(feedEntryMetadata.getEntryTitle());
        }
    }

    @Test
    @DisplayName("Get feed data should give me back the correct object type")
    void testGetReturnType() {
        assertTrue(rss.getTitlesAndUrls() instanceof ArrayList);
    }

    //NOT ASKED
    @Test
    @DisplayName("Get feed data should give me back the correct object type")
    void testGetSubscriptionReturnType() {
        assertTrue(rss.getSubscriptions() instanceof HashMap);
    }


}

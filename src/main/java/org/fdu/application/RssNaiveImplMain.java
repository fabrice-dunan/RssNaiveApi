package org.fdu.application;

import com.rometools.rome.io.FeedException;
import org.fdu.application.obj.FeedEntryMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;

public class RssNaiveImplMain {

    public static final String FEEDS_URL_1 = "https://www.techrepublic.com/rssfeeds/articles/";
    public static final String FEEDS_URL_2 = "https://www.wired.com/feed/category/science/latest/rss";

    static Logger logger = LoggerFactory.getLogger(RssNaiveImplMain.class);

    public static void main(String[] args) {
        IRssNaive rssNaive = new RssNaiveImpl();
        String[] urls = {FEEDS_URL_1, FEEDS_URL_2};
        for (String url : urls) {
            try {
                rssNaive.addFeed(url, Duration.ofDays(1));
            } catch (FeedException fe) {
                logger.error("!!! Issue during feed creation with {} !!! <{}>", url, fe.getMessage());
            } catch (MalformedURLException e) {
                logger.error("!!! Feed URL Malformed with {} !!! <{}>", url, e.getMessage());
            } catch (IOException e) {
                logger.error("!!! Network issue with {} !!! <{}>", url, e.getMessage());
            }
        }
        for (FeedEntryMetadata etu : rssNaive.getTitlesAndUrls())
            System.out.println(etu);
    }
}

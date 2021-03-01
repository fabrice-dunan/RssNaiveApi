package org.fdu.application;

import com.rometools.rome.io.FeedException;
import org.fdu.application.obj.FeedEntryMetadata;
import org.fdu.application.obj.Subscription;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

public interface IRssNaive {

    /**
     * Designed to answer this US :
     * <p>
     * _As a user, I want to search and consult RSS feeds from several sources, for example :
     * - https://www.wired.com/feed/category/science/latest/rss
     * - https://www.techrepublic.com/rssfeeds/articles/
     * - ..._
     * <p>
     * This method returns a Collection of titles and URL.
     * The developer can show them in a UI for User interaction.
     *
     * @return ArrayList of FeedEntryMetadata
     */
    ArrayList<FeedEntryMetadata> getTitlesAndUrls();

    /**
     * Designed to answer this US :
     * <p>
     * _As an admin, I can add and configure new RSS feeds into the system. The configuration
     * must include :
     * - The feed URL
     * - The periodicity (how often the feed is refreshed) : each minute, each hour, each 12
     * hours, each day etc._
     * <p>
     * This method permits to add a Feed source and the period after which it will be refreshed.
     *
     * @param feedUrl - String representation of the URL of the feed source
     * @param pollInterval - Duration representaiton of time between two refreshes of the feed
     * @throws IOException - Exception XMl parser during acquisition of the feed
     * @throws FeedException - Exception during the creation of Feed instance
     * @return A subscription
     */
    Subscription addFeed(String feedUrl, Duration pollInterval) throws IOException, FeedException;

    /**
     * Additional method which permits to get more infos as far as the Feed sources, entries,... are concerned.
     * It may be used to update fetch duration parameters...
     * <p>
     * The map key is the String which represents feed source URL.
     *
     * (NOT ASKED)
     *
     * @return HashMap of String, Subscription
     */
    HashMap<String, Subscription> getSubscriptions();
}

package org.fdu.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.fdu.application.obj.FeedEntryMetadata;
import org.fdu.application.obj.Subscription;
import org.fdu.application.tasks.FeedFetchTask;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

@Slf4j
@Service
public class RssNaiveImpl implements IRssNaive {

    public static final String ISSUE_MSG_NET = "Ignoring entry with malformed URL";

    @Getter
    private final HashMap<String, Subscription> subscriptions;

    public RssNaiveImpl() {
        subscriptions = new HashMap<>();
    }


    public ArrayList<FeedEntryMetadata> getTitlesAndUrls() {
        ArrayList<FeedEntryMetadata> entriesTitlesAndUrls = new ArrayList<>();
        for (Subscription subscription : subscriptions.values()) {
            for (SyndEntry entry : subscription.getFeed().getEntries()) {
                FeedEntryMetadata feedEntryMetadata;
                String title = entry.getTitle();
                log.debug("\nTitre {}", title);
                String desc = entry.getDescription().getValue();
                log.debug("Desc {}\n", title);
                feedEntryMetadata = new FeedEntryMetadata(title, desc, entry.getLink());
                entriesTitlesAndUrls.add(feedEntryMetadata);
            }
        }
        return entriesTitlesAndUrls;
    }

    public Subscription addFeed(@NonNull String feedUrl, @NonNull Duration pollInterval) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(feedUrl)));
        Subscription subscription = new Subscription(feed, feedUrl, pollInterval);
        subscriptions.put(feedUrl, subscription);
        long interval = pollInterval.toMillis();
        new Timer().scheduleAtFixedRate(new FeedFetchTask(subscription), interval, interval);
        return subscription;
    }

    /**
     * Helper to convert ArrayList of FeedEntryMetadata to String
     *
     * @param datas - an array of FeedEntryMetadata
     * @return String
     */
    public static String out2String(ArrayList<FeedEntryMetadata> datas) {
        StringBuilder titlesAndUrls = new StringBuilder();
        for (FeedEntryMetadata feedEntryMetadata : datas) {
            titlesAndUrls.append(feedEntryMetadata.toString()).append("\n");
        }
        return titlesAndUrls.toString();
    }

    /**
     * Helper to convert ArrayList of FeedEntryMetadata to JSONArray
     *
     * @param datas - an array of FeedEntryMetadata
     * @return JSONArray
     */
    public static String out2JsonString(ArrayList<FeedEntryMetadata> datas) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(datas);
    }

}

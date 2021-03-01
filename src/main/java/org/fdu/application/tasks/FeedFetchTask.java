package org.fdu.application.tasks;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fdu.application.obj.Subscription;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;

@Slf4j
@AllArgsConstructor
public class FeedFetchTask extends TimerTask {

    public static final String ISSUE_MSG_FETCH_URL = "Malformed URL retrieval during fetch !";
    public static final String ISSUE_MSG_FETCH_FEED = "Feed exception during fetch !";
    public static final String ISSUE_MSG_FETCH_NET = "IO exception during fetch !";

    private final Subscription subscription;

    @Override
    public void run() {
        SyndFeed feed = null;
        String feedUrl = subscription.getFeedUrl();
        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(feedUrl)));
            log.info("The feed identified by <{}> is refreshed !", feedUrl);
        } catch (MalformedURLException e) {
            log.error(ISSUE_MSG_FETCH_URL, e);
        } catch (FeedException e) {
            log.error(ISSUE_MSG_FETCH_FEED, e);
        } catch (IOException e) {
            log.error(ISSUE_MSG_FETCH_NET, e);
        }
        subscription.setFeed(feed);
    }
}

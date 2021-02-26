package org.fdu.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rometools.rome.io.FeedException;
import lombok.extern.slf4j.Slf4j;
import org.fdu.application.obj.FeedAdminInput;
import org.fdu.application.obj.FeedEntryMetadata;
import org.fdu.application.obj.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

@SuppressWarnings("SpellCheckingInspection")
@RestController
@Slf4j
public class RssNaiveController {
    @Autowired
    private IRssNaive rss;

    @PostConstruct
    public void init() {
        rss=new RssNaiveImpl();
    }

    @SuppressWarnings("SpellCheckingInspection")
    @RequestMapping(value = "/addfeed", method = RequestMethod.POST)
    public String addFeed(@RequestBody FeedAdminInput feedAdminInput) {
        log.debug("\n*************  POST /addfeed  ************");
        Subscription sub = null;
        try {
            sub = rss.addFeed(feedAdminInput.getUrlStr(), Duration.parse(feedAdminInput.getRefreshDurationStr()));
        } catch (IOException e) {
            log.error("Net issue : {}", e.getMessage());
        } catch (FeedException e) {
            log.error(RssNaiveImpl.ISSUE_MSG_NET + ": {}", e.getMessage());
        }
        if(sub != null)
            return "Added " + sub.getFeed().getTitle() + " <" + sub.getFeed().getLink() + ">\n";
        else
            return "Nothing added !!! \n";
    }

    @RequestMapping(value = "/getfeeds", method = RequestMethod.GET)
    public String getFeeds() throws JsonProcessingException {
        log.debug("\n*************  GET /getfeeds  ************");
        ArrayList<FeedEntryMetadata> datas = rss.getTitlesAndUrls();
        return RssNaiveImpl.out2JsonString(datas);
    }


}

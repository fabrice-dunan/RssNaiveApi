package org.fdu.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.fdu.application.obj.FeedAdminInput;
import org.fdu.application.obj.FeedEntryMetadata;
import org.fdu.application.obj.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.ArrayList;

@SuppressWarnings("SpellCheckingInspection")
@RestController
@Slf4j
public class RssNaiveController {

    private final IRssNaive rss;

    @Autowired
    public RssNaiveController(IRssNaive rss) {
        this.rss = rss;
    }


    @SuppressWarnings("SpellCheckingInspection")
    @RequestMapping(value = "/addfeed", method = RequestMethod.POST)
    public ResponseEntity<String> addFeed(@RequestBody FeedAdminInput feedAdminInput) {
        log.debug("\n*************  POST /addfeed  ************");
        Subscription sub;
        try {
            sub = rss.addFeed(feedAdminInput.getUrlStr(), Duration.parse(feedAdminInput.getRefreshDurationStr()));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Issue with RSSNaiveAPI !!!", e);
        }
        if (sub != null)
            return new ResponseEntity<>("Added " + sub.getFeed().getTitle() + " <" + sub.getFeed().getLink() + ">\n", HttpStatus.OK);
        else
            return new ResponseEntity<>("Nothing added !!! \n", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/getfeeds", method = RequestMethod.GET)
    public String getFeeds() throws JsonProcessingException {
        log.debug("\n*************  GET /getfeeds  ************");
        ArrayList<FeedEntryMetadata> datas;
        try {
            datas = rss.getTitlesAndUrls();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Issue with RSSNaiveAPI !!!", e);
        }
        return RssNaiveImpl.out2JsonString(datas);
    }


}

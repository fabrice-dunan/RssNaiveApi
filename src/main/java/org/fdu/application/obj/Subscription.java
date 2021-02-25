package org.fdu.application.obj;

import com.rometools.rome.feed.synd.SyndFeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@AllArgsConstructor
public class Subscription {

    @Setter
    @Getter
    SyndFeed feed;

    @Setter
    @Getter
    String feedUrl;

    @Setter
    @Getter
    Duration refreshPeriod;
}

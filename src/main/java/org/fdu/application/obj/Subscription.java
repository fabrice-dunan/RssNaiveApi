package org.fdu.application.obj;

import com.rometools.rome.feed.synd.SyndFeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@AllArgsConstructor
@Setter
@Getter
public class Subscription {
    SyndFeed feed;

    String feedUrl;

    Duration refreshPeriod;
}

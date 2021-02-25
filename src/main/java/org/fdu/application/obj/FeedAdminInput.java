package org.fdu.application.obj;

import lombok.Getter;
import lombok.Setter;

public class FeedAdminInput {
    @Getter
    @Setter
    private String urlStr;

    //syntax https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-
    @Getter
    @Setter
    private String refreshDurationStr;
}

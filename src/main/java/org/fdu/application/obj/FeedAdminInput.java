package org.fdu.application.obj;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedAdminInput {

    private String urlStr;

    //syntax https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-
    private String refreshDurationStr;
}

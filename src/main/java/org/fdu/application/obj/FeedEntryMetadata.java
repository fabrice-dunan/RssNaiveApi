package org.fdu.application.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class FeedEntryMetadata {
    @Setter
    @Getter
    private String entryTitle;

    @Setter
    @Getter
    private String entryDesc;

    @Setter
    @Getter
    private String entryUrl;
}

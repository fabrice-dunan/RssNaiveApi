package org.fdu.application.obj;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"entryTitle", "entryDesc", "entryUrl"})
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

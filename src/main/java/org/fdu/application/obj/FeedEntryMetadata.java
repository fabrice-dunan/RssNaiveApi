package org.fdu.application.obj;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"entryTitle", "entryDesc", "entryUrl"})
@AllArgsConstructor
@ToString
@Setter
@Getter
public class FeedEntryMetadata {
    private String entryTitle;

    private String entryDesc;

    private String entryUrl;
}

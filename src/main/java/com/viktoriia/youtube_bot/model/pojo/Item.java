package com.viktoriia.youtube_bot.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * generated classes for parsing search results from YouTube API search request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "kind",
        "etag",
        "id",
        "snippet"
})
@Getter
@Setter
public class Item {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("id")
    private Id id;
    @JsonProperty("snippet")
    private Snippet snippet;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public boolean isChannel() {
        return "youtube#channel".equals(getId().getKind());
    }
}

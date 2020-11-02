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
        "channelId",
        "videoId"
})
@Getter
@Setter
public class Id {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("channelId")
    private String channelId;
    @JsonProperty("videoId")
    private String videoId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}

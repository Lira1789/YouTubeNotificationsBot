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
        "publishedAt",
        "channelId",
        "title",
        "description",
        "thumbnails",
        "channelTitle",
        "liveBroadcastContent",
        "publishTime"
})
@Getter
@Setter
public class Snippet {

    @JsonProperty("publishedAt")
    private String publishedAt;
    @JsonProperty("channelId")
    private String channelId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("thumbnails")
    private Thumbnails thumbnails;
    @JsonProperty("channelTitle")
    private String channelTitle;
    @JsonProperty("liveBroadcastContent")
    private String liveBroadcastContent;
    @JsonProperty("publishTime")
    private String publishTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}

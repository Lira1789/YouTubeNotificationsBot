package com.viktoriia.youtube_bot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * generated classes for parsing search results from YouTube API search request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "kind",
        "etag",
        "nextPageToken",
        "regionCode",
        "pageInfo",
        "items"
})
@Getter
@Setter
public class SearchResult {

    @JsonProperty("kind")
    private String kind;
    @JsonProperty("etag")
    private String etag;
    @JsonProperty("nextPageToken")
    private String nextPageToken;
    @JsonProperty("regionCode")
    private String regionCode;
    @JsonProperty("pageInfo")
    private PageInfo pageInfo;
    @JsonProperty("items")
    private List<Item> items = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}

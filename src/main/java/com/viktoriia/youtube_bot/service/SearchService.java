package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.exceptions.EmptySearchResult;
import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.model.pojo.Item;
import com.viktoriia.youtube_bot.model.pojo.SearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * searches channels by name with YouTube API
 */
@Service
@RequiredArgsConstructor
public class SearchService {

    private final RestTemplate restTemplate;
    private final ChannelService channelService;
    @Value("${search.url}")
    private String searchUrl;

    public List<Channel> searchChannel(String stringChannelId) {
        ResponseEntity<SearchResult> forEntity = restTemplate.getForEntity(searchUrl, SearchResult.class, stringChannelId);
        Optional<SearchResult> optionalSearchResult = Optional.ofNullable(forEntity.getBody());
        List<Channel> channels = getChannelsFromSearchResult(optionalSearchResult.orElseThrow(EmptySearchResult::new));
        channelService.createChannels(channels);
        return channels;
    }

    private List<Channel> getChannelsFromSearchResult(SearchResult searchResult) {
        return searchResult.getItems()
                .stream()
                .filter(Item::isChannel)
                .map(Item::getChannelFromItem)
                .collect(Collectors.toList());
    }
}

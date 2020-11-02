package com.viktoriia.youtube_bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.viktoriia.youtube_bot.common.Constants.HUB_CALLBACK;
import static com.viktoriia.youtube_bot.common.Constants.HUB_MODE;
import static com.viktoriia.youtube_bot.common.Constants.HUB_TOPIC;
import static com.viktoriia.youtube_bot.common.Constants.HUB_VERIFY;

/**
 * Sends a request to https://pubsubhubbub.appspot.com/subscribe
 * to subscribe to channel updates
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final RestTemplate restTemplate;
    @Value("${subscription.url}")
    private String url;
    @Value("${subscription.youtubeurl}")
    private String youTubeUrl;
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${subscription.verify}")
    private String verifyMode;

    public void subscriptionAction(String channelId, String mode) {
        String channelUrl = youTubeUrl + channelId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = getFormDataMap(channelUrl, mode);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        restTemplate.postForEntity(url, request, String.class);
        log.info(String.format("%s to channel %s", mode, channelId));
    }

    private MultiValueMap<String, String> getFormDataMap(String channel, String mode) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(HUB_CALLBACK, webHookPath + "/subs");
        map.add(HUB_MODE, mode);
        map.add(HUB_TOPIC, channel);
        map.add(HUB_VERIFY, verifyMode);
        return map;
    }
}

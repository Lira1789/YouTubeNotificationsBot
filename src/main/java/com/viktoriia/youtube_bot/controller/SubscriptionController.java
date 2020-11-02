package com.viktoriia.youtube_bot.controller;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.viktoriia.youtube_bot.bot.YouTubeBot;
import com.viktoriia.youtube_bot.exceptions.EmptySyndEntryException;
import com.viktoriia.youtube_bot.model.User;
import com.viktoriia.youtube_bot.model.Video;
import com.viktoriia.youtube_bot.service.UserService;
import com.viktoriia.youtube_bot.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.viktoriia.youtube_bot.common.Constants.HUB_CHALLENGE;
import static com.viktoriia.youtube_bot.common.Constants.HUB_TOPIC;

@RestController
@RequestMapping(value = "/subs")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final YouTubeBot youTubeBot;
    private final UserService userService;
    private final VideoService videoService;

    /**
     * Processes a request from https://pubsubhubbub.appspot.com/subscribe
     * and confirms a subscription for a specific channel
     * @param challenge special param that need to be returned back
     * @param channel - YouTube channel ID
     * @return challenge param to server
     */
    @GetMapping
    public ResponseEntity<String> verifySubscription(@RequestParam(value = HUB_CHALLENGE) String challenge,
                                                     @RequestParam(value = HUB_TOPIC) String channel) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
        log.info(String.format("%s - subscription verified", channel));
        return new ResponseEntity<>(challenge, httpHeaders, HttpStatus.OK);
    }

    /**
     * Receives data from https://pubsubhubbub.appspot.com/subscribe
     *  about the new video of the channel in the ATOM file format
     *  and sends the data to users who have subscribed to the channel
     * @param request - HttpServlet Request to get data from
     */
    @PostMapping
    public void sendNotification(HttpServletRequest request) throws IOException, FeedException {
        Video video = getVideoFromRequest(request);
        log.info(String.format("Notification: video %s", video.toString()));
        if (videoService.isVideoExist(video)) {
            return;
        }
        videoService.createVideo(video);
        Set<User> usersForNotifications = userService.getAllUsersForNotifications(video.getChannelStringId());
        for (User user : usersForNotifications) {
            try {
                youTubeBot.execute(new SendMessage(user.getChatId(), video.toString()));
            } catch (TelegramApiException e) {
                log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private Video getVideoFromRequest(HttpServletRequest request) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndEntry entry;
        try (ServletInputStream inputStream = request.getInputStream()) {
            SyndFeed feed = input.build(new XmlReader(inputStream));
            List<SyndEntry> entries = feed.getEntries();
            if (entries.isEmpty()) {
                throw new EmptySyndEntryException();
            }
            entry = entries.get(0);
        }
        return Video.builder()
                .stringId(entry.getForeignMarkup().get(0).getContent(0).getValue())
                .title(entry.getTitle())
                .author(entry.getAuthor())
                .url(entry.getLink())
                .channelStringId(entry.getForeignMarkup().get(1).getContent(0).getValue())
                .date(LocalDateTime.now())
                .build();
    }
}

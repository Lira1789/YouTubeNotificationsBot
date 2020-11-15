package com.viktoriia.youtube_bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingService {

    private final VideoService videoService;
    private final ChannelService channelService;
    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "${scheduling.deletevideotime}")
    public void deleteOldVideos() {
        int oldVideos = videoService.deleteOldVideos();
        log.info(String.format("%d old videos were deleted", oldVideos));
    }

    @Scheduled(cron = "${scheduling.deletechanneltime}")
    public void deleteChannelsWithNoUsers() {
        int channelsWithNoUsers = channelService.deleteChannelsWithNoUsers();
        log.info(String.format("%d channels without users were deleted", channelsWithNoUsers));
    }

}

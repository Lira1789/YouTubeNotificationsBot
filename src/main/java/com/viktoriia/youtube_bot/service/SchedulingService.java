package com.viktoriia.youtube_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingService {

    private final VideoService videoService;
    private final ChannelService channelService;

    @Scheduled(cron = "${scheduling.deletevideotime}")
    public void deleteOldVideos() {
        videoService.deleteOldVideos();
    }

    @Scheduled(cron = "${scheduling.deletechanneltime}")
    public void deleteChannelsWithNoUsers() {
        channelService.deleteChannelsWithNoUsers();
    }

}

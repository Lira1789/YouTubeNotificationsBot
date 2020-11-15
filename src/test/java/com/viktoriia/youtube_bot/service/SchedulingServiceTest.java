package com.viktoriia.youtube_bot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SchedulingServiceTest {

    @Mock
    private VideoService videoService;

    @Mock
    private ChannelService channelService;

    @InjectMocks
    private SchedulingService schedulingService;

    @Test
    public void deleteOldVideos() {
        schedulingService.deleteOldVideos();
        verify(videoService).deleteOldVideos();
    }

    @Test
    public void deleteChannelsWithNoUsers() {
        schedulingService.deleteChannelsWithNoUsers();
        verify(channelService).deleteChannelsWithNoUsers();
    }
}
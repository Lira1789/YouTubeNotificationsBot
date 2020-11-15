package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.model.Video;
import com.viktoriia.youtube_bot.repository.VideoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    private Video video;

    @Before
    public void setUp() {
        video = getVideos().get(0);
    }

    @Test
    public void createVideo() {
        when(videoRepository.save(any(Video.class))).thenReturn(video);
        Video actualVideo = videoService.createVideo(video);
        verify(videoRepository).save(video);
        assertEquals(video, actualVideo);
    }

    @Test
    public void isVideoExists_whenVideoExist_thenReturnTrue() {
        when(videoRepository.existsByStringId(anyString())).thenReturn(true);
        assertTrue(videoService.isVideoExist(video));
    }

    @Test
    public void isVideoExists_whenVideoNotExist_thenReturnFalse() {
        when(videoRepository.existsByStringId(anyString())).thenReturn(false);
        assertFalse(videoService.isVideoExist(video));
    }

    @Test
    public void deleteOldVideos() {
        List<Video> videos = getVideos();
        when(videoRepository.findByDateBefore(any(LocalDateTime.class))).thenReturn(videos);
        int oldVideos = videoService.deleteOldVideos();
        verify(videoRepository, times(videos.size())).delete(any(Video.class));
        assertEquals(videos.size(), oldVideos);
    }

    private List<Video> getVideos() {
        return Arrays.asList(
                Video.builder().author("Author").title("Video").channelStringId("jwefjhg").stringId("hdgsag").videoId(1L).build(),
                Video.builder().author("Author").title("Video1").channelStringId("jwefjhg11").stringId("hdgsag11").videoId(2L).build()
        );
    }
}
package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.model.Video;
import com.viktoriia.youtube_bot.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    @Transactional
    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Transactional(readOnly = true)
    public boolean isVideoExist(Video video) {
        return videoRepository.existsByStringId(video.getStringId());
    }

    @Transactional
    public void deleteOldVideos() {
        List<Video> oldVideos = videoRepository.findByDateBefore(LocalDateTime.now().minusDays(1));
        oldVideos.forEach(videoRepository::delete);
    }
}

package com.viktoriia.youtube_bot.repository;

import com.viktoriia.youtube_bot.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    boolean existsByStringId(String stringId);

    List<Video> findByDateBefore(LocalDateTime date);
}

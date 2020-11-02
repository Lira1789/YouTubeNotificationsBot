package com.viktoriia.youtube_bot.repository;

import com.viktoriia.youtube_bot.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByStringId(String stringId);

    boolean existsByStringId(String stringId);

    List<Channel> findByUsersNotNull();

    List<Channel> findByUsersIsNull();

}

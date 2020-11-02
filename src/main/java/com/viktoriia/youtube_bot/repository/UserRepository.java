package com.viktoriia.youtube_bot.repository;

import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllByChannelsIsContaining(Channel channel);
}

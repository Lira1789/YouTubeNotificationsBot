package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.model.User;
import com.viktoriia.youtube_bot.repository.ChannelRepository;
import com.viktoriia.youtube_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Transactional
    public User createUser(String userName, long chatId) {
        User user = User.builder().chatId(chatId).username(userName).build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(long chatId) {
        return userRepository.findById(chatId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User addChannelToUser(long chatId, String stringChannelId) {
        User user = userRepository.findById(chatId).orElseThrow(NotFoundException::new);
        Channel channel = channelRepository.findByStringId(stringChannelId).orElseThrow(NotFoundException::new);
        user.getChannels().add(channel);
        return userRepository.save(user);
    }

    @Transactional
    public User deleteChannelFromUser(long chatId, String stringChannelId) {
        User user = userRepository.findById(chatId).orElseThrow(NotFoundException::new);
        Channel channel = channelRepository.findByStringId(stringChannelId).orElseThrow(NotFoundException::new);
        user.getChannels().remove(channel);
        return userRepository.save(user);
    }

    @Transactional
    public User switchUserToSearchMode(long chatId) {
        User user = userRepository.findById(chatId).orElseThrow(NotFoundException::new);
        user.setSearch(true);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean isUserOnSearchMode(long chatId) {
        User user = userRepository.findById(chatId).orElseThrow(NotFoundException::new);
        boolean search = user.isSearch();
        user.setSearch(false);
        userRepository.save(user);
        return search;
    }

    @Transactional(readOnly = true)
    public Set<User> getAllUsersForNotifications(String stringChannelId) {
        Channel channel = channelRepository.findByStringId(stringChannelId).orElseThrow(NotFoundException::new);
        return userRepository.findAllByChannelsIsContaining(channel);
    }
}

package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.exceptions.NotFoundException;
import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.model.User;
import com.viktoriia.youtube_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChannelService channelService;

    @Transactional
    public User createUser(String userName, long chatId) {
        User user = User.builder().chatId(chatId).username(userName).build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(long chatId) {
        return userRepository.findById(chatId).orElseThrow(() -> new NotFoundException(User.class));
    }

    @Transactional
    public User addChannelToUser(long chatId, String stringChannelId) {
        User user = getUser(chatId);
        Channel channel = channelService.getChannelByStringId(stringChannelId);
        user.getChannels().add(channel);
        return userRepository.save(user);
    }

    @Transactional
    public User deleteChannelFromUser(long chatId, String stringChannelId) {
        User user = getUser(chatId);
        Channel channel = channelService.getChannelByStringId(stringChannelId);
        user.getChannels().remove(channel);
        return userRepository.save(user);
    }

    @Transactional
    public void switchUserToSearchMode(long chatId) {
        User user = getUser(chatId);
        user.setSearch(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean isUserOnSearchMode(long chatId) {
        User user = getUser(chatId);
        return user.isSearch();
    }

    @Transactional
    public void switchUserFromSearchMode(long chatId) {
        User user = getUser(chatId);
        user.setSearch(false);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Set<User> getAllUsersForNotifications(String stringChannelId) {
        Channel channel = channelService.getChannelByStringId(stringChannelId);
        return userRepository.findAllByChannelsIsContaining(channel);
    }
}

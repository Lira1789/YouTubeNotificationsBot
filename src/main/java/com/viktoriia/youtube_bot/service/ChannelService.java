package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional
    public void createChannels(Set<Channel> channels) {
        channels.forEach(channel -> {
            if (!channelRepository.existsByStringId(channel.getStringId())) {
                channelRepository.save(channel);
            }
        });
    }

    @Transactional
    public void deleteChannelsWithNoUsers() {
        List<Channel> channels = channelRepository.findByUsersIsNull();
        channels.forEach(channelRepository::delete);
    }
}

package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.exceptions.NotFoundException;
import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional
    public void createChannels(List<Channel> channels) {
        channels.forEach(channel -> {
            if (!channelRepository.existsByStringId(channel.getStringId())) {
                channelRepository.save(channel);
            }
        });
    }

    @Transactional
    public int deleteChannelsWithNoUsers() {
        List<Channel> channels = channelRepository.findByUsersIsNull();
        int channelsSize = channels.size();
        channels.forEach(channelRepository::delete);
        return channelsSize;
    }

    @Transactional(readOnly = true)
    public List<Channel> getAllChannelsWithUsers() {
        return channelRepository.findByUsersNotNull();
    }

    @Transactional(readOnly = true)
    public Channel getChannelByStringId(String stringId) {
        return channelRepository.findByStringId(stringId).orElseThrow(() -> new NotFoundException(Channel.class));
    }
}

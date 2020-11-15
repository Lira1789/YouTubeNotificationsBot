package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.repository.ChannelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ChannelService channelService;

    private List<Channel> channels;

    @Before
    public void setUp() throws Exception {
        channels = getChannels();
    }

    @Test
    public void createChannels_whenChannelsNotExist_thenChannelsCreated() {
        List<Channel> channels = getChannels();
        when(channelRepository.existsByStringId(anyString())).thenReturn(false);
        channelService.createChannels(channels);
        verify(channelRepository, times(channels.size())).save(any(Channel.class));
    }

    @Test
    public void createChannels_whenChannelsExist_thenChannelsNotCreated() {
        List<Channel> channels = getChannels();
        when(channelRepository.existsByStringId(anyString())).thenReturn(true);
        channelService.createChannels(channels);
        verify(channelRepository, never()).save(any(Channel.class));
    }

    @Test
    public void deleteChannelsWithNoUsers() {
        when(channelRepository.findByUsersIsNull()).thenReturn(channels);
        int channelsWithNoUsers = channelService.deleteChannelsWithNoUsers();
        verify(channelRepository, times(channels.size())).delete(any(Channel.class));
        assertEquals(channels.size(), channelsWithNoUsers);
    }

    @Test
    public void getAllChannelsWithUsers() {
        when(channelRepository.findByUsersNotNull()).thenReturn(channels);
        List<Channel> actualChannels = channelService.getAllChannelsWithUsers();
        assertEquals(channels, actualChannels);
    }

    @Test
    public void getChannelByStringId_whenStringIdIsCorrect() {
        Channel expectedChannel = getChannels().get(0);
        when(channelRepository.findByStringId("String id 1")).thenReturn(Optional.ofNullable(expectedChannel));
        Channel actualChannel = channelService.getChannelByStringId("String id 1");
        assertEquals(expectedChannel, actualChannel);
    }

    @Test(expected = NotFoundException.class)
    public void getChannelByStringId_whenNotFound_thenShouldThrowException() {
        when(channelRepository.findByStringId("String id 1")).thenReturn(Optional.empty());
        channelService.getChannelByStringId("String id 1");
    }

    private List<Channel> getChannels() {
        return Arrays.asList(
        Channel.builder().title("Channel 1").stringId("String id 1").channelId(1L).build(),
        Channel.builder().title("Channel 2").stringId("String id 2").channelId(2L).build());
    }
}
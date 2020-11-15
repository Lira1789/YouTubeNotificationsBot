package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.model.User;
import com.viktoriia.youtube_bot.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChannelService channelService;

    @InjectMocks
    private UserService userService;

    private User expectedUser;
    private Channel channel;

    @Before
    public void setUp() throws Exception {
        expectedUser = getUsers().get(0);
        channel = Channel.builder().title("Channel 1").stringId("String id 1").channelId(1L).build();
    }

    @Test
    public void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User actualUser = userService.createUser("User 1", 1L);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void getUser_WhenChatIdIsCorrect_thenGetUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
        User actualUser = userService.getUser(1L);
        assertEquals(expectedUser, actualUser);
    }

    @Test(expected = NotFoundException.class)
    public void getUser_WhenChatIdIsNotCorrect_thenThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        userService.getUser(500L);
    }

    @Test
    public void addChannelToUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
        when(channelService.getChannelByStringId(anyString())).thenReturn(channel);
        expectedUser.getChannels().add(channel);
        userService.addChannelToUser(expectedUser.getChatId(), channel.getStringId());
        verify(userRepository).save(eq(expectedUser));
    }

    @Test
    public void deleteChannelFromUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
        when(channelService.getChannelByStringId(anyString())).thenReturn(channel);
        expectedUser.getChannels().remove(channel);
        userService.deleteChannelFromUser(expectedUser.getChatId(), channel.getStringId());
        verify(userRepository).save(eq(expectedUser));
    }

    @Test
    public void switchUserToSearchMode() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
        expectedUser.setSearch(true);
        userService.switchUserToSearchMode(expectedUser.getChatId());
        verify(userRepository).save(eq(expectedUser));
    }

    @Test
    public void isUserOnSearchMode_whenUserOnSearchMode_thenReturnTrue() {
        expectedUser.setSearch(true);
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
        assertTrue(userService.isUserOnSearchMode(1L));
        expectedUser.setSearch(false);
        verify(userRepository).save(eq(expectedUser));
    }

    @Test
    public void isUserOnSearchMode_whenUserNotOnSearchMode_thenReturnFalse() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));
        assertFalse(userService.isUserOnSearchMode(1L));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void getAllUsersForNotifications() {
        HashSet<User> expectedUsers = new HashSet<>(getUsers());
        when(channelService.getChannelByStringId("String id 1")).thenReturn(channel);
        when(userRepository.findAllByChannelsIsContaining(channel)).thenReturn(expectedUsers);
        Set<User> actualUsers = userService.getAllUsersForNotifications(channel.getStringId());
        assertEquals(expectedUsers, actualUsers);
    }

    private List<User> getUsers() {
        return Arrays.asList(
                User.builder().chatId(1L).username("User 1").channels(new HashSet<>()).isSearch(false).build(),
                User.builder().chatId(2L).username("User 1").channels(new HashSet<>()).isSearch(false).build()
        );
    }
}
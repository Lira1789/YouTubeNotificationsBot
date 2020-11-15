package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.common.SubsMod;
import com.viktoriia.youtube_bot.model.Channel;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.viktoriia.youtube_bot.common.Messages.EMPTY_MESSAGE;
import static com.viktoriia.youtube_bot.common.Messages.FIND_MESSAGE;
import static org.junit.Assert.assertEquals;

public class MessageServiceTest {

    private MessageService messageService;
    private List<Channel> channels;


    @Before
    public void setUp() throws Exception {
        messageService = new MessageService();
        channels = getChannels();
    }

    @Test
    public void createMessage() {
        List<SendMessage> expected = Collections.singletonList(new SendMessage(1L, "Hello"));
        List<SendMessage> actual = messageService.createMessage("Hello", 1L);
        assertEquals(expected, actual);
    }

    @Test
    public void createMessages_whenChannelsIsEmpty_thenWritesOnlyMessage() {
        List<SendMessage> expected = Collections.singletonList(new SendMessage(1L, "No channels"));
        List<SendMessage> actual =
                messageService.createMessages(Collections.emptyList(), SubsMod.SUBSCRIBE.name(), 1L, "No channels");
        assertEquals(expected, actual);
    }

    @Test
    public void createMessages_whenChannelsIsNull_thenWritesOnlyMessage() {
        List<SendMessage> expected = Collections.singletonList(new SendMessage(1L, "No channels"));
        List<SendMessage> actual =
                messageService.createMessages(null, SubsMod.SUBSCRIBE.name(), 1L, "No channels");
        assertEquals(expected, actual);
    }

    @Test
    public void createMessages_whenChannelsNotEmptyAndSubscribeMode_thenWritesChannelsAndSubscribeMessage() {
        List<SendMessage> expected = Arrays.asList(
                new SendMessage(1L, FIND_MESSAGE + channels.get(0)).enableMarkdown(true)
                        .setReplyMarkup(messageService.getKeyBoard(channels.get(0).getStringId(), SubsMod.SUBSCRIBE.name(), SubsMod.SUBSCRIBE.name())),
                new SendMessage(1L, FIND_MESSAGE + channels.get(1)).enableMarkdown(true)
                        .setReplyMarkup(messageService.getKeyBoard(channels.get(1).getStringId(), SubsMod.SUBSCRIBE.name(), SubsMod.SUBSCRIBE.name())));
        List<SendMessage> actual =
                messageService.createMessages(channels, SubsMod.SUBSCRIBE.name(), 1L, "No channels");
        assertEquals(expected, actual);
    }

    @Test
    public void createMessages_whenChannelsNotEmptyAndUnsubscribeMode_thenWritesChannelsAndUnsubscribeMessage() {
        List<SendMessage> expected = Arrays.asList(
                new SendMessage(1L, EMPTY_MESSAGE + channels.get(0)).enableMarkdown(true)
                        .setReplyMarkup(messageService.getKeyBoard(channels.get(0).getStringId(), SubsMod.UNSUBSCRIBE.name(), SubsMod.UNSUBSCRIBE.name())),
                new SendMessage(1L, EMPTY_MESSAGE + channels.get(1)).enableMarkdown(true)
                        .setReplyMarkup(messageService.getKeyBoard(channels.get(1).getStringId(), SubsMod.UNSUBSCRIBE.name(), SubsMod.UNSUBSCRIBE.name())));
        List<SendMessage> actual =
                messageService.createMessages(channels, SubsMod.UNSUBSCRIBE.name(), 1L, "No channels");
        assertEquals(expected, actual);
    }

    @Test
    public void getKeyBoard() {
        InlineKeyboardMarkup expected = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton().setText("Text");
        button.setCallbackData(SubsMod.SUBSCRIBE.name() + "/" + channels.get(0).getStringId());
        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(button);
        List<List<InlineKeyboardButton>> superKeyboardButtonList = new ArrayList<>();
        superKeyboardButtonList.add(keyboardButtons);
        expected.setKeyboard(superKeyboardButtonList);

        InlineKeyboardMarkup actual = messageService.getKeyBoard(channels.get(0).getStringId(), "Text", SubsMod.SUBSCRIBE.name());

        assertEquals(expected, actual);
    }

    private List<Channel> getChannels() {
        return Arrays.asList(
                Channel.builder().title("Channel 1").stringId("String id 1").channelId(1L).build(),
                Channel.builder().title("Channel 2").stringId("String id 2").channelId(2L).build());
    }
}
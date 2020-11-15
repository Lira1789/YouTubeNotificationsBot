package com.viktoriia.youtube_bot.handler;

import com.viktoriia.youtube_bot.common.SubsMod;
import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.service.MessageService;
import com.viktoriia.youtube_bot.service.SearchService;
import com.viktoriia.youtube_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.List;

import static com.viktoriia.youtube_bot.common.Messages.CHANNELS_NOT_FOUND_MESSAGE;
import static com.viktoriia.youtube_bot.common.Messages.WRONG_COMMAND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ChannelMessageHandler implements MessageHandler {

    private final UserService userService;
    private final SearchService searchService;
    private final MessageService messageService;

    @Override
    public List<SendMessage> getMessages(Message message) {
        if (!userService.isUserOnSearchMode(message.getChatId())) {
            return Collections.singletonList(new SendMessage(message.getChatId(), WRONG_COMMAND_MESSAGE));
        }

        List<Channel> channels = searchService.searchChannel(message.getText());
        return messageService.createMessages(channels, SubsMod.SUBSCRIBE.name(), message.getChatId(), CHANNELS_NOT_FOUND_MESSAGE);
    }

    @Override
    public String getCommand() {
        return null;
    }
}

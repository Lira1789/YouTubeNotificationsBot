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
            return messageService.createSingleMessageList(WRONG_COMMAND_MESSAGE, message.getChatId());
        }

        userService.switchUserFromSearchMode(message.getChatId());

        List<Channel> channels = searchService.searchChannel(message.getText());

        if (channels.isEmpty()) {
            return messageService.createSingleMessageList(CHANNELS_NOT_FOUND_MESSAGE, message.getChatId());
        }
        return messageService.createChannelMessagesList(channels, SubsMod.SUBSCRIBE.name(), message.getChatId());
    }

    @Override
    public String getCommand() {
        return null;
    }
}

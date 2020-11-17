package com.viktoriia.youtube_bot.handler;

import com.viktoriia.youtube_bot.common.Command;
import com.viktoriia.youtube_bot.common.SubsMod;
import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.service.MessageService;
import com.viktoriia.youtube_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

import static com.viktoriia.youtube_bot.common.Messages.SUBS_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class SubscriptionsMessageHandler implements MessageHandler {

    private final UserService userService;
    private final MessageService messageService;

    @Override
    public List<SendMessage> getMessages(Message message) {
        Long chatId = message.getChatId();
        List<Channel> channels = new ArrayList<>(userService.getUser(chatId).getChannels());
        if (channels.isEmpty()) {
            return messageService.createSingleMessageList(SUBS_NOT_FOUND_MESSAGE, chatId);
        }
        return messageService.createChannelMessagesList(channels, SubsMod.UNSUBSCRIBE.name(), chatId);
    }

    @Override
    public String getCommand() {
        return Command.SUBSCRIPTIONS.getDescription();
    }
}

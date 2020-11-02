package com.viktoriia.youtube_bot.handler;

import com.viktoriia.youtube_bot.common.Command;
import com.viktoriia.youtube_bot.service.MessageService;
import com.viktoriia.youtube_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.viktoriia.youtube_bot.common.Messages.SEARCH_MESSAGE;

@RequiredArgsConstructor
@Service
public class SearchMessageHandler implements MessageHandler {

    private final UserService userService;
    private final MessageService messageService;

    @Override
    public List<SendMessage> getMessages(Message message) {
        userService.switchUserToSearchMode(message.getChatId());
        return messageService.createMessage(SEARCH_MESSAGE, message.getChatId());
    }

    @Override
    public String getCommand() {
        return Command.SEARCH.getDescription();
    }
}


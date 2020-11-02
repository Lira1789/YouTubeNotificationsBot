package com.viktoriia.youtube_bot.handler;

import com.viktoriia.youtube_bot.common.Command;
import com.viktoriia.youtube_bot.model.User;
import com.viktoriia.youtube_bot.service.MessageService;
import com.viktoriia.youtube_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.viktoriia.youtube_bot.common.Messages.START_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class StartMessageHandler implements MessageHandler {

    private final UserService userService;
    private final MessageService messageService;

    @Override
    public List<SendMessage> getMessages(Message message) {
        User user = userService.createUser(message.getFrom().getUserName(), message.getChatId());
        log.info(String.format("New user registered: %s", user.toString()));
        return messageService.createMessage(START_MESSAGE, message.getChatId());
    }

    @Override
    public String getCommand() {
        return Command.START.getDescription();
    }
}

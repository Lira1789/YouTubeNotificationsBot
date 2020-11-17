package com.viktoriia.youtube_bot.handler;

import com.viktoriia.youtube_bot.common.Command;
import com.viktoriia.youtube_bot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.viktoriia.youtube_bot.common.Messages.HELP_MESSAGE;

@Service
@RequiredArgsConstructor
public class HelpMessageHandler implements MessageHandler {

    private final MessageService messageService;

    @Override
    public List<SendMessage> getMessages(Message message) {
        return messageService.createSingleMessageList(HELP_MESSAGE, message.getChatId());
    }

    @Override
    public String getCommand() {
        return Command.HELP.getDescription();
    }
}

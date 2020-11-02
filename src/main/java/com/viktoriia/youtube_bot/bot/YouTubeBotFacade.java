package com.viktoriia.youtube_bot.bot;


import com.viktoriia.youtube_bot.handler.CallbackHandler;
import com.viktoriia.youtube_bot.handler.ChannelMessageHandler;
import com.viktoriia.youtube_bot.handler.MessageHandler;
import com.viktoriia.youtube_bot.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class YouTubeBotFacade {
    @Autowired
    @Qualifier("messageHandlerMap")
    private final Map<String, MessageHandler> messageHandlerMap;
    @Autowired
    @Qualifier("callbackHandlerMap")
    private final Map<String, CallbackHandler> callbackHandlerMap;
    private final ChannelMessageHandler channelMessageHandler;

    public List<SendMessage> handleMessage(Update update) {
        Message message = update.getMessage();
        String command = message.getText();
        return messageHandlerMap.getOrDefault(command, channelMessageHandler).getMessages(message);
    }

    public AnswerCallbackQuery handleCallBackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String subsMode = Util.getStringFromMessage(callbackQuery.getData(), 0, "/");
        String channelId = Util.getStringFromMessage(callbackQuery.getData(), 1, "/");
        return callbackHandlerMap.get(subsMode).handleCallbackQuery(callbackQuery, chatId, channelId);
    }

    public EditMessageReplyMarkup changeButtonText(CallbackQuery callbackQuery) {
        String subsMode = Util.getStringFromMessage(callbackQuery.getData(), 0, "/");
        return callbackHandlerMap.get(subsMode).getNewButtonText(callbackQuery);
    }
}

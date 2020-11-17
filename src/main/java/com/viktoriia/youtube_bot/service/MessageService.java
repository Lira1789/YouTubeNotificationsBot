package com.viktoriia.youtube_bot.service;

import com.viktoriia.youtube_bot.common.SubsMod;
import com.viktoriia.youtube_bot.model.Channel;
import com.viktoriia.youtube_bot.util.Util;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.viktoriia.youtube_bot.common.Messages.EMPTY_MESSAGE;
import static com.viktoriia.youtube_bot.common.Messages.FIND_MESSAGE;
import static com.viktoriia.youtube_bot.common.Messages.NEW_BUTTON_TEXT;

@Service
public class MessageService {
    Map<String, String> messageMap;

    public MessageService() {
        messageMap = new HashMap<>();
        messageMap.put(SubsMod.SUBSCRIBE.name(), FIND_MESSAGE);
        messageMap.put(SubsMod.UNSUBSCRIBE.name(), EMPTY_MESSAGE);
    }

    public List<SendMessage> createSingleMessageList(String message, long chatId) {
        return Collections.singletonList(createMessage(message, chatId));
    }

    public List<SendMessage> createChannelMessagesList(List<Channel> channels, String mode, long chatId) {
        String message = messageMap.get(mode);
        return channels.stream()
                .map(channel -> createMessageWithKeyBoard(message + channel.toString(), channel.getStringId(), mode, chatId))
                .collect(Collectors.toList());
    }

    public SendMessage createMessage(String message, long chatId) {
        return new SendMessage(chatId, message);
    }

    public SendMessage createMessageWithKeyBoard(String message, String channelId, String mode, long chatId) {
        return createMessage(message, chatId).enableMarkdown(true).setReplyMarkup(getKeyBoard(channelId, mode, mode));
    }

    public InlineKeyboardMarkup getKeyBoard(String channelId, String buttonText, String callbackDataMode) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button = new InlineKeyboardButton().setText(buttonText);
        button.setCallbackData(callbackDataMode + "/" + channelId);
        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(button);

        List<List<InlineKeyboardButton>> superKeyboardButtonList = new ArrayList<>();
        superKeyboardButtonList.add(keyboardButtons);
        inlineKeyboardMarkup.setKeyboard(superKeyboardButtonList);
        return inlineKeyboardMarkup;
    }

    public EditMessageReplyMarkup changeMessageButtonText(CallbackQuery callbackQuery, String buttonMode, String dataMode) {
        String channelId = Util.getStringFromMessage(callbackQuery.getData(), 1, "/");
        String buttonMessage = String.format(NEW_BUTTON_TEXT, buttonMode);
        InlineKeyboardMarkup keyBoard = getKeyBoard(channelId, buttonMessage, dataMode);

        long message_id = callbackQuery.getMessage().getMessageId();
        long chat_id = callbackQuery.getMessage().getChatId();

        return new EditMessageReplyMarkup()
                .setChatId(chat_id).setMessageId((int) (message_id)).setReplyMarkup(keyBoard);
    }
}

package com.viktoriia.youtube_bot.handler;

import com.viktoriia.youtube_bot.common.SubsMod;
import com.viktoriia.youtube_bot.service.MessageService;
import com.viktoriia.youtube_bot.service.SubscriptionService;
import com.viktoriia.youtube_bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.viktoriia.youtube_bot.common.Messages.SUBS_MESSAGE;

@Service
@RequiredArgsConstructor
public class SubscribeCallbackHandler implements CallbackHandler {

    private final SubscriptionService subscriptionService;
    private final UserService userService;
    private final MessageService messageService;

    @Override
    public AnswerCallbackQuery handleCallbackQuery(CallbackQuery callbackQuery, long chatId, String channelId) {
        subscriptionService.subscriptionAction(channelId, getSubsMod().toLowerCase());
        userService.addChannelToUser(chatId, channelId);
        return sendAnswerCallbackQuery(SUBS_MESSAGE, false, callbackQuery);
    }

    @Override
    public String getSubsMod() {
        return SubsMod.SUBSCRIBE.name();
    }

    @Override
    public EditMessageReplyMarkup getNewButtonText(CallbackQuery callbackQuery) {
        return messageService.changeMessageButtonText(callbackQuery, SubsMod.SUBSCRIBE.name(), SubsMod.UNSUBSCRIBE.name());
    }
}

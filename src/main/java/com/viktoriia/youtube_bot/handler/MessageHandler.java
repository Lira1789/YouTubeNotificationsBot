package com.viktoriia.youtube_bot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface MessageHandler {

    List<SendMessage> getMessages(Message message);

    String getCommand();
}

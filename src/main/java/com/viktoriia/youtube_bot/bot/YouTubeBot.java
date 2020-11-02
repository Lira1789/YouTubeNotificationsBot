package com.viktoriia.youtube_bot.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class YouTubeBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;
    private final YouTubeBotFacade youTubeBotFacade;

    public YouTubeBot(DefaultBotOptions options, YouTubeBotFacade youTubeBotFacade) {
        super(options);
        this.youTubeBotFacade = youTubeBotFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                execute(youTubeBotFacade.handleCallBackQuery(update));
                execute(youTubeBotFacade.changeButtonText(update.getCallbackQuery()));
            } catch (TelegramApiException e) {
                log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
            }
        }

        if (update.getMessage() != null && update.getMessage().hasText()) {
            List<SendMessage> sendMessages = youTubeBotFacade.handleMessage(update);
            for (SendMessage message : sendMessages) {
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    log.error(e.getMessage() + Arrays.toString(e.getStackTrace()));
                }
            }
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}

package com.viktoriia.youtube_bot.configuration;

import com.viktoriia.youtube_bot.bot.YouTubeBot;
import com.viktoriia.youtube_bot.bot.YouTubeBotFacade;
import com.viktoriia.youtube_bot.handler.CallbackHandler;
import com.viktoriia.youtube_bot.handler.HelpMessageHandler;
import com.viktoriia.youtube_bot.handler.MessageHandler;
import com.viktoriia.youtube_bot.handler.SearchMessageHandler;
import com.viktoriia.youtube_bot.handler.StartMessageHandler;
import com.viktoriia.youtube_bot.handler.SubscribeCallbackHandler;
import com.viktoriia.youtube_bot.handler.SubscriptionsMessageHandler;
import com.viktoriia.youtube_bot.handler.UnsubscribeCallbackHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    private final YouTubeBotConfig youTubeBotConfig;

    public AppConfig(YouTubeBotConfig youTubeBotConfig) {
        this.youTubeBotConfig = youTubeBotConfig;
    }

    @Bean
    public YouTubeBot youTubeBot(YouTubeBotFacade youTubeBotFacade) {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);

        YouTubeBot youTubeBot = new YouTubeBot(options, youTubeBotFacade);
        youTubeBot.setBotUserName(youTubeBotConfig.getBotUserName());
        youTubeBot.setBotToken(youTubeBotConfig.getBotToken());
        youTubeBot.setWebHookPath(youTubeBotConfig.getWebHookPath());

        return youTubeBot;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Map<String, MessageHandler> messageHandlerMap(StartMessageHandler startMessageHandler,
                                                         HelpMessageHandler helpMessageHandler,
                                                         SubscriptionsMessageHandler subscriptionsMessageHandler,
                                                         SearchMessageHandler searchMessageHandler) {
        Map<String, MessageHandler> map = new HashMap<>();
        map.put(startMessageHandler.getCommand(), startMessageHandler);
        map.put(searchMessageHandler.getCommand(), searchMessageHandler);
        map.put(helpMessageHandler.getCommand(), helpMessageHandler);
        map.put(subscriptionsMessageHandler.getCommand(), subscriptionsMessageHandler);
        return map;
    }

    @Bean
    public Map<String, CallbackHandler> callbackHandlerMap(SubscribeCallbackHandler subscribeCallbackHandler,
                                                           UnsubscribeCallbackHandler unsubscribeCallbackHandler) {
        Map<String, CallbackHandler> map = new HashMap<>();
        map.put(subscribeCallbackHandler.getSubsMod(), subscribeCallbackHandler);
        map.put(unsubscribeCallbackHandler.getSubsMod(), unsubscribeCallbackHandler);
        return map;
    }
}

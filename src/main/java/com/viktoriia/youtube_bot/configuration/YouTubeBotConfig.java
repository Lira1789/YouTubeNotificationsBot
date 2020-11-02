package com.viktoriia.youtube_bot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class YouTubeBotConfig {

    private String webHookPath;
    private String botUserName;
    private String botToken;

}

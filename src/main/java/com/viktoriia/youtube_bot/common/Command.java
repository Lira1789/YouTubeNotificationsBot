package com.viktoriia.youtube_bot.common;

public enum Command {
    START("/start"),
    SEARCH("/search"),
    HELP("/help"),
    SUBSCRIPTIONS("/subscriptions");

    private final String description;

    Command(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

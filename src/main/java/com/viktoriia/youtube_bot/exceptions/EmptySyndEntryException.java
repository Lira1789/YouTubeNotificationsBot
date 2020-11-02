package com.viktoriia.youtube_bot.exceptions;

public class EmptySyndEntryException extends RuntimeException {

    private static final String MESSAGE = "Empty SyndEntry. No video data";

    public EmptySyndEntryException() {
        super(MESSAGE);
    }
}

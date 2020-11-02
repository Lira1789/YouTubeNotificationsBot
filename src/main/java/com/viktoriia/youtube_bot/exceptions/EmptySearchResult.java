package com.viktoriia.youtube_bot.exceptions;

public class EmptySearchResult extends RuntimeException {

    private static final String MESSAGE = "SearchResult is empty. No data available";

    public EmptySearchResult() {
        super(MESSAGE);
    }
}

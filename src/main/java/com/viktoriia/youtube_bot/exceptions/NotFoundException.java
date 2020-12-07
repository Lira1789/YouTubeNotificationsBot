package com.viktoriia.youtube_bot.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = " object not found";

    public NotFoundException() {
        super(MESSAGE);
    }

    public NotFoundException(Class<?> clazz) {
        super(clazz.getSimpleName() + MESSAGE);
    }
}

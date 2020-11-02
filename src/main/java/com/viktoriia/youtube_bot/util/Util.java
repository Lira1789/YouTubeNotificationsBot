package com.viktoriia.youtube_bot.util;

public class Util {

    public static String getStringFromMessage(String message, int index, String delimeter) {
        String[] strings = message.split(delimeter);
        if (strings.length < 1) {
            throw new RuntimeException("Wrong Message");
        }
        return strings[index];
    }
}

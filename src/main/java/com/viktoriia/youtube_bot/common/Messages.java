package com.viktoriia.youtube_bot.common;

import com.vdurmont.emoji.EmojiParser;

public class Messages {

    public static final String SEARCH_MESSAGE = "Send me the name of the channel you want to subscribe to";
    public static final String HELP_MESSAGE = "How I work: \n" + "/search for searching channels you want to subscribe to.\n"
            + "/subscriptions to see and manage your subscriptions \n" + "/help - for help \n"
            + "After subscribing you will receive new  channels videos immediately after publication!";
    public static final String START_MESSAGE = "Hello! I'm YouTubeNotification bot! \n"
            + "With my help you can subscribe to YouTube channels and receive new videos immediately after publication. \n"
            + HELP_MESSAGE;

    public static final String FIND_MESSAGE = EmojiParser.parseToUnicode(":mag_right:") + " *Found channel:* \n";
    public static final String EMPTY_MESSAGE = "";
    public static final String WRONG_COMMAND_MESSAGE = "You have to send /search command first";
    public static final String CHANNELS_NOT_FOUND_MESSAGE = "No Channels  were found";
    public static final String SUBS_NOT_FOUND_MESSAGE = "You have no subscriptions";
    public static final String SUBS_MESSAGE = "You are subscribed";
    public static final String UNSUBS_MESSAGE = "You are unsubscribed";
    public static final String NEW_BUTTON_TEXT = EmojiParser.parseToUnicode(":heavy_check_mark:") + " %sD";

}

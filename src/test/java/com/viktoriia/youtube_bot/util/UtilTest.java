package com.viktoriia.youtube_bot.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTest {

    @Test
    public void getStringFromMessage() {
        String actualString1 = Util.getStringFromMessage("Util/test", 1, "/");
        String actualString2 = Util.getStringFromMessage("Util/test", 0, "/");
        assertEquals("test", actualString1);
        assertEquals("Util", actualString2);
    }

    @Test(expected = RuntimeException.class)
    public void getStringFromMessage_WhenInvalidMessage_thenThrowException() {
        Util.getStringFromMessage("Utiltest", 1, "/");
    }
}
package com.viktoriia.youtube_bot.model;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@Entity(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private long videoId;
    @Column(name = "string_id")
    private String stringId;
    private String title;
    private String author;
    private String url;
    @Column(name = "channel_string_id")
    private String channelStringId;
    private LocalDateTime date;


    @Override
    public String toString() {
        return EmojiParser.parseToUnicode(":video_camera:")
                + String.format("NEW VIDEO! \n%s just uploaded a new video \n\"%s\" \nCheck it out!\n %s",
                author, title, url);
    }
}

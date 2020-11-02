package com.viktoriia.youtube_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "isSearch")
@Builder(toBuilder = true)
@Table(name = "users")
public class User {

    @Id
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "user_name")
    private String username;
    @Column(name = "is_search")
    private boolean isSearch;

    @ManyToMany
    @JoinTable(name = "users_channels", joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id", referencedColumnName = "channel_id"))
    private Set<Channel> channels;

}

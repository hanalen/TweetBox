package com.tweetbox.tweet.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMention {
    private String name;
    private String screen_name;
    private Long id;
    private Long id_str;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_str() {
        return id_str;
    }

    public void setId_str(Long id_str) {
        this.id_str = id_str;
    }
}

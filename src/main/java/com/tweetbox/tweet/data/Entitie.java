package com.tweetbox.tweet.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entitie {
    @JsonProperty("media")
    private Media[] media;
    @JsonProperty("user_mentions")
    private UserMention[] user_mentions;
    @JsonProperty("urls")
    private Url[] urls;

    public Media[] getMedia() {
        return media;
    }

    public void setMedia(Media[] media) {
        this.media = media;
    }

    public UserMention[] getUser_mentions() {
        return user_mentions;
    }

    public void setUser_mentions(UserMention[] user_mentions) {
        this.user_mentions = user_mentions;
    }

    public Url[] getUrls() {
        return urls;
    }

    public void setUrls(Url[] urls) {
        this.urls = urls;
    }
}

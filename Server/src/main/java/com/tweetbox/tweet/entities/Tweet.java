package com.tweetbox.tweet.entities;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
  private Long userId;
  private Long id;
  private String id_str;
  private String created_at;

  private Long retweet_count;
  private Long favorite_count;

  private Boolean retweeted;
  private Boolean favorited;

  private String in_reply_to_screen_name;
  private String in_reply_to_user_id_str;

  @JsonProperty("full_text")
  @JsonAlias("text")
  private String full_text;

  private String source;

  @JsonProperty("entities")
  private Entitie entities;
  @JsonProperty("extended_entities")
  private Entitie extended_entities;

  private Long in_reply_to_status_id;
  private String in_reply_to_status_id_str;

  public Boolean getRetweeted() {
    return retweeted;
  }

  public void setRetweeted(Boolean retweeted) {
    this.retweeted = retweeted;
  }

  public String getFull_text() {
    return full_text;
  }

  public void setFull_text(String full_text) {
    this.full_text = full_text;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public Long getRetweet_count() {
    return retweet_count;
  }

  public void setRetweet_count(Long retweet_count) {
    this.retweet_count = retweet_count;
  }

  public Long getFavorite_count() {
    return favorite_count;
  }

  public void setFavorite_count(Long favorite_count) {
    this.favorite_count = favorite_count;
  }

  public Boolean getFavorited() {
    return favorited;
  }

  public void setFavorited(Boolean favorited) {
    this.favorited = favorited;
  }

  public String getIn_reply_to_screen_name() {
    return in_reply_to_screen_name;
  }

  public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
    this.in_reply_to_screen_name = in_reply_to_screen_name;
  }

  public String getIn_reply_to_user_id_str() {
    return in_reply_to_user_id_str;
  }

  public void setIn_reply_to_user_id_str(String in_reply_to_user_id_str) {
    this.in_reply_to_user_id_str = in_reply_to_user_id_str;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Entitie getEntities() {
    return entities;
  }

  public void setEntities(Entitie entities) {
    this.entities = entities;
  }

  public Entitie getExtended_entities() {
    return extended_entities;
  }

  public void setExtended_entities(Entitie extended_entities) {
    this.extended_entities = extended_entities;
  }

  public Long getIn_reply_to_status_id() {
    return in_reply_to_status_id;
  }

  public void setIn_reply_to_status_id(Long in_reply_to_status_id) {
    this.in_reply_to_status_id = in_reply_to_status_id;
  }

  public String getIn_reply_to_status_id_str() {
    return in_reply_to_status_id_str;
  }

  public void setIn_reply_to_status_id_str(String in_reply_to_status_id_str) {
    this.in_reply_to_status_id_str = in_reply_to_status_id_str;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}

package com.tweetbox.tweet.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Url {
  private Long userId;
  private String url;
  private String expanded_url;
  private String display_url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getExpanded_url() {
    return expanded_url;
  }

  public void setExpanded_url(String expanded_url) {
    this.expanded_url = expanded_url;
  }

  public String getDisplay_url() {
    return display_url;
  }

  public void setDisplay_url(String display_url) {
    this.display_url = display_url;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}

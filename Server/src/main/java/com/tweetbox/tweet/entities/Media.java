package com.tweetbox.tweet.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
  private Long userId;
  private String id_str;
  private String media_url;
  private String media_url_https;
  private String url;
  private String display_url;
  private String expanded_url;
  private String type;

  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

  public String getMedia_url() {
    return media_url;
  }

  public void setMedia_url(String media_url) {
    this.media_url = media_url;
  }

  public String getMedia_url_https() {
    return media_url_https;
  }

  public void setMedia_url_https(String media_url_https) {
    this.media_url_https = media_url_https;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDisplay_url() {
    return display_url;
  }

  public void setDisplay_url(String display_url) {
    this.display_url = display_url;
  }

  public String getExpanded_url() {
    return expanded_url;
  }

  public void setExpanded_url(String expanded_url) {
    this.expanded_url = expanded_url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
//    private video_info?: I.VideoInfo;
}

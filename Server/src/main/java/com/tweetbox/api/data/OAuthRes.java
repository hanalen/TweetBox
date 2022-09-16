package com.tweetbox.api.data;

public class OAuthRes {
  private Boolean oauth_callback_confirmed;
  private String oauth_token;
  private String oauth_token_secret;
  private String screen_name;
  private String name;
  private String user_id;

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

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

  public String getOauth_token_secret() {
    return oauth_token_secret;
  }

  public void setOauth_token_secret(String oauth_token_secret) {
    this.oauth_token_secret = oauth_token_secret;
  }

  public String getOauth_token() {
    return oauth_token;
  }

  public void setOauth_token(String oauth_token) {
    this.oauth_token = oauth_token;
  }

  public Boolean getOauth_callback_confirmed() {
    return oauth_callback_confirmed;
  }

  public void setOauth_callback_confirmed(Boolean oauth_callback_confirmed) {
    this.oauth_callback_confirmed = oauth_callback_confirmed;
  }
}

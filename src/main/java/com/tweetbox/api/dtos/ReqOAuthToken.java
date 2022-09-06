package com.tweetbox.api.dtos;

import org.jetbrains.annotations.NotNull;

import java.util.SortedMap;
import java.util.TreeMap;

public class ReqOAuthToken extends APIRequest {
  public ReqOAuthToken() {
    super("https://api.twitter.com/oauth/request_token", new TreeMap<>());
  }

  public String getOauth_callback() {
    return super.getData().get("oauth_callback");
  }

  public void setOauth_callback(String oauth_callback) {
    super.getData().put("oauth_callback", oauth_callback);
  }
}

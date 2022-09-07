package com.tweetbox.api.dtos;

import java.util.TreeMap;

public class ReqAccessToken extends APIRequest {
  public ReqAccessToken() {
    super("https://api.twitter.com/oauth/access_token", new TreeMap<>());
  }

  public String getOauth_verifier() {
    return super.getData().get("oauth_verifier");
  }

  public void setOauth_verifier(String value) {
    super.getData().put("oauth_verifier", value);
  }

  public String getOauth_token() {
    return super.getData().get("oauth_token");
  }

  public void setOauth_token(String value) {
    super.getData().put("oauth_token", value);
  }
}

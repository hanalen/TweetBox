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
}

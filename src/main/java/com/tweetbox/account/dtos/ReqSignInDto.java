package com.tweetbox.account.dtos;

public class ReqSignInDto {
  private String oauth_token;
  private String oauth_verifier;

  public String getOauth_verifier() {
    return oauth_verifier;
  }

  public void setOauth_verifier(String oauth_verifier) {
    this.oauth_verifier = oauth_verifier;
  }

  public String getOauth_token() {
    return oauth_token;
  }

  public void setOauth_token(String oauth_token) {
    this.oauth_token = oauth_token;
  }
}

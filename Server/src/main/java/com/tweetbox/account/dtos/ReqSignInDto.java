package com.tweetbox.account.dtos;

public class ReqSignInDto {
  private String oauth_token;
  private String oauth_token_secret;
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

  public String getOauth_token_secret() {
    return oauth_token_secret;
  }

  public void setOauth_token_secret(String oauth_token_secret) {
    this.oauth_token_secret = oauth_token_secret;
  }
}
